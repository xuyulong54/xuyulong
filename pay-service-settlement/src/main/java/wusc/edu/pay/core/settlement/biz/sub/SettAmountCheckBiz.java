package wusc.edu.pay.core.settlement.biz.sub;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectTypeEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;

/**
 * 
 * @描述: 结算金额检查biz.
 * @作者: Along.shen .
 * @创建时间:2015-3-18,下午3:21:12.
 * @版本:
 */
@Component("settAmountCheckBiz")
public class SettAmountCheckBiz {

	@Autowired
	private SettDailyCollectDao settDailyCollectDao;

	private static final Log log = LogFactory.getLog(SettAmountCheckBiz.class);

	/** 判断结算金额是否小于0.01 如果不足一分 不生成结算记录 **/
	public void checkSettAmount(SettRecord settRecord) {
		BigDecimal countAmount = settRecord.getSettAmount().subtract(settRecord.getSettFee());
		if (countAmount.compareTo(BigDecimal.valueOf(0.01D)) == -1) {
			throw new SettBizException(SettBizException.SETTLE_AMOUNT_ACCOUNT_CHECKERR, "扣除手续费后,打款金额不足0.01元,不生成结算记录！");
		}

		// 判断结算金额是否是精确到了小数点前两位
		// 1.如果小数点两位之后没有数据eg：123.12
		// 2.如果小数点两位之后有数据eg：123.1233
		// 3.把小数点后两位有数据的数据截取：eg：0.0033 放入一个临时的SettDailyCollect中
		BigDecimal settAmount = settRecord.getSettAmount();// 原始值
		settRecord.setTradeAmount(settRecord.getSettAmount());
		BigDecimal setScale = settAmount.setScale(2, BigDecimal.ROUND_DOWN);// 取小数点后两位之前的值
		BigDecimal subtract = settAmount.subtract(setScale);// 小数点2位后面的值，可能为0
		SettDailyCollect tempLeave = null;
		// 如果不为0
		if (subtract.compareTo(BigDecimal.ZERO) == 1) {
			settRecord.setSettAmount(setScale);// 取舍之后的值
			// 打款金额为结算金额减去手续费
			settRecord.setRemitAmount(setScale.subtract(settRecord.getSettFee()));
			tempLeave = new SettDailyCollect();

			tempLeave.setTotalAmount(subtract);// 把小数点后面两位设置为汇总
			tempLeave.setTotalCount(1);
			tempLeave.setAccountNo(settRecord.getAccountNo());
			tempLeave.setCollectType(SettDailyCollectTypeEnum.LEAVE.getValue());
			tempLeave.setCollectDate(new Date());
			tempLeave.setSettStatus(SettDailyCollectStatusEnum.UNSETTLE.getValue());
			tempLeave.setRemark("结算遗留汇总金额");
			log.info("生成遗留汇总");
			settDailyCollectDao.insert(tempLeave);
		}

	}
}
