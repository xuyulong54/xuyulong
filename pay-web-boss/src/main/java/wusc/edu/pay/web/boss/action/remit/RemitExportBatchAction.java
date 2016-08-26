package wusc.edu.pay.web.boss.action.remit;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.remit.entity.RemitBatch;
import wusc.edu.pay.facade.remit.enums.RemitBatchStatusEnum;
import wusc.edu.pay.facade.remit.enums.RemitProcessStatusEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.service.RemitBatchFacade;
import wusc.edu.pay.facade.remit.service.RemitFacade;
import wusc.edu.pay.facade.remit.service.RemitProcessFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;



/***
 * 
 * @ClassName: RemitExportBatchAction
 * @Description: 打款确认Action
 * @author huangbin
 * @date 2015-3-14 下午5:36:07
 *
 */
public class RemitExportBatchAction extends BossBaseAction {

	private static final long serialVersionUID = 245827766930488432L;

	private static Log log = LogFactory.getLog(RemitExportBatchAction.class);

	@Autowired
	private RemitProcessFacade remitProcessFacade;
	@Autowired
	private RemitFacade remitFacade;
	@Autowired
	private RemitBatchFacade remitBatchFacade;

	/**
	 * @Title: remitExportBatchList
	 * @Description: 打款导出批次列表
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String remitExportBatchList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", getString("status"));
		paramMap.put("beginDate", getString("beginDate"));
		paramMap.put("endDate", getString("endDate"));
		paramMap.put("batchNo", getString("batchNo"));
		super.pageBean = remitBatchFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("remitBatchStatusEnumList", RemitBatchStatusEnum.toList()); //  
		this.putData("remitBatchStatusEnum", RemitBatchStatusEnum.toMap()); //  
		this.putData("publicStatusEnum", PublicStatusEnum.toMap()); //  
		return "remitExportBatchList";
	}

	/**
	 * @Title: remitExportBatchDetail
	 * @Description: 打款导出批次明细
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitExportBatch:view")
	public String remitExportBatchDetail() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", getString("batchNo"));
		RemitBatch remitBatch = remitBatchFacade.getByBatchNo(getString("batchNo"));
		super.pageBean = remitProcessFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.putData("remitExportBatch", remitBatch);
		this.putData("remitBatchStatusEnumList", RemitBatchStatusEnum.toList()); //  
		this.putData("remitProcessStatusEnumMap", RemitProcessStatusEnum.toList()); // 打款处理
		this.putData("tradeSourcesEnumList", TradeSourcesEnum.toList());	// 业务发起方
//		this.putData("remitBatchStatusEnumList", RemitBatchStatusEnum.toList());
//		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
//		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toList());
		return "remitExportBatchDetail";
	}

	/**
	 * @Title: remitExportBatchStatusChange
	 * @Description: 修改导出批次的状态
	 * @param
	 * @return void
	 * @throws
	 */
	@Permission("boss:remitExportBatch:edit")
	public void remitExportBatchStatusChange() {

		String result = getString("result");
		String batchNo = getString("batchNo");

		log.info("====info====  打款确认开始，批次号为：" + batchNo);

		if ("success".equals(result)) {
			remitFacade.remitSuccess(batchNo, getLoginedOperator().getLoginName());
			this.logSave("打款确认成功：批次号：" + batchNo);
			getOutputMsg().put("STATUS", "success");
			getOutputMsg().put("MSG", "操作成功");

		} else {
			remitFacade.remitFail(batchNo, getLoginedOperator().getLoginName(), "");
			this.logSave("打款确认失败：批次号：" + batchNo);
			getOutputMsg().put("STATUS", "fail");
			getOutputMsg().put("MSG", "操作成功");
		}
		log.info("====info==== 打款确认结束，批次号为：" + batchNo);
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

}
