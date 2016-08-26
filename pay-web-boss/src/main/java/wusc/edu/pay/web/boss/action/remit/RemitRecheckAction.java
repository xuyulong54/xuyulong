package wusc.edu.pay.web.boss.action.remit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.remit.service.RemitChannelFacade;
import wusc.edu.pay.facade.remit.service.RemitFacade;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.permission.entity.PmsOperator;


/***
 * @ClassName: RemitRecheckAction
 * @Description: 打款管理-复核
 * @author huangbin
 * @date 2015-3-14 下午3:48:41
 * 
 */
public class RemitRecheckAction extends BossBaseAction {

	private static final long serialVersionUID = -9069579696546652181L;
	private static final Log log = LogFactory.getLog(RemitRecheckAction.class);
	@Autowired
	private RemitChannelFacade remitChannelFacade;
	@Autowired
	private RemitRequestFacade remitRequestFacade;
	@Autowired
	private RemitFacade remitFacade;

	/**
	 * @Title: 打款复核页面
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitRecheck:detail")
	public String remitRecheckList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", getString("beginDate"));
		paramMap.put("endDate", getString("endDate"));
		paramMap.put("requestNo", getString("requestNo"));
		paramMap.put("status", RemitRequestStatusEnum.AUDITED.getValue()); // 查询审核通过的数据
		paramMap.put("isCount", 1);
		paramMap.put("orderByParam", "AMOUNT");
		paramMap.put("sort", "DESC");
		super.pageBean = remitRequestFacade.listPage(this.getPageParam(), paramMap); // 查询打款请求表
		List<RemitChannel> remitChannelList = remitChannelFacade.getAllOpenChannel();
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("remitChannelList", remitChannelList);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.values());
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitRequestStatusEnumList", RemitRequestStatusEnum.toList());
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());

		return "remitRecheckList";
	}

	/***
	 * 
	 * @Title: remitRecheckDetail
	 * @Description: 复核详情
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Permission("boss:remitRecheck:detail")
	public String remitRecheckDetail() {

		Long id = getLong("id");
		RemitRequest remitRequest = remitRequestFacade.getById(id);
		if (remitRequest != null) {
			this.putData("RemitProcessStatusEnums", RemitRequestStatusEnum.values());
			this.putData("TradeSourcesEnums", TradeSourcesEnum.values());
			this.putData("AccountTypeEnums", BankAccountTypeEnum.toRemitList());
			this.putData("PublicStatusEnums", PublicStatusEnum.toIsOrNotList());
			this.putData("TradeTypeEnums", TradeTypeEnum.values());
			this.pushData(remitRequest);
		} else {
			return operateError("异常数据");
		}

		// Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("batchNo", getString("batchNo"));
		// RemitBatch remitBatch =
		// remitBatchFacade.getByBatchNo(getString("batchNo"));
		// super.pageBean = remitProcessFacade.listPage(this.getPageParam(),
		// paramMap);
		// List<RemitChannel> remitChannelList =
		// remitChannelFacade.getAllOpenChannel();
		// this.pushData(pageBean);
		// this.putData("remitBatch", remitBatch);
		// this.putData("remitBatchStatusEnumList",
		// RemitBatchStatusEnum.toList());
		// this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		// this.putData("tradeSourcesEnumList", TradeSourcesEnum.toList());
		// this.putData("remitChannelList", remitChannelList);
		// this.putData("bankAccountTypeEnumList",
		// BankAccountTypeEnum.toList());
		// this.putData("remitProcessStatusEnumMap",
		// RemitProcessStatusEnum.toMap());
		// this.putData("remitBatchStatusEnumMap",
		// RemitBatchStatusEnum.toMap());
		return "remitRecheckDetail";
	}

	/**
	 * @Title: 多批次复核处理
	 * @Description:
	 * @param @return
	 * @return
	 * @throws
	 */
	@Permission("boss:remitRecheck:recheck")
	public String remitBatchRecheck() {
		String passOrders = getString("passOrders"); // 复审通过的数据
		String rejectOrders = getString("rejectOrders"); // 复审不通过的数据
		String flag = getString("flag");
		int batchNumber = 0; // 批量审核数量
		if (StringUtils.isNotBlank(passOrders)) {
			batchNumber += passOrders.split(",").length;
		}
		if (StringUtils.isNotBlank(rejectOrders)) {
			batchNumber += rejectOrders.split(",").length;
		}
		if (StringUtil.isEmpty(passOrders) && StringUtil.isEmpty(rejectOrders)) {
			return operateError("请选择批次");
		} else {
			String result = "";
			PmsOperator pmsOperator = getLoginedOperator();
			String confirm = pmsOperator.getLoginName(); // 复核人
			try {
				if (StringUtil.isNotNull(passOrders)) {
					String[] ids = passOrders.split(","); // 选中的记录
					remitFacade.reviewSuccess(ids, confirm);
					this.logSave("多批次复核通过，id：" + passOrders);
				}
				if (StringUtil.isNotNull(rejectOrders)) {
					String[] ids = rejectOrders.split(","); // 选中的记录
					remitFacade.reviewFail(ids, confirm);
					this.logSave("多批次复核不通过，id：" + rejectOrders);
				}
			} catch (BizException e) {
				return operateError("" + e.getMessage());
			}
			log.info("=================多批次复核处理 结果：总共" + batchNumber + "批次，" + result);
			if (!"1".equals(flag)) {
				return operateSuccess();
			} else {
				return remitRecheckList();
			}
		}
	}

	/**
	 * @Title: 单批次复核处理
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitRecheck:recheck")
	public String remitSingleRecheck() {
		String passRequests = getString("passRequests");
		String rejectRequests = getString("rejectRequests");
		int requestNumber = 0;
		// if (passRequests != null) {
		// requestNumber += passRequests.split(",").length;
		// }
		// if (passRequests != null) {
		// requestNumber += passRequests.split(",").length;
		// }
		if (StringUtil.isEmpty(passRequests) && StringUtil.isEmpty(rejectRequests)) {
			return operateError("请选择打款请求!");
			// getOutputMsg().put("STATE", "FAIL");
			// getOutputMsg().put("MSG", "请选择打款请求");
		} else {
			String result = "";
			try {
				PmsOperator pmsOperator = getLoginedOperator();
				String confirm = pmsOperator.getLoginName(); // 复核人
				if (StringUtils.isNotBlank(passRequests)) {
					String[] ids = { passRequests };
					remitFacade.reviewSuccess(ids, confirm);
					this.logSave("单批次复核通过，id：" + passRequests);
				}
				if (StringUtils.isNotBlank(rejectRequests)) {
					String[] ids = { rejectRequests };
					remitFacade.reviewFail(ids, confirm);
					this.logSave("单批次复核不通过，id：" + rejectRequests);
				}
				// getOutputMsg().put("STATE", "SUCCESS");
				// getOutputMsg().put("MSG", result);
			} catch (BizException e) {
				return operateError("打款复核异常!");
				// getOutputMsg().put("STATE", "FAIL");
				// getOutputMsg().put("MSG", e.getMessage() + "，异常编码：" +
				// e.getCode());
			}
			log.info("================单批次复核处理  结果：总共有" + requestNumber + "笔请求" + result);
		}
		return operateSuccess();
		// this.outPrint(this.getHttpResponse(),
		// JSONObject.fromObject(this.getOutputMsg()));
	}
}
