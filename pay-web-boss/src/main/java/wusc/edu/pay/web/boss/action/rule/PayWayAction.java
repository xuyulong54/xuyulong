package wusc.edu.pay.web.boss.action.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.payrule.entity.BankBranch;
import wusc.edu.pay.facade.payrule.entity.Frp;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.entity.vo.FrpSelectVo;
import wusc.edu.pay.facade.payrule.enums.PayProductStatusEnum;
import wusc.edu.pay.facade.payrule.service.BankBranchFacade;
import wusc.edu.pay.facade.payrule.service.FrpFacade;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 支付方式 ClassName: PayWayAction <br/>
 * Function: <br/>
 * date: 2014-6-27 下午5:00:08 <br/>
 * 
 * @author laich
 */
public class PayWayAction extends BossBaseAction {

	private static final Log log = LogFactory.getLog(PayWayAction.class);
	private static final long serialVersionUID = -9086857476337734230L;

	@Autowired
	private PayWayFacade payWayFacade;
	@Autowired
	private FrpFacade frpFacade;
	@Autowired
	private BankBranchFacade bankBranchFacade;

	// 支付产品 绑定支付渠道
	@Permission("payrule:payway:view")
	public String editUI() {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payProductCode", getString("payProductCode"));
		paramMap.put("payProductName", getString("payProductName"));
		this.pushData(paramMap); // 回显查询条件
		List<FrpSelectVo> frps = payWayFacade.queryFrpSelectVos(getString("payProductCode"));

		super.putData("frps", frps);
		super.putData("PayProductStatusEnum", PayProductStatusEnum.toMap());
		return "editUI";
	}

	// 修改
	@Permission("payrule:payway:edit")
	public String editView() {
		String payWayCode = getString("payWayCode");
		String payProductCode = getString("payProductCode");
		PayWay payWay = payWayFacade.getPayWayBypayWayCodeAndpayProductCode(payWayCode, payProductCode);
		BankBranch bankBranch = bankBranchFacade.getByFrpCode(payWay.getPayWayCode());

		// 如果是非银行卡，则前端不需要显示选择支付接口 ，仅当 bankBranch = null;

		super.putData("payWay", payWay);
		super.putData("bankBranch", bankBranch);
		log.info("修改支付产品编号：" + payProductCode + " 支付方式编号:" + payWay.getPayWayCode());
		return "editView";
	}

	// 新增 保存操作 ajax
	@Permission("payrule:payway:add")
	public void payWaySave() {
		try {
			String sign = getString("sign");
			if (sign.equals("add")) {
				String frpCode = getString("frpCode");
				String payProductCode = getString("payProductCode");
				Frp frp = frpFacade.findByFrpCode(frpCode);
				BankBranch bankBranch = bankBranchFacade.getCacheByFrpCode(frpCode);
				String defaultBankChannelCode = "";

				if (bankBranch == null) {// 非银行卡类
					defaultBankChannelCode = frp.getBankName();
				} else {
					defaultBankChannelCode = bankBranch.getDefaultBankChannelCode();
				}

				long payWayId = payWayFacade.createPayWay(frpCode, frp.getBankName(), payProductCode, defaultBankChannelCode,
						PayProductStatusEnum.ACTIVITY.getValue(), 1000);
				this.logSave("添加支付方式，支付方式名称：" + frp.getBankName());
				super.outPrint(super.getHttpResponse(), "[{\"msg\":\"success\",\"payWayId\":\"" + payWayId + "\"}]");
			} else if (sign.equals("delete")) {
				long payWayId = Long.parseLong(getString("payWayId"));
				payWayFacade.deletePayWay(payWayId);
				log.info("删除支付方式：" + payWayId);
				this.logDelete("删除支付方式：" + payWayId + " ");
				super.outPrint(super.getHttpResponse(), "[{\"msg\":\"success\"}]");
			}
		} catch (Exception e) {
			super.outPrint(super.getHttpResponse(), "[{\"msg\":\"error\"}]");
			log.error("Exception" ,e);
		}
	}

	// 修改操作
	@Permission("payrule:payway:edit")
	public String payWayEdit() {
		if (StringUtils.isEmpty(getString("id"))) {
			return super.operateError("参数为空!");
		}
		Long id = Long.parseLong(getString("id"));
		String defaultPayInterface = getString("defaultPayInterface");
		String payWayName = getString("payWayName");
		String payWayCode = getString("payWayCode");

		// 当 defaultPayInterface = null 时，有可能是非银行卡的修改操作需要 特定处理。非银行卡 payWayCode = defaultPayInterface
		if (defaultPayInterface == null) {
			defaultPayInterface = payWayCode;
		}

		Integer sort = 1000;// 默认值
		if (StringUtils.isNotEmpty(getString("sort"))) {
			sort = Integer.parseInt(getString("sort"));
		}
		payWayFacade.updatePayWay(payWayCode, payWayName, id, defaultPayInterface, PayProductStatusEnum.ACTIVITY.getValue(), sort);
		this.logEdit("添加支付方式，支付方式名称：" + payWayName);
		log.info("修改支付方式：" + payWayCode + "  修改成功");
		return super.operateSuccess();
	}

	/**
	 * 支付方式查找带回
	 * 
	 * @return
	 */
	public String payWayLookupList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payProductCode", getString("payProductCode"));
		super.pageBean = payWayFacade.queryPayWay(getPageParam(), paramMap);
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);
		return "payWayLookupList";
	}

}
