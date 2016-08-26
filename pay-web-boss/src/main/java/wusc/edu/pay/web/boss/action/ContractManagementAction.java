package wusc.edu.pay.web.boss.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.ContractFilePropertiesEnum;
import wusc.edu.pay.common.enums.ContractTypeEnum;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.common.web.file.FastDFSClient;
import wusc.edu.pay.facade.bank.service.BankAgreementFacade;
import wusc.edu.pay.facade.boss.entity.ContractManagement;
import wusc.edu.pay.facade.boss.service.ContractManagementFacade;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.permission.entity.PmsOperator;

/***
 * 
 * @描述: 文件管理.
 * @作者: Lanzy.
 * @创建时间: 2014-4-9, 上午11:00:27 .
 * @版本: V1.0.
 *
 */
public class ContractManagementAction extends BossBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3025268276731797870L;
	
	private static Log log = LogFactory.getLog(ContractManagementAction.class);
	@Autowired
	private ContractManagementFacade contractManagementFacade;
	@Autowired
	private BankAgreementFacade bankAgreementFacade;
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	private File file;// 上传文件本身
	private String fileFileName;// 上传文件的原始文件名
	private String fileContentType;// 上传文件的文件类型
	
	// 10M
	public final int MAX_FILE_SIZE = 1024 * 1024 * 10;
	
	/**
	 * 查看文件列表
	 */
	@Permission("boss:contract:view")
	public String listContract(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", getString("userName"));//商户名称或者银行接口名称
		paramMap.put("contractNo", getString("contractNo"));//文件编号
		paramMap.put("contractType", getString("contractType"));//文件类型
		paramMap.put("creater", getString("creater"));//创建人
		paramMap.put("fileProperties", getString("fileProperties"));//文件性质
		super.pageBean = contractManagementFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("contractTypeList", ContractTypeEnum.toList()); //文件类型
		this.putData("filePropertiesList", ContractFilePropertiesEnum.toList());//文件性质
		return "listContract";
	}
	
	/**
	 * 查看文件详情
	 */
	@Permission("boss:contract:view")
	public String viewContract(){
		Long id = getLong("id");
		ContractManagement contractManagement = contractManagementFacade.getById(id);
		this.pushData(contractManagement);
		this.putData("contractTypeList", ContractTypeEnum.toList()); //文件类型
		this.putData("filePropertiesList", ContractFilePropertiesEnum.toList());//文件性质
		return "viewContract";
	}
	
	/**
	 * 上传文件UI
	 */
	@Permission("boss:contract:add")
	public String addContractUI(){
		this.putData("contractTypeList", ContractTypeEnum.toList());//文件类型
		this.putData("filePropertiesList", ContractFilePropertiesEnum.toList());//文件性质
		return "addContract";
	}
	/**
	 * 上传文件
	 */
	@Permission("boss:contract:add")
	public String addContractFile(){
		boolean flag = false;
	    String message = null;
		PmsOperator operator = getLoginedOperator();
		String creater = operator.getRealName();
		String userNo = getString("merchantId");//TODO 这个是商户ID或者银行序号，命名有待规范
		String merchantName = getString("merchantName");
		Integer contractType = getInteger("contractType");
		String bankNo = getString("bankId");
		String bankName = getString("bankName");
		Integer fileProperties = getInteger("fileProperties");
		String signTime = getString("signTime");
		String contractValid = getString("contractValid");
		if(merchantName==null && bankName==null){
			return this.operateError("请选择文件类型、商户名称或者银行协议");
		}
		HttpServletResponse response = getHttpResponse();
		response.setContentType("text/html"); 
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
		try {
			out = response.getWriter();
			if (validateUploadFile(file,fileFileName)) {
				String contractFile = FastDFSClient.uploadFile(file, fileFileName);//文件地址
				if(contractFile!=null){
					String contractNo = "";
					StringBuffer relationNumber = new StringBuffer();
					ContractManagement CMParam = new ContractManagement();//文件实体
					CMParam.setContractFile(contractFile);//文件地址
					CMParam.setContractType(contractType);//文件类型
					CMParam.setCreater(operator.getRealName());//创建人
					CMParam.setFileName(fileFileName);//原文件名
					CMParam.setFileProperties(fileProperties);//文件性质
					CMParam.setRemark(getString("desc"));//描述
					
					if(contractType==ContractTypeEnum.MERCHANT.getValue()){//文件类型是商户
						CMParam.setUserName(merchantName);//商户名称
						CMParam.setUserNo(userNo);//商户ID
						List<ContractManagement> merchantList = contractManagementFacade.getByMerchantId(userNo);
						relationNumber=validateRelationNumber(merchantList,fileProperties);//文件编号拼接(自增)
						contractNo = ContractTypeEnum.MERCHANT.getValue()+getString("merchantNo")+relationNumber;
						CMParam.setContractNo(contractNo);//文件编号
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						CMParam.setSignTime(sdf.parse(signTime));
						CMParam.setContractValid(sdf.parse(contractValid));
						
						contractManagementFacade.create(CMParam);
						
						super.logSave(creater + "上传了 "+ fileFileName +" 文件" + "文件编号为：" + contractNo);
					}else if(contractType==ContractTypeEnum.BANK.getValue()){//文件类型是银行
						List<ContractManagement> bankList = contractManagementFacade.getByMerchantId(bankNo);
						relationNumber=validateRelationNumber(bankList,fileProperties);//文件编号拼接(自增)
						contractNo = ContractTypeEnum.BANK.getValue()+getString("bankNo")+relationNumber;
						CMParam.setUserNo(bankNo);
						CMParam.setUserName(bankName);
						CMParam.setContractNo(contractNo);//文件编号
						contractManagementFacade.create(CMParam);
						
						super.logSave(creater + "上传了 "+ fileFileName +" 文件" + "文件编号为：" + contractNo);
					}
					flag = true;
					message="上传成功！";
				}else{
					flag = false;
					message="上传文件失败！";
				}
			} else {
				flag = false;
				message="上传的文件文件不能为大于10M,且后缀名必须为zip、rar、doc、docx！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.logSaveError(creater + "上传 "+ getString("merchantListDiv.merchantName") +" 文件失败" + "文件编号为：" + getString("contractNo"));
			flag = false;
			message="上传失败，请联系管理员！";
		}
		out.write("<script>parent.callback('"+flag+"','"+message+"')</script>"); 
		out.close();
		return null;
	}
	
	/**
	 * 文件编号拼接
	 */
	public StringBuffer validateRelationNumber(List<ContractManagement> merchantList, int fileProperties){
		StringBuffer relationNumber = new StringBuffer();
		if(fileProperties<10){
			relationNumber.append("0"+fileProperties);
		}else{
			relationNumber.append(fileProperties);
		}
		if(merchantList == null|| merchantList.size() <=0){//空则从001开始
			relationNumber.append("001");
		}else if(merchantList.size()<10){//小于10则加00
			relationNumber.append("00"+(merchantList.size()+1)) ;
		}else if(merchantList.size()<100){//小于100则加0
			relationNumber.append("0"+(merchantList.size()+1));
		}else if(merchantList.size()<1000){//小于1000则加“”
			relationNumber.append(""+(merchantList.size()+1));
		}else{
			return relationNumber;
		}
		return relationNumber;
	}
	
	/**
	 * 文件格式校验
	 */
	public boolean validateUploadFile(File file,String fileFileName) {
		
		if (file.length() > MAX_FILE_SIZE) {
			//上传的文件文件不能大于10M
			return false;
		}
		String lastName = fileFileName.substring(fileFileName.lastIndexOf(".")+1);
		if (!"rar".equals(lastName) && !"zip".equals(lastName) 
				&& !"doc".equals(lastName) && !"docx".equals(lastName)){
			//上单的文件文件必须是zip、rar、doc为后缀名的
			return false;
		}
		return true;
	}
	
	/**
	 * 银行协议的查找带回
	 */
	public String listBankAgreement(){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("interfaceName", getString("interfaceName")); // 接口名称
		super.pageBean = bankAgreementFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "listBankAgreement";
	}
	
	/**
	 * 在线商户信息的查找带回
	 * @return merchantLookupList .
	 */
	public  String onlineMerchantLookupList(){
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("merchantNo", getString("merchantNo")); //商户编号
			paramMap.put("fullName", getString("fullName")); //商户全称
			super.pageBean = merchantOnlineFacade.listMerchantListPage(this.getPageParam(), paramMap);
			pushData(pageBean);
			putData("MerchantStatusEnum", MerchantStatusEnum.values());
			putData("merchantNo",getString("merchantNo"));
			return "merchantLookupList";
		} catch (Exception e) {
			log.error("== merchantLookupList get data exception:", e);
			return operateError("获取商户信息失败");
		}
	}
	/**
	 * 在商户页面的下载功能的页面跳转
	 * @return
	 */
	@Permission("boss:contract:download")
	public String fileDownLoad(){
		String relationId = getString("id");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("relationId", relationId);//商户Id
		paramMap.put("contractNo", getString("contractNo"));//文件编号
		paramMap.put("contractType", getString("contractType"));//文件类型
		paramMap.put("creater", getString("creater"));//创建人
		paramMap.put("fileProperties", getString("fileProperties"));//文件性质
		super.pageBean = contractManagementFacade.listPage(getPageParam(), paramMap);
		this.putData("contractTypeList", ContractTypeEnum.toList()); //文件类型
		this.putData("filePropertiesList", ContractFilePropertiesEnum.toList());//文件性质
		super.logQuery("下载文件信息,文件编号["+getString("contractNo")+"],文件类型["+getString("contractType")+"]");
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "fileDownLoad";
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
}
