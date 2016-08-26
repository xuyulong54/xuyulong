package wusc.edu.pay.core.settlement.biz.sub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.service.CalculateFeeFacade;
import wusc.edu.pay.facade.notify.util.NotifyUtil;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;

import com.alibaba.fastjson.JSONObject;

@Component("feeBiz")
public class FeeBiz {

	@Autowired
	private CalculateFeeFacade calculateFeeFacade;

	@Autowired
	private JmsTemplate notifyJmsTemplate;

	private static final Log log = LogFactory.getLog(FeeBiz.class);

	/**
	 * 
	 * @描述: 结算T+0手续费计算方法.
	 * @作者: Along.shen .
	 * @创建时间:2015-3-11,下午2:13:13.
	 * @版本:
	 */
	public double calculateFee(String merchantNo, double amount, SettRecord settRecord) {
		// 初始化结算计费项
		CalculateFeeItemEnum calculateFeeItem = CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_0;
		
		log.info(String
				.format("==>userNo:%s, userType:%s, calFeeItem:%s, payProduct:%s, frpCode:%s, amount:%s, transferDate:%s, merchantName:%s, orderNo:%s, trxNo:%s",
						settRecord.getUserNo(), settRecord.getUserType(), calculateFeeItem.name(), calculateFeeItem.name(),
						calculateFeeItem.name(), settRecord.getSettAmount(), settRecord.getCreateTime(), settRecord.getUserName(),
						settRecord.getBatchNo(), settRecord.getBatchNo()));
		
		// 计算商户结算手续费
		final FeeCalculateResultDTO settFeeDto = calculateFeeFacade.preCaculateFee(merchantNo, UserTypeEnum.MERCHANT.getValue(), calculateFeeItem.getValue(), calculateFeeItem.name(), calculateFeeItem.name(), amount, new Date(), settRecord.getUserName(), settRecord.getBatchNo(), settRecord.getBatchNo());
		log.info("==>preCaculate Result:" + JSONObject.toJSONString(settFeeDto));
		
		// 结算手续费
		double settFee = settFeeDto.getPayFee();
		List<FeeCalculateResultDTO> calculateResultDTOList = new ArrayList<FeeCalculateResultDTO>();
		settFeeDto.calFeeFlow = null;
		calculateResultDTOList.add(settFeeDto);
		// settRecord中保存calculateResultDTOList
		settRecord.setCalculateResultDTOList(calculateResultDTOList);

		// 得到打款金额(没有做到分的取舍，结算记录审核时做处理)
		double remitAmount = AmountUtil.sub(amount, settFee); // 打款金额
		settRecord.setSettFee(BigDecimal.valueOf(settFee)); // 结算手续费
		settRecord.setRemitAmount(BigDecimal.valueOf(remitAmount)); // 结算打款金额
		return settFee;
	}
	

	/**
	 * 计费订单保存
	 * 
	 * @param remitProcess
	 * @return
	 */
	public void caculate(final FeeCalculateResultDTO dto) {
		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(NotifyUtil.formatFee(dto));
			}
		});
	}
}
