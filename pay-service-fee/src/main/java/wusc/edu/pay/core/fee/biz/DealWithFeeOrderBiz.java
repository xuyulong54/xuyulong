package wusc.edu.pay.core.fee.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.entity.FeeFlowAccumulator;
import wusc.edu.pay.facade.fee.entity.FeeOrder;


/**
 * 计费订单处理接口biz(异步--订单保存、流量累加、退款处理)
 * 
 */
@Component("dealWithFeeOrderBiz")
public class DealWithFeeOrderBiz {
	private static final Log log = LogFactory.getLog(DealWithFeeOrderBiz.class);

	@Autowired
	private FeeFlowAccumulatorBiz feeFlowAccumulatorBiz;
	@Autowired
	private FeeOrderBiz feeOrderBiz;

	/** 处理计费订单 **/
	public void dealWithFeeOrder(FeeCalculateResultDTO calculateResult) {
		FeeOrder order = calculateResult.getFeeOrder();
		FeeFlowAccumulator calFeeFlow = calculateResult.getCalFeeFlow();
		if (calFeeFlow != null) {
			// 如果流量对象数据库中没有这创建
			if (calFeeFlow.getId() == null || calFeeFlow.getId() <= 0) {
				log.info("插入流量累加数据");
				feeFlowAccumulatorBiz.create(calFeeFlow);
			} else {
				log.info("修改流量累加数据");
				feeFlowAccumulatorBiz.update(calFeeFlow);
			}
		}
		if(order!=null){
		// 创建计费订单
		log.info("创建计费订单");
		order.setStatus(PublicStatusEnum.ACTIVE.getValue());
		feeOrderBiz.createFeeOrder(order);
		}

	}

}
