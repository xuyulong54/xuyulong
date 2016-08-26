package wusc.edu.pay.web.boss.action.rule;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.bank.enums.BankBusTypeEnum;
import wusc.edu.pay.facade.payrule.entity.Frp;
import wusc.edu.pay.facade.payrule.enums.PayTypeEnum;
import wusc.edu.pay.facade.payrule.service.FrpFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 
 * @描述: 支付渠道管理Action.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-12,下午4:08:47 .
 * @版本: 1.0 .
 */
@Scope("prototype")
public class FrpAction extends BossBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3188385796404167392L;
	@Autowired
	private FrpFacade frpFacade;

	public FrpFacade getFrpFacade() {
		return frpFacade;
	}

	public void setFrpFacade(FrpFacade frpFacade) {
		this.frpFacade = frpFacade;
	}

	/**
	 * 获取支付渠道列表
	 * 
	 * @return listFrp or operateError .
	 */
	@Permission("bank:frp:view")
	public String listFrp() {
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("frpCode", getString("frpCode")); // 支付渠道编号
		paramMap.put("bankName", getString("bankName")); // 银行名称
		paramMap.put("bankCode", getString("bankCode")); // 银行编号
		paramMap.put("status", getString("status")); // 状态
		paramMap.put("busType", getString("busType")); // 业务类型
		paramMap.put("payType", getString("payType")); //支付类型
		super.pageBean = frpFacade.listPage(getPageParam(), paramMap);

		this.putData("bankBusTypeEnumList", BankBusTypeEnum.toListForPayWay());
		this.putData("payTypeEnum", PayTypeEnum.values());
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "listFrp";
	}

	/**
	 * 转到添加支付渠道页面
	 * 
	 * @return addFrpUI or operateError .
	 */
	public String addFrpUI() {
		// 获取银行列表
		Map<String, String> map = new Hashtable<String, String>();
		for (BankCode s : BankCode.values()) {
			map.put(s.name(), s.getDesc());
		}
		this.putData("bankBusTypeEnumList", BankBusTypeEnum.toListForPayWay());
		this.putData("PayTypeEnum", PayTypeEnum.toMap());
		this.putData("bankDictList", map);
		return "addFrpUI";
	}

	/**
	 * 保存新添加支付渠道信息
	 * 
	 * @return operateSuccess or operateError.
	 */
	@Permission("bank:frp:add")
	public String addFrp() {
		String frpCode = getString("frpCode");
		String busType = getString("busType"); // 业务类型
		String bCode = getString("bankCode");
		if ("".equals(bCode) || bCode == null) {
			return operateError("请选择银行");
		}
		String payType = getString("payType");
		//如果非银行卡则没有业务类型
		if(payType.equals(PayTypeEnum.BANK_CARD.getValue())){
			if ("".equals(busType) || busType == null) {
				return operateError("请选择业务类型");
			}
		}

		if ("".equals(frpCode) || frpCode == null) {
			return operateError("支付渠道编号为空");
		}

		if (frpCode.indexOf("未知") > 0) {
			return operateError("支付渠道编号有误");
		}
		Frp frp = frpFacade.findByFrpCode(frpCode);
		if (frp != null) {
			// return operateError("该支付渠道编码已经存在");
			return operateError("该支付渠道编码已经存在");
		}
		frp = new Frp();
		frp.setFrpCode(frpCode);
		frp.setPayType(Integer.parseInt(payType));
		//如果非银行卡则没有业务类型
		if(payType.equals(PayTypeEnum.BANK_CARD.getValue()+"")){
			BankCode bankCode = BankCode.valueOf(bCode);
			frp.setBankName(bankCode.getDesc());
			frp.setBankCode(bankCode);
		}else{
			frp.setBankName(bCode);
			frp.setBankCode(BankCode.OTHER);
		}
		
		frp.setStatus(getInteger("status"));
		frp.setBusType(busType);// 业务类型
		frpFacade.create(frp);
		super.logSave("添加支付渠道信息.支付渠道编号[" + frp.getFrpCode() + "]");
		return operateSuccess();
	}

	/**
	 * 转到支付渠道修改页面
	 * 
	 * @return editFrpUI or operateError.
	 */
	@Permission("bank:frp:edit")
	public String editFrpUI() {
		// 获取银行列表
		Map<String, String> map = new Hashtable<String, String>();
		for (BankCode s : BankCode.values()) {
			map.put(s.name(), s.getDesc());
		}
		this.putData("bankDictList", map);
		this.putData("bankBusTypeEnumList", BankBusTypeEnum.toListForPayWay());
		String frpCode = getString("frpCode");
		Frp frp = frpFacade.findByFrpCode(frpCode);
		this.putData("PayTypeEnum", PayTypeEnum.toMap());
		this.pushData(frp);
		return "editFrpUI";
	}

	/**
	 * 保存修改后的支付渠道信息
	 * 
	 * @return operateSuccess or operateError.
	 */
	public String editFrp() {
		String frpCode = getString("frpCode");
		if ("".equals(frpCode) || frpCode == null) {
			return operateError("支付渠道编号有误");
		}
		Frp frp = frpFacade.findByFrpCode(frpCode);
		if (frp == null) {
			return operateError("不存在该支付渠道");
		}
		frp.setStatus(getInteger("status"));
		frpFacade.update(frp);
		super.logEdit("修改支付渠道信息.支付渠道编号[" + frp.getFrpCode() + "]");
		return operateSuccess();
	}

	/**
	 * 对添加的支付渠道进行验证
	 * 
	 * @author shenjialong
	 * @param frp
	 * @return
	 */
//	private String validateFrp(Frp frp) {
//		String errmeg = "";
//		if (!frp.getBankCode().getDesc().equals(frp.getBankName())) {
//			errmeg += "银行编号和银行的名字不对应";
//		}
//
//		if (frp.getFrpCode().isEmpty()) {
//			errmeg += "支付渠道编号不能为空，";
//		}
//		return errmeg;
//	}

	/**
	 * 按条件查询并分页列出支付渠道信息（用于设置支付渠道时的查找带回）.
	 * 
	 * @return lookupFrpList .
	 */
	public String lookupFrpList() {
		// 如果请求中有查询参数，刚按照查询参数进行查询
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer bankCode = getInteger("bankCode"); // 支付渠道的业务类型，是属于B2C,B2B,FAST
		paramMap.put("frpCode", getString("frpCode")); //
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue()); // 激活状态
		String busType = "1";
		if (null != bankCode) {
			if (bankCode == 6001) {
				busType = BankBusTypeEnum.NET_B2C.name();
			} else if (bankCode == 6002) {
				busType = BankBusTypeEnum.NET_B2B.name();
			} else if (bankCode == 6004) {
				busType = BankBusTypeEnum.FAST.name();
			}
		}
		this.putData("bankCode", bankCode);
		paramMap.put("busType", busType); // 业务类型
		super.pageBean = frpFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "lookupFrpList";
	}
}
