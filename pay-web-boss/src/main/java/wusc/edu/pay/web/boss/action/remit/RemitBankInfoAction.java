package wusc.edu.pay.web.boss.action.remit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.remit.entity.RemitBankInfo;
import wusc.edu.pay.facade.remit.enums.BankTypeEnum;
import wusc.edu.pay.facade.remit.enums.IsInProvinceEnum;
import wusc.edu.pay.facade.remit.service.RemitBankAreaFacade;
import wusc.edu.pay.facade.remit.service.RemitBankInfoFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * @Title: 银行信息管理ACTION
 * @Description: 
 * @author zzh
 * @date 2014-7-29 下午1:57:57
 */
public class RemitBankInfoAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RemitBankInfoAction.class);
	@Autowired
	private RemitBankInfoFacade remitBankInfoFacade;
	@Autowired
	private RemitBankAreaFacade remitBankAreaFacade;
	
	private File remitBankInfoFile;// 上传文件本身
	
	/**
	 * @Title: 查询银行信息 
	 * @Description: 
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@Permission("boss:remitBankInfo:view")
	public String remitBankInfoList(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("isInProvince", getInteger("isInProvince"));
		paramMap.put("bankChannelNo", getString("bankChannelNo"));
		paramMap.put("bankName", getString("bankName"));
		paramMap.put("clearBankChannelNo", getString("clearBankChannelNo"));
		super.pageBean = remitBankInfoFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("bankTypeEnumList", BankTypeEnum.toList());
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("isInProvinceEnumList", IsInProvinceEnum.toList());
		return "remitBankInfoList";
	}
	
	/**
	 * @Title: 银行信息添加页面 
	 * @Description: 
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@Permission("boss:remitBankInfo:add")
	public String remitBankInfoAdd(){
		this.putData("bankTypeEnumList", BankTypeEnum.toList());
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("isInProvinceEnumList", IsInProvinceEnum.toList());
		this.putData("provinceList", remitBankAreaFacade.getProvince());
		return "remitBankInfoAdd";
	}
	
	/**
	 * @Title: 银行信息添加保存 
	 * @Description: 
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@Permission("boss:remitBankInfo:add")
	public String remitBankInfoSave(){
		RemitBankInfo remitBankInfo = new RemitBankInfo();	
		try{
			remitBankInfo.setBankChannelNo(getString("bankChannelNo"));
			remitBankInfo.setBankName(getString("bankName"));
			remitBankInfo.setBankType(getInteger("bankType"));
			remitBankInfo.setCity(getString("city"));
			remitBankInfo.setClearBankChannelNo(getString("clearBankChannelNo"));
			remitBankInfo.setIsInProvince(getInteger("isInProvince"));
			remitBankInfo.setProvince(getString("province"));
			remitBankInfoFacade.create(remitBankInfo);
			log.info("====info==== 创建银行信息成功：bankChannelNo："+remitBankInfo.getBankChannelNo()+",bankName:"+remitBankInfo.getBankName()+",bankType:"+remitBankInfo.getBankType());
			this.logSave("添加银行信息：银行行号："+remitBankInfo.getBankChannelNo()+"，银行名称："+remitBankInfo.getBankName());
			return operateSuccess();
		}catch(Exception e){
			this.logSaveError("添加银行信息：银行行号："+remitBankInfo.getBankChannelNo()+"，银行名称："+remitBankInfo.getBankName());
			log.error("====error==== 创建银行信息出错RemitBankInfoAction_remitBankInfoSave",e);
			return operateError("保存出错");
		}
	}
	
	/**
	 * @Title: 银行信息修改页面 
	 * @Description: 
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@Permission("boss:remitBankInfo:edit")
	public String remitBankInfoEdit(){
		Long id = getLong("id");
		RemitBankInfo remitBankInfo = remitBankInfoFacade.getById(id);
		this.putData("bankTypeEnumList", BankTypeEnum.toList());
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("isInProvinceEnumList", IsInProvinceEnum.toList());
		this.putData("provinceList", remitBankAreaFacade.getProvince());
		this.putData("cityList", remitBankAreaFacade.getCityByProvince(remitBankInfo.getProvince()));
		this.pushData(remitBankInfo);
		return "remitBankInfoEdit";
	}
	
	/**
	 * @Title: 银行信息修改保存 
	 * @Description: 
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@Permission("boss:remitBankInfo:edit")
	public String remitBankInfoUpdate(){
		Long id = getLong("id");
		RemitBankInfo remitBankInfo = remitBankInfoFacade.getById(id);
		try{
			remitBankInfo.setBankChannelNo(getString("bankChannelNo"));
			remitBankInfo.setBankName(getString("bankName"));
			remitBankInfo.setBankType(getInteger("bankType"));
			remitBankInfo.setCity(getString("city"));
			remitBankInfo.setClearBankChannelNo(getString("clearBankChannelNo"));
			remitBankInfo.setIsInProvince(getInteger("isInProvince"));
			remitBankInfo.setProvince(getString("province"));
			remitBankInfoFacade.update(remitBankInfo);
			log.info("====info==== 更新银行信息成功：bankChannelNo："+remitBankInfo.getBankChannelNo()+",bankName:"+remitBankInfo.getBankName()+",bankType:"+remitBankInfo.getBankType());
			this.logEdit("修改银行信息：银行行号："+remitBankInfo.getBankChannelNo()+"，银行名称："+remitBankInfo.getBankName());
			return operateSuccess();
		}catch(Exception e){
			this.logEditError("修改银行信息：银行行号："+remitBankInfo.getBankChannelNo()+"，银行名称："+remitBankInfo.getBankName());
			log.error("====error==== 更新银行信息出错RemitBankInfoAction_remitBankInfoUpdate",e);
			return operateError("更新失败");
		}
	}
	
	/**
	 * @Title: 根据ID删除银行信息 
	 * @Description: 
	 * @param     
	 * @return void  
	 * @throws
	 */
	@Permission("boss:remitBankInfo:delete")
	public void deleteRemitBankInfo(){
		Long id = getLong("id");
		try{
			if(id!=null){
				remitBankInfoFacade.deleteById(id);
				this.logDelete("删除银行信息，id为"+id);
				log.info("====info==== 删除银行信息成功，id为"+id);
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "删除成功");
			}
			
		}catch(Exception e){
			this.logDeleteError("删除银行信息，id为"+id);
			log.error("====error==== 删除银行信息失败RemitBankInfoAction_deleteRemitBankInfo",e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "删除失败");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}
	
	/**
	 * @Title: 银行信息导入页面 
	 * @Description: 
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@Permission("boss:remitBankInfo:add")
	public String remitBankInfoUpload(){
		return "remitBankInfoUpload";
	}
	
	/**
	 * @Title: 银行信息导入保存 
	 * @Description: 
	 * @param     
	 * @return void  
	 * @throws
	 */
	public void remitBankInfoUploadSave(){
		System.out.println(remitBankInfoFile);
	}

	/**
	 * @return 上传文件本身
	 */
	public File getRemitBankInfoFile() {
		return remitBankInfoFile;
	}

	/**
	 * @param 上传文件本身
	 */
	public void setRemitBankInfoFile(File remitBankInfoFile) {
		this.remitBankInfoFile = remitBankInfoFile;
	}
	
	
	
}
