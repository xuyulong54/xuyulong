package wusc.edu.pay.web.boss.action.sett;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.CurrencyTypeEnum;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettReturnFeeTypeEnum;
import wusc.edu.pay.facade.settlement.service.SettBusinessFacade;
import wusc.edu.pay.facade.settlement.service.SettManagementFacade;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 
 * Desc: 结算记录管理
 * 
 * @author lichao Date: 2014-8-7
 * 
 */
public class SettRecordAction extends BossBaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SettQueryFacade settQueryFacade;
	@Autowired
	private SettBusinessFacade settBusinessFacade;
	@Autowired
	private SettManagementFacade settManagementFacade;

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@Permission("sett:record:view")
	public String listSettRecord() {
		String userNo = getString("userNo");
		String accountNo = getString("accountNo");
		Integer settMode = getInteger("settMode");
		Integer settStatus = getInteger("settStatus");
		String startSettDate = getString("startSettDate");
		String endSettDate = getString("endSettDate");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("accountNo", accountNo);
		map.put("settMode", settMode);
		map.put("settStatus", settStatus);
		map.put("startSettDate", startSettDate);
		map.put("endSettDate", endSettDate);
		map.put("withDraw", SettModeTypeEnum.MEMBER_WITHDRAW.getValue());// 过滤会员提现
		map.put("isCount", 1); // 统计
		PageBean pageSettRecord = settQueryFacade.listPageSettRecord(getPageParam(), map);
		this.pushData(pageSettRecord);
		this.putData("userNo", userNo);
		this.putData("accountNo", accountNo);
		this.putData("settMode", settMode);
		this.putData("settStatus", settStatus);
		this.putData("startSettDate", startSettDate);
		this.putData("endSettDate", endSettDate);
		this.putData("SettRecordStatusEnum", SettRecordStatusEnum.toList());
		this.putData("remitFail", SettRecordStatusEnum.FAIL_RETURN.getValue());
		this.putData("waitConfirm", SettRecordStatusEnum.WAIT_CONFIRM.getValue());
		this.putData("SettModeTypeEnum", SettModeTypeEnum.toList());
		return "listSettRecord";
	}

	/**
	 * 详情
	 */
	@Permission("sett:record:view")
	public String detailSettRecord() {
		String batchNo = getString("batchNo");
		String accountNo = getString("accountNo");
		SettRecord settRecord = settQueryFacade.getSettRecordByBatchNoAndAccountNo(batchNo, accountNo);
		this.pushData(settRecord);
		this.putData("SettleReturnFeeTypeEnum", SettReturnFeeTypeEnum.toList());
		this.putData("SettRecordStatusEnum", SettRecordStatusEnum.toList());
		this.putData("BankAccountTypeEnum", BankAccountTypeEnum.toList());
		this.putData("CurrencyTypeEnum", CurrencyTypeEnum.values());
		this.putData("SettModeTypeEnum", SettModeTypeEnum.toList());
		return "detailSettRecord";
	}

	/**
	 * 重新发送结算
	 * 
	 * @return
	 */
	@Permission("sett:record:audit")
	public String resendSett() {
		// 取到失败的结算记录
		String batchNo = getString("batchNo");
		String accountNo = getString("accountNo");
		SettRecord settRecord = settQueryFacade.getSettRecordByBatchNoAndAccountNo(batchNo, accountNo);
		// 重新结算
		settBusinessFacade.launchAnewSettle(settRecord);
		super.logSave("重新发送结算.用户编号[" + settRecord.getUserNo() + "]");
		return this.operateSuccess("已经重新发送结算！");
	}
	
	/**
	 * 确认结算失败,结算记录作废
	 * confirmFail()
	 * @return 
	 * @since  1.0
	 */
	@Permission("sett:record:audit")
	public String confirmFail() {
		// 取到失败的结算记录
		String batchNo = getString("batchNo");
		String accountNo = getString("accountNo");
		SettRecord settRecord = settQueryFacade.getSettRecordByBatchNoAndAccountNo(batchNo, accountNo);
		// 调接口把结算记录的状态设置为 REMIT_FAIL (打款确定失败)，并解冻资金
		try {
			settBusinessFacade.confirmFail(settRecord);
		} catch (BizException e) {
			return this.operateSuccess("操作失败："+e.getMsg());
		}
		super.logSave("确认结算失败.用户编号[" + settRecord.getUserNo() + "]");
		return this.operateSuccess("操作成功");
	}

	/**
	 * 审核结算信息UI
	 * 
	 * @return
	 */
	public String auditSettUI() {
		String batchNo = getString("batchNo");
		String accountNo = getString("accountNo");
		SettRecord settRecord = settQueryFacade.getSettRecordByBatchNoAndAccountNo(batchNo, accountNo);
		this.pushData(settRecord);
		this.putData("SettleReturnFeeTypeEnum", SettReturnFeeTypeEnum.toList());
		this.putData("SettRecordStatusEnum", SettRecordStatusEnum.toList());
		this.putData("BankAccountTypeEnum", BankAccountTypeEnum.toList());
		this.putData("CurrencyTypeEnum", CurrencyTypeEnum.values());
		this.putData("SettModeTypeEnum", SettModeTypeEnum.toList());
		return "auditSettUI";
	}

	/**
	 * 审核结算信息
	 * 
	 * @return
	 */
	@Permission("sett:record:audit")
	public String auditSett() {
		Integer settStatus = getInteger("settStatus");
		String remark = getString("remark");
		String batchNo = getString("batchNo");
		String accountNo = getString("accountNo");
		if (settStatus == 1) {
			settStatus = SettRecordStatusEnum.CONFIRMED.getValue();
		} else if (settStatus == 2) {
			settStatus = SettRecordStatusEnum.CANCEL.getValue();
		}
		try {
			settManagementFacade.updateSettRecordByBatchNoAccountNo(batchNo, accountNo, settStatus, remark);
			this.logSave("审核结算信息，账户编号："+accountNo);
		} catch (BizException e) {
			return this.operateError(e.getMsg());
		}
		return this.operateSuccess();
	}

}
