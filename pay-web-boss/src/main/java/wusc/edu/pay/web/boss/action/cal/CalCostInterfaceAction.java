package wusc.edu.pay.web.boss.action.cal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.bank.entity.BankChannel;
import wusc.edu.pay.facade.bank.service.BankChannelFacade;
import wusc.edu.pay.facade.cost.entity.CalCostInterface;
import wusc.edu.pay.facade.cost.entity.CalDimension;
import wusc.edu.pay.facade.cost.enums.BillingCycleEnum;
import wusc.edu.pay.facade.cost.enums.CostBillCycleEnum;
import wusc.edu.pay.facade.cost.enums.CostInterfaceChargeTypeEnum;
import wusc.edu.pay.facade.cost.enums.CostInterfacePolicyEnum;
import wusc.edu.pay.facade.cost.enums.CostInterfaceStatusEnum;
import wusc.edu.pay.facade.cost.service.CalCostInterfaceFacade;
import wusc.edu.pay.facade.cost.service.CalDimensionFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 计费接口Action
 */
public class CalCostInterfaceAction extends BossBaseAction{

	private static final long serialVersionUID = 1051697126591020323L;
	private static final Log log = LogFactory.getLog(CalCostInterfaceAction.class);

	@Autowired 
	private CalCostInterfaceFacade calCostInterfaceFacade;
	@Autowired
	private CalDimensionFacade calDimensionFacade;
	@Autowired
	private BankChannelFacade bankChannelFacade;
	
	@Permission("boss:calCostInterface:view")
	public String calCostInterfaceList(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interfaceCode", getString("interfaceCode"));
		paramMap.put("interfaceName", getString("interfaceName"));
		super.pageBean = calCostInterfaceFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("costInterfaceChargeTypeEnumList", CostInterfaceChargeTypeEnum.toList());
		this.putData("costBillCycleEnumList", CostBillCycleEnum.toList());
		this.putData("costInterfaceStatusEnumList", CostInterfaceStatusEnum.toList());
		this.putData("costInterfacePolicyEnumList", CostInterfacePolicyEnum.toList());
		return "calCostInterfaceList";
	}
	
	@Permission("boss:calCostInterface:add")
	public String calCostInterfaceAdd(){
		List<BankChannel> channelList =  bankChannelFacade.listBy(new HashMap<String, Object>());
		this.putData("costInterfaceChargeTypeEnumList", CostInterfaceChargeTypeEnum.toList());
		this.putData("costBillCycleEnumList", CostBillCycleEnum.toList());
		this.putData("costInterfaceStatusEnumList", CostInterfaceStatusEnum.toList());
		this.putData("costInterfacePolicyEnumList", CostInterfacePolicyEnum.toList());
		this.putData("channelList", channelList);
		return "calCostInterfaceAdd";
	}
	
	@Permission("boss:calCostInterface:add")
	public String calCostInterfaceSave(){
		CalCostInterface calCostInterface=new CalCostInterface();	
		try{
			calCostInterface.setInterfaceCode(getString("interfaceCode"));
			calCostInterface.setInterfaceName(getString("interfaceName"));
			calCostInterface.setChargeType(getInteger("chargeType"));
			calCostInterface.setStatus(getInteger("status"));
			calCostInterface.setPolicy(getInteger("policy"));
			calCostInterface.setBillCycle(getInteger("billCycle"));
			calCostInterface.setCustomBillCycle(getInteger("customBillCycle"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(getString("customBillDay")!=null){
				calCostInterface.setCustomBillDay(format.parse(getString("customBillDay")));
			}
			calCostInterface.setModifyTime(new Date());
			calCostInterface.setRemark(getString("remark"));
			calCostInterfaceFacade.create(calCostInterface);
			this.logSave("添加计费接口："+getString("interfaceName"));
			return operateSuccess();
		}catch(Exception e){
			log.error("==calCostInterfaceSave Exception",e);
			this.logSaveError("添加计费接口："+getString("interfaceName"));
			return operateError("保存出错");
		}
	}
	
	@Permission("boss:calCostInterface:edit")
	public String calCostInterfaceEdit(){
		Long id = getLong("id");
		CalCostInterface calCostInterface = calCostInterfaceFacade.getById(id);
		this.putData("costInterfaceChargeTypeEnumList", CostInterfaceChargeTypeEnum.toList());
		this.putData("costBillCycleEnumList", CostBillCycleEnum.toList());
		this.putData("costInterfaceStatusEnumList", CostInterfaceStatusEnum.toList());
		this.putData("costInterfacePolicyEnumList", CostInterfacePolicyEnum.toList());
		this.pushData(calCostInterface);
		return "calCostInterfaceEdit";
	}
	
	@Permission("boss:calCostInterface:edit")
	public String calCostInterfaceUpdate(){
		Long id = getLong("id");
		CalCostInterface calCostInterface = calCostInterfaceFacade.getById(id);
		try{
			calCostInterface.setInterfaceCode(getString("interfaceCode"));
			calCostInterface.setInterfaceName(getString("interfaceName"));
			calCostInterface.setChargeType(getInteger("chargeType"));
			calCostInterface.setStatus(getInteger("status"));
			calCostInterface.setPolicy(getInteger("policy"));
			calCostInterface.setBillCycle(getInteger("billCycle"));
			if(getInteger("billCycle")!=null&&(BillingCycleEnum.CUSTOM.getValue() == getInteger("billCycle"))){
				calCostInterface.setCustomBillCycle(getInteger("customBillCycle"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				if(getString("customBillDay")!=null){
					calCostInterface.setCustomBillDay(format.parse(getString("customBillDay")));
				}
			}
			calCostInterface.setModifyTime(new Date());
			calCostInterface.setRemark(getString("remark"));
			calCostInterfaceFacade.update(calCostInterface);
			this.logEdit("修改计费接口："+getString("interfaceName"));
			return operateSuccess();
		}catch(Exception e){
			log.error("==calCostInterfaceUpdate Exception",e);
			this.logEditError("修改计费接口："+getString("interfaceName"));
			return operateError("更新失败");
		}
	}
	
	/**
	 * 计费接口删除
	 */
	public void deleteCalCostInterface(){
		try{
			String calCostInterfaceCode = getString("calCostInterfaceCode");
			//TODO:要加维度关联校验，接口关联到维度后不能删除
			List<CalDimension> calDimensionList = calDimensionFacade.getByCostInterfaceCode(calCostInterfaceCode);
			if(calDimensionList.size()>0){
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "接口已关联到维度，不能删除");
			}else{
				calCostInterfaceFacade.deleteByCalCostInterfaceCode(calCostInterfaceCode);
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "删除成功");
				this.logDelete("删除计费接口，计费接口编码："+ calCostInterfaceCode);
			}
			
		}catch(Exception e){
			log.error("==deleteCalCostInterface Exception",e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "删除失败");
			this.logDeleteError("删除计费接口，计费接口编码："+ getString("calCostInterfaceCode"));
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}
	
	/**
	 * 获取渠道
	 */
	public void getChannelList(){
		if(getString("costInterfaceType").equals("bank")){
			List<BankChannel> channelList =  bankChannelFacade.listBy(new HashMap<String, Object>());
			getOutputMsg().put("channelList", channelList);
		}else{
			
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}
	
	/**
	 * 修改接口状态
	 */
	public void changeInterfaceStatus(){
		long id = getLong("id");
		Integer status = getInteger("status");
		CalCostInterface calCostInterface = calCostInterfaceFacade.getById(id);
		calCostInterface.setStatus(status);
		calCostInterfaceFacade.update(calCostInterface);
		getOutputMsg().put("STATE", "SUCCESS");
		getOutputMsg().put("MSG", "修改成功");
		this.logEditError("修改接口状态，计费接口id："+ id);
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}
}
