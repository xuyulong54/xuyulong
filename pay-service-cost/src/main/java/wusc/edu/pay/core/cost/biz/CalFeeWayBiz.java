package wusc.edu.pay.core.cost.biz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.CheckUtils;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.cost.dao.CalFeeWayDao;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.BillingCycleEnum;


/**
 * 
 * @描述: 计费约束表业务实现类 .
 * @作者: 李安国 .
 * @创建时间: 2014-4-15, 下午2:28:52
 */
@Component("calFeeWayBiz")
public class CalFeeWayBiz extends BaseBizImpl<CalFeeWay> {

	@Autowired
	private CalFeeWayDao calFeeWayDao;
	/**
	 * log4j日志记录
	 */
	private Log logger = LogFactory.getLog(CalFeeWayBiz.class);

	/**
	 * 计费约束中，有效期的起始和截止时间的格式
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	protected BaseDao<CalFeeWay> getDao() {
		return calFeeWayDao;
	}

	public long deleteById(long id) {
		return calFeeWayDao.deleteById(id);
	}

	/**
	 * 根据计费维度ID查询计费约束
	 * 
	 * @param productName
	 * @return
	 */
	public List<CalFeeWay> listByDimensionId(long dimensionId) {
		return calFeeWayDao.getCalFeeWayByDimensionID(dimensionId);
	}

	/**
	 * 获取所有计费约束信息
	 * 
	 * @return
	 */
	public List<CalFeeWay> listAll() {
		return calFeeWayDao.listBy(new HashMap());
	}

	/**
	 * 验证计费约束是否有效
	 * 
	 * @param calFeeWay
	 *            计费约束
	 * @param mccTypeCode
	 *            客户端上送的MCC类别信息
	 * @return
	 * @throws ParseException
	 */
	public boolean validate(CalFeeWay calFeeWay, String mccTypeCode) {
		if (CheckUtils.isNull(calFeeWay.getBeginDate())) {
			logger.warn("计费约束中,[" + calFeeWay.getWayName() + "]约束的生效日期为空");
			return false;
		}
		if (CheckUtils.isNull(calFeeWay.getEndDate())) {
			logger.warn("计费约束中,[" + calFeeWay.getWayName() + "]约束的失效日期为空");
			return false;
		}
		if (!DateUtils.isBetween(new Date(), calFeeWay.getBeginDate(), calFeeWay.getEndDate(), DateUtils.LEFT_CLOSE_RIGHT_OPEN,
				DateUtils.COMP_MODEL_DATE)) {
			logger.info("计费约束中,[" + calFeeWay.getWayName() + "]约束已失效[" + sdf.format(calFeeWay.getBeginDate()) + "]->["
					+ sdf.format(calFeeWay.getEndDate()) + "]");
			return false;
		}
		if (calFeeWay.getCycleType() != null && calFeeWay.getCycleType() == BillingCycleEnum.CUSTOM.getValue()
				&& calFeeWay.getCustomizeDay() != null) {
			if (new Date().before(calFeeWay.getCustomizeDay())) {
				logger.info("计费约束中,自定义周期的起始日期为[" + sdf.format(calFeeWay.getCustomizeDay()) + "],约束暂未生效");
				return false;
			}
		}
		if (calFeeWay.getStatus().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			logger.info("计费约束中,[" + calFeeWay.getWayName() + "]约束状态异常[" + calFeeWay.getStatus() + "]");
			return false;
		}
		if (!CheckUtils.isNull(calFeeWay.getMcc()) && !CheckUtils.isNull(mccTypeCode)) {
			if (!mccTypeCode.equals(calFeeWay.getMcc())) {
				logger.info("计费约束中,[" + calFeeWay.getWayName() + "]约束的MCC类别[" + calFeeWay.getMcc() + "]与客户端上送的MCC类别[" + mccTypeCode + "]不符");
				return false;
			}
		}
		return true;
	}
}