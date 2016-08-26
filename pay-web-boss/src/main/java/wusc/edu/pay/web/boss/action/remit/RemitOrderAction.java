package wusc.edu.pay.web.boss.action.remit;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.importExcel.ExcelUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.remit.entity.RemitBankInfo;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;
import wusc.edu.pay.facade.remit.enums.RemitUrgentEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.remit.service.RemitBankAreaFacade;
import wusc.edu.pay.facade.remit.service.RemitBankInfoFacade;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.remit.service.RemitFacade;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.permission.entity.PmsOperator;


/**
 * @Title: 制单管理ACTION
 * @Description:
 * @author zzh
 * @date 2014-8-1 下午4:24:53
 */
public class RemitOrderAction extends BossBaseAction {

	private static final long serialVersionUID = -6223771945124125681L;
	private static final Log log = LogFactory.getLog(RemitOrderAction.class);

	@Autowired
	private RemitRequestFacade remitRequestFacade;
	@Autowired
	private RemitBankTypeFacade remitBankTypeFacade;
	@Autowired
	private RemitBankInfoFacade remitBankInfoFacade;
	@Autowired
	private RemitBankAreaFacade remitBankAreaFacade;
	@Autowired
	private UserBankAccountFacade userBankAccountFacade;
	@Autowired
	private RemitFacade remitFacade;

	private File upload; // 上传的文件
	private String uploadContentType;// 上传文件类型
	private String uploadFileName;// 上传文件名

	/**
	 * @Title: 查询制单
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitOrder:view")
	public String remitOrderList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountName", getString("accountName"));
		paramMap.put("accountNo", getString("accountNo"));
		paramMap.put("bankChannelNo", getString("bankChannelNo"));
		// paramMap.put("creator", getLoginedOperator().getLoginName());
		super.pageBean = remitRequestFacade.listPage(this.getPageParam(), paramMap);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.values());
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitRequestStatusEnumList", RemitRequestStatusEnum.toList());
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());
		this.pushData(pageBean);
		this.pushData(paramMap);

		return "remitOrderList";
	}

	/**
	 * @Title: 制单新增页面
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitOrder:add")
	public String remitOrderAdd() {
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitBankTypeList", remitBankTypeList);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("provinceList", remitBankAreaFacade.getProvince());
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());
		return "remitOrderAdd";
	}

	/**
	 * @Title: 查找带回账户银行信息
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String remitOrderAccountInfo() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", getString("loginName"));
		paramMap.put("userNo", getString("userNo"));
		paramMap.put("isDefault", PublicStatusEnum.ACTIVE.getValue());
		super.pageBean = userBankAccountFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		return "remitOrderAccountInfo";
	}

	/**
	 * @Title: 获取省份信息
	 * @Description: {[province:xxx],[province:xxx]}
	 * @param
	 * @return void
	 * @throws
	 */
	public void getProvince() {
		List<Map<String, Object>> provinceList = remitBankAreaFacade.getProvince();
		getOutputMsg().put("provinceList", provinceList);
		log.info("====info==== 获取省份信息有" + provinceList.size() + "条");
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * @Title: 根据省份获取下面的城市信息
	 * @Description: {[city:xxx],[city:xxx]}
	 * @param
	 * @return void
	 * @throws
	 */
	public void getCityByProvince() {
		String province = getString("province");
		List<Map<String, Object>> cityList = remitBankAreaFacade.getCityByProvince(province);
		getOutputMsg().put("cityList", cityList);
		log.info("====info==== 获取【" + province + "】下的城市信息有" + cityList.size() + "条");
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * @Title: 根据银行行别和区域查询银行信息
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public void getBankInfoListByBankTypeCodeAndArea() {
		try {
			String bankTypeCode = getString("bankTypeCode");
			String province = getString("province");
			String city = getString("city");
			List<RemitBankInfo> remitBankInfoList = remitBankInfoFacade.listByBankTypeCodeAndArea(bankTypeCode,
					province, city);
			getOutputMsg().put("remitBankInfoList", remitBankInfoList);
			log.info("====info==== 查询银行信息，条件为bankTypeCode：" + bankTypeCode + ",province:" + province + ",city:" + city
					+ ",查到相关信息有" + remitBankInfoList.size() + "条");
		} catch (Exception e) {
			log.error("============= RemitOrderAction deleteRemitOrder Exception", e);
			getOutputMsg().put("remitBankInfoList", null);
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * @Title: 根据银行行号获取银行信息
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public void getRemitBankInfoByBankChannelNo() {
		try {
			String bankChannelNo = getString("bankChannelNo");
			if (bankChannelNo != null) {
				RemitBankInfo remitBankInfo = remitBankInfoFacade.getByBankChannelNo(bankChannelNo);
				getOutputMsg().put("remitBankInfo", remitBankInfo);
			} else {
				getOutputMsg().put("FAIL", "FAIL");
			}
		} catch (Exception e) {
			log.error("============= RemitOrderAction getRemitBankInfoByBankChannelNo Exception", e);
			getOutputMsg().put("FAIL", "FAIL");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * @Title: 根据开户行名搜索银行信息，返回第一条数据
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public void getRemitBankInfoByBankName() {
		String bankName = getString("bankName");
		List<RemitBankInfo> remitBankInfoList = remitBankInfoFacade.listByBankName(bankName);
		if (remitBankInfoList.size() > 0) {
			RemitBankInfo remitBankInfo = remitBankInfoList.get(0);
			getOutputMsg().put("remitBankInfo", remitBankInfo);
			String typeCode = remitBankInfo.getBankChannelNo().substring(0, 3);
			String areaCode = remitBankInfo.getBankChannelNo().substring(3, 7);
			getOutputMsg().put("remitBankType", remitBankTypeFacade.getByTypeCode(typeCode));
			getOutputMsg().put("remitBankArea", remitBankAreaFacade.listByAreaCode(areaCode).get(0));
			getOutputMsg().put(
					"cityList",
					remitBankAreaFacade.getCityByProvince(remitBankAreaFacade.listByAreaCode(areaCode).get(0)
							.getProvince()));
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * @Title: 制单新增保存
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitOrder:add")
	public String remitOrderSave() {
		RemitRequest remitRequest = new RemitRequest();
		try {
			// remitRequest.setRequestNo(String.valueOf(System.currentTimeMillis()));
			String requestNo = remitRequestFacade.buildRemitRequestNo();
			remitRequest.setRequestNo(requestNo);
			remitRequest.setAccountName(getString("accountName"));
			remitRequest.setAccountNo(getString("accountNo"));
			remitRequest.setUserNo(getString("userNo"));
			remitRequest.setAccountType(getInteger("accountType"));
			remitRequest.setAmount(BigDecimal.valueOf(getDouble("amount")));
			remitRequest.setBankChannelNo(getString("bankChannelNo"));
			remitRequest.setBankName(remitBankInfoFacade.getByBankChannelNo(getString("bankChannelNo")).getBankName());
			remitRequest.setBankRemark(getString("bankRemark"));
			remitRequest.setCity(getString("city"));
			remitRequest.setCreateDate(new Date());
			PmsOperator pmsOperator = getLoginedOperator();
			remitRequest.setCreator(pmsOperator.getLoginName());
			remitRequest.setCurrency(getString("currency"));
			// remitRequest.setFlowNo(flowNo); TODO:打款流水号
			remitRequest.setIsAutoProcess(PublicStatusEnum.INACTIVE.getValue());
			remitRequest.setIsUrgent(RemitUrgentEnum.UN_URGENT.getValue());
			remitRequest.setProvince(getString("province"));
			remitRequest.setStatus(RemitRequestStatusEnum.UNAUDIT.getValue());
			remitRequest.setTradeInitiator(TradeSourcesEnum.BOSS_SYSTEM.getValue());
			remitRequest.setUserNo(getString("userNo"));
			remitRequest.setBusinessType(TradeTypeEnum.MERCHANT_MANUAL_ORDER.getValue());
			remitRequestFacade.create(remitRequest);
			log.info("====info==== 创建打款请求成功 requestNo:" + remitRequest.getRequestNo() + ", accountName:"
					+ remitRequest.getAccountName() + ", accountNo:" + remitRequest.getAccountNo());
			this.logSave("添加打款请求，请求号：" + remitRequest.getRequestNo() + ", 账户名：" + remitRequest.getAccountName());
			return operateSuccess();
		} catch (Exception e) {
			log.error("===========RemitOrderAction remitOrderSave Exception", e);
			return operateError("保存出错");
		}
	}

	/**
	 * @Title: 制单修改页面
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitOrder:edit")
	public String remitOrderEdit() {
		Long id = getLong("id");
		RemitRequest remitRequest = remitRequestFacade.getById(id);
		this.pushData(remitRequest);
		RemitBankInfo remitBankInfo = remitBankInfoFacade.getByBankChannelNo(remitRequest.getBankChannelNo());
		this.putData("remitBankInfo", remitBankInfo);
		this.putData("bankType", remitRequest.getBankChannelNo().substring(0, 3));
		List<RemitBankInfo> remitBankInfoList = remitBankInfoFacade.listByBankTypeCodeAndArea(remitRequest
				.getBankChannelNo().substring(0, 3), remitRequest.getProvince(), remitRequest.getCity());
		this.putData("remitBankInfoList", remitBankInfoList);
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitBankTypeList", remitBankTypeList);
		this.putData("provinceList", remitBankAreaFacade.getProvince());
		this.putData("cityList", remitBankAreaFacade.getCityByProvince(remitRequest.getProvince()));
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());
		return "remitOrderEdit";
	}

	/**
	 * @Title: 制单修改更新
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitOrder:edit")
	public String remitOrderUpdate() {
		Long id = getLong("id");
		RemitRequest remitRequest = remitRequestFacade.getById(id);
		try {
			remitRequest.setAccountName(getString("accountName"));
			remitRequest.setAccountNo(getString("accountNo"));
			remitRequest.setUserNo(getString("userNo"));
			remitRequest.setAccountType(getInteger("accountType"));
			remitRequest.setAmount(BigDecimal.valueOf(getDouble("amount")));
			remitRequest.setBankChannelNo(getString("bankChannelNo"));
			remitRequest.setBankName(remitBankInfoFacade.getByBankChannelNo(getString("bankChannelNo")).getBankName());
			remitRequest.setBankRemark(getString("bankRemark"));
			remitRequest.setCity(getString("city"));
			remitRequest.setCreateDate(new Date());
			PmsOperator pmsOperator = getLoginedOperator();
			remitRequest.setCreator(pmsOperator.getLoginName());
			remitRequest.setCurrency(getString("currency"));
			// remitRequest.setFlowNo(flowNo); TODO:打款流水号
			remitRequest.setIsAutoProcess(PublicStatusEnum.INACTIVE.getValue());
			remitRequest.setIsUrgent(getInteger("isUrgent"));
			remitRequest.setProvince(getString("province"));
			remitRequest.setStatus(RemitRequestStatusEnum.UNAUDIT.getValue());
			// remitRequest.setUserNo(userNo) TODO:用户编号
			remitRequestFacade.update(remitRequest);
			this.logSave("修改制单，请求号：" + remitRequest.getRequestNo() + ", 账户名：" + remitRequest.getAccountName());
			return operateSuccess();
		} catch (Exception e) {
			log.error("===========RemitOrderAction remitOrderUpdate Exception", e);
			return operateError("更新失败");
		}
	}

	public void judgeRemitOrderStatus() {
		Long id = getLong("id");
		RemitRequest remitRequest = remitRequestFacade.getById(id);
		if (remitRequest.getStatus() != RemitRequestStatusEnum.UNAUDIT.getValue()) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "该制单已被审核，不可更改");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * @Title: 删除制单信息
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	@Permission("boss:remitOrder:delete")
	public void deleteRemitOrder() {
		try {
			Long id = getLong("id");
			RemitRequest remitRequest = remitRequestFacade.getById(id);
			if (remitRequest.getStatus() != RemitRequestStatusEnum.UNAUDIT.getValue()) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "该制单已被审核，不可删除");
			} else {
				remitRequestFacade.deleteById(id);
				this.logSave("删除制单，请求号：" + remitRequest.getRequestNo() + ", 账户名：" + remitRequest.getAccountName());
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "删除成功");
			}

		} catch (Exception e) {
			log.error("============= RemitOrderAction deleteRemitOrder Exception", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "删除失败");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/***
	 * 重新制单
	 * 
	 * @return
	 */
	@Permission("boss:remitOrder:reAdd")
	public String reRemitOrder() {

		String remitOrderIdList = getString("remitOrderIdList");
		
		try {
			if (StringUtil.isNotNull(remitOrderIdList)) {
				
				String[] ids = remitOrderIdList.split(","); 
				remitFacade.reCreate(ids, getLoginedOperator().getLoginName());
				
				super.logSave(getLoginedOperator().getRealName() + "重新制单（id分别为：【" + remitOrderIdList + "】）成功！");
				return this.operateSuccess("重新制单成功！");
				
			}else{
				return operateError("重新制单失败！");
			}
		} catch (Exception e) {
			return operateError("" + e.getMessage());
		}
	}

	/**
	 * 单笔订单重新制单
	 * @return
	 */
	@Permission("boss:remitOrder:edit")
	public String reRemitOrderSingle(){
		String requestNo = getString("requestNo");//请求号
		Integer accountType = getInteger("accountType");//账户类型
		String accountName = getString("accountName");//账户名
		String accountNo = getString("accountNo");//收款方账号
		Double amount = getDouble("amount");//金额
		String province = getString("province");//省份
		String city = getString("city");//城市
		String typeCode = getString("typeCode");//银行名称
		String bankName = getString("bankName");//银行名称
		
		RemitRequest remitRequest = remitRequestFacade.getByRequestNo(requestNo);
		if(remitRequest == null){
			return operateError("【"+requestNo+"】该请求打款单不存在");
		}else{
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			remitRequest.setAccountName(accountName);
			remitRequest.setAccountNo(accountNo);
			remitRequest.setAccountType(accountType);
			remitRequest.setAmount(BigDecimal.valueOf(amount));
			remitRequest.setProvince(province);
			remitRequest.setCity(city);
			remitRequest.setBankName(bankName);
			remitRequest.setStatus(RemitRequestStatusEnum.UNAUDIT.getValue());
			
			List<RemitBankInfo> remitBankInfoList = remitBankInfoFacade.listByBankName(bankName);
			if(remitBankInfoList.size() > 0){
				remitRequest.setBankChannelNo(remitBankInfoList.get(0).getBankChannelNo());
			}else{
				return operateError("查不到对应的银行行号");
			}
			remitRequest.setCreator(getLoginedOperator().getLoginName());
			remitRequest.setCreateDate(new Date());
			remitRequest.setCreateTime(new Date());
			
			remitRequestFacade.update(remitRequest);
			
			return operateSuccess();
		}
	}
	
	/**
	 * 批量导入打款记录界面
	 */
	@Permission("boss:remitOrder:add")
	public String importRemitOrderUI() {
		return "importRemitOrderUI";
	}

	/**
	 * 批量导入打款记录
	 */
	@Permission("boss:remitOrder:add")
	public String importRemitOrder() {
		if (StringUtil.isNotNull(upload)) {
			List<Map<String, String>> datas = ExcelUtil.readSheet(upload, 0);
			try {
				// 检测Excel数据是否有效
				List<Map<String, String>> resuleDatas = checkExcelData(datas);
				List<RemitRequest> remitRequestList = new ArrayList<RemitRequest>();
				// 查询所有银行信息
				// List<RemitBankInfo> remitBankInfoList =
				// remitBankInfoFacade.listAll();

				for (int i = 0; i < resuleDatas.size(); i++) {
					Map<String, String> data = resuleDatas.get(i);

					RemitRequest remitRequest = new RemitRequest();
					/*
					 * for (RemitBankInfo rB : remitBankInfoList) { if
					 * (data.get("收款开户行行号").equals(rB.getBankChannelNo())) {
					 * remitRequest.setBankName(rB.getBankName()); break; } }
					 */
					if (StringUtil.isNotBlank(data.get("收款开户行行号"))) {
						RemitBankInfo remitBankInfo = remitBankInfoFacade.getByBankChannelNo(data.get("收款开户行行号"));
						if (remitBankInfo != null) {
							remitRequest.setBankName(remitBankInfo.getBankName());
						}
					}
					if (remitRequest.getBankName() == null) {
						return this.operateError("第[" + (i + 1) + "]行的收款开户行行号无效！");
					}
					remitRequest.setAccountName(data.get("收款户名"));
					remitRequest.setAccountNo(data.get("收款账号"));
					remitRequest.setAccountType(Integer.valueOf(data.get("帐户类型")));
					remitRequest.setAmount(new BigDecimal(data.get("收款金额")));
					remitRequest.setBankChannelNo(data.get("收款开户行行号"));
					remitRequest.setTradeInitiator(TradeSourcesEnum.BOSS_SYSTEM.getValue());
					remitRequest.setBusinessType(TradeTypeEnum.MERCHANT_MANUAL_ORDER.getValue());
					remitRequest.setCity(data.get("市"));
					// remitRequest.setIsUrgent(Integer.valueOf(data.get("是否加急")));
					remitRequest.setProvince(data.get("省"));

					remitRequest.setIsAutoProcess(PublicStatusEnum.INACTIVE.getValue());// 是否自动处理
					remitRequest.setRequestNo(remitRequestFacade.buildRemitRequestNo());// 打款请求号
					remitRequest.setStatus(RemitRequestStatusEnum.UNAUDIT.getValue());// 状态：待审核
					remitRequest.setCreateDate(new Date());// 创建时间
					remitRequest.setCreator(getLoginedOperator().getLoginName());// 创建人
					remitRequestList.add(remitRequest);
				}
				remitRequestFacade.batchInsert(remitRequestList);
			} catch (Exception e1) {
				log.error("添加数据出错!", e1);
				return this.operateError(e1.getMessage());
			}
		} else {
			return this.operateError("文件格式有误，必须为Excel文件格式。");
		}

		super.logSave(getLoginedOperator().getRealName() + "批量导入打款记录成功");
		return this.operateSuccess("导入数据成功！");
	}

	/**
	 * 检测Excel数据是否有效
	 * 
	 * @param datas
	 * @throws Exception
	 */
	private List<Map<String, String>> checkExcelData(List<Map<String, String>> datas) throws Exception {

		List<Map<String, String>> resultDate = new ArrayList<Map<String, String>>();

		StringBuffer eMsg = new StringBuffer();
		if (datas == null) {
			eMsg.append("Excel数据为空!");
			throw new Exception(eMsg.toString());
		}
		for (int i = 0; i < datas.size(); i++) {
			Map<String, String> data = datas.get(i);
			
			// 校验帐户类型
			if (!StringUtil.isNotNull(data.get("帐户类型"))) {
				log.error("第[" + (i + 1) + "]行的帐户类型不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的帐户类型不能为空！");
			} else if (data.get("帐户类型").equals("对私")) {
				data.put("帐户类型", String.valueOf(BankAccountTypeEnum.PRIVATE_DEBIT_CARD.getValue()));
			} else if (data.get("帐户类型").equals("对公")) {
				data.put("帐户类型", String.valueOf(BankAccountTypeEnum.PUBLIC_ACCOUNTS.getValue()));
			} else {
				log.error("第[" + (i + 1) + "]行的帐户类型只能填对公或者对私！");
				eMsg.append("第[" + (i + 1) + "]行的帐户类型只能填对公或者对私！");
			}
			/*
			 * // 校验是否加急 if (!StringUtil.isNotNull(data.get("是否加急"))) {
			 * log.error("第[" + (i + 1) + "]行的是否加急不能为空！"); eMsg.append("第[" + (i
			 * + 1) + "]行的是否加急不能为空！"); } else if (data.get("是否加急").equals("是"))
			 * { data.put("是否加急",
			 * String.valueOf(PublicStatusEnum.ACTIVE.getValue())); } else if
			 * (data.get("是否加急").equals("否")) { data.put("是否加急",
			 * String.valueOf(PublicStatusEnum.INACTIVE.getValue())); } else {
			 * log.error("第[" + (i + 1) + "]行的是否加急只能填是或者否！"); eMsg.append("第[" +
			 * (i + 1) + "]行的是否加急只能填是或者否！"); }
			 */
			// 校验收款开户行行号
			if (!StringUtil.isNotNull(data.get("收款开户行行号"))) {
				log.error("第[" + (i + 1) + "]行的收款开户行行号不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的收款开户行行号不能为空！");
			} else {
				Pattern pattern = Pattern.compile("^\\d{11}$");// 必须为11位数字
				if (pattern.matcher(data.get("收款开户行行号")).matches()) {
					log.error("第[" + (i + 1) + "]行的收款开户行行号错误！");
					eMsg.append("第[" + (i + 1) + "]行的收款开户行行号错误！");
				}
			}
			// 校验收款账号
			if (!StringUtil.isNotNull(data.get("收款账号"))) {
				log.error("第[" + (i + 1) + "]行的收款账号不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的收款账号不能为空！");
			} else {
				Pattern pattern = Pattern.compile("^\\d{13,19}$");// 必须为13到19位数字
				if (pattern.matcher(data.get("收款开户行行号")).matches()) {
					log.error("第[" + (i + 1) + "]行的收款账号错误！");
					eMsg.append("第[" + (i + 1) + "]行的收款账号错误！");
				}
			}
			// 校验收款户名
			if (!StringUtil.isNotNull(data.get("收款户名"))) {
				log.error("第[" + (i + 1) + "]行的收款户名不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的收款户名不能为空！");
			}
			// 校验收款金额
			if (!StringUtil.isNotNull(data.get("收款金额"))) {
				log.error("第[" + (i + 1) + "]行的收款金额不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的收款金额不能为空！");
			}

			if (StringUtil.isNotNull(eMsg)) {
				throw new Exception(eMsg.toString());
			}
			resultDate.add(data);
		}
		return resultDate;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

}
