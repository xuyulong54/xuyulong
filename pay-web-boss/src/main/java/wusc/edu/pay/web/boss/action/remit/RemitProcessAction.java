/**
 * wusc.edu.pay.web.boss.action.remit.RemitProcessAction.java
 */
package wusc.edu.pay.web.boss.action.remit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.enums.RemitProcessStatusEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.remit.service.RemitChannelFacade;
import wusc.edu.pay.facade.remit.service.RemitProcessFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * <ul>
 * <li>Title:打款记录</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-8-4
 */
public class RemitProcessAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private RemitProcessFacade remitProcessFacade;
	@Autowired
	private RemitChannelFacade remitChannelFacade;

	/**
	 * 查询打款记录
	 * 
	 * @return
	 */
	@Permission("boss:remitProcess:view")
	public String remitProcessList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", getString("beginDate"));
		paramMap.put("endDate", getString("endDate"));
		paramMap.put("channelCode", getString("remitBankChannelCode"));// 打款通道编号
		paramMap.put("requestNo", getString("requestNo"));// 请求流水号
		paramMap.put("flowNo", getString("flowNo"));// 打款流水号
		paramMap.put("accountName", getString("accountName"));// 收款户名
		paramMap.put("accountNo", getString("accountNo"));// 收款账号
		paramMap.put("bankName", getString("bankName"));// 收款发卡行
		paramMap.put("status", getInteger("status"));
		super.pageBean = remitProcessFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("RemitProcessStatusEnums", RemitProcessStatusEnum.values());
		this.putData("remitProcessStatusEnumList", RemitProcessStatusEnum.toList());
		this.putData("remitChannelList", remitChannelFacade.listBy(new HashMap<String,Object>()));
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());
		return "remitProcessList";
	}

	/**
	 * 详情
	 * 
	 * @return
	 */
	@Permission("boss:remitProcess:view")
	public String remitProcessView() {
		Long id = getLong("id");
		if (ValidateUtils.isEmpty(id)) {
			return operateError("参数丢失");
		}
		RemitProcess remitProcess = remitProcessFacade.getById(id);
		if (ValidateUtils.isEmpty(remitProcess)) {
			return operateError("未查询到相关信息");
		}
		this.pushData(remitProcess);

		this.putData("RemitProcessStatusEnums", RemitProcessStatusEnum.values());
		this.putData("TradeSourcesEnums", TradeSourcesEnum.values());
		this.putData("AccountTypeEnums", BankAccountTypeEnum.values());
		this.putData("PublicStatusEnums", PublicStatusEnum.toIsOrNotList());
		this.putData("TradeTypeEnums", TradeTypeEnum.values());

		return "remitProcessView";
	}

	/**
	 * 待打款列表
	 * 
	 * @return
	 */
	@Permission("boss:remitProcess:view")
	public String remitProcessUnremitList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", getString("beginDate"));
		paramMap.put("endDate", getString("endDate"));

		paramMap.put("status", RemitProcessStatusEnum.AUTHSTR.getValue());// 等打款
		paramMap.put("isAutoProcess", PublicStatusEnum.INACTIVE.getValue());// 非自动打款

		paramMap.put("requestNo", getString("requestNo"));// 请求流水号
		paramMap.put("flowNo", getString("flowNo"));// 打款流水号
		paramMap.put("accountName", getString("accountName"));// 收款户名
		paramMap.put("bankChannelNo", getString("bankChannelNo"));// 收款账号
		paramMap.put("bankName", getString("bankName"));// 收款发卡行
		super.pageBean = remitProcessFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		
		
		paramMap.clear();
		List<RemitChannel> remitChannelList = remitChannelFacade.listBy(paramMap);//打款通道
		this.putData("remitChannelList", remitChannelList);

		
		this.putData("RemitProcessStatusEnums", RemitProcessStatusEnum.values());
		return "remitProcessUnremitList";
	}

}
