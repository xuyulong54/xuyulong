package wusc.edu.pay.web.boss.action.cal;

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
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.service.CalCostInterfaceFacade;
import wusc.edu.pay.facade.cost.service.CalDimensionFacade;
import wusc.edu.pay.facade.cost.service.CalFeeWayFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 计费维度
 */
public class CalDimensionAction extends BossBaseAction {

	private static final long serialVersionUID = -5137034289343272476L;
	private static final Log log = LogFactory.getLog(CalDimensionAction.class);
	
	@Autowired
	private CalDimensionFacade calDimensionFacade;
	
	@Autowired
	private BankChannelFacade bankChannelFacade;
	
	@Autowired
	private CalFeeWayFacade calFeeWayFacade;
	
	@Autowired
	private CalCostInterfaceFacade calCostInterfaceFacade;
	
	/**
	 * 计费维度查询
	 */
	@Permission("boss:calDimension:view")
	public String calDimensionList(){
		try{
			Map<String, Object> calDimensionMap = new HashMap<String, Object>();
			calDimensionMap.put("calProduct", getString("calProduct"));
			calDimensionMap.put("calCostInterfaceCode", getString("calCostInterfaceCode"));
			List<BankChannel> channelList =  bankChannelFacade.listBy(calDimensionMap);
			List<CalCostInterface> calCostInterfaceList = calCostInterfaceFacade.listAll();
			super.pageBean =calDimensionFacade.listPage(this.getPageParam(), calDimensionMap);
			this.pushData(pageBean);
			this.pushData(calDimensionMap);
			this.putData("channelList", channelList);
			this.putData("calCostInterfaceList", calCostInterfaceList);
		}catch(Exception e){
			log.error("==calDimensionList Exception",e);
		}
		return "CalDimensionList";
	}
	
	/**
	 * 计费维度添加
	 */
	@Permission("boss:calDimension:add")
	public String calDimensionAdd(){
		try{
			Map<String, Object> calDimensionMap = new HashMap<String, Object>();
			List<BankChannel> channelList =  bankChannelFacade.listBy(calDimensionMap);
			List<CalCostInterface> calCostInterfaceList = calCostInterfaceFacade.listAll();
			this.putData("calCostInterfaceList", calCostInterfaceList);
			this.putData("channelList", channelList);
		}catch(Exception e){
			log.error("== calDimensionAdd Exception",e);
		}
		return "CalDimensionAdd";
	}
	
	/**
	 * 计费维度添加保存
	 */
	@Permission("boss:calDimension:add")
	public String calDimensionSave(){
		CalDimension calDimension = new CalDimension();
		if(getString("calCostInterfaceCode")!=null){
			CalCostInterface calCostInterface = calCostInterfaceFacade.getByCalCostInterfaceCode(getString("calCostInterfaceCode"));
			if(calCostInterface!=null){
				calDimension.setCalCostInterfaceCode(calCostInterface.getInterfaceCode());
				calDimension.setCalProduct(getString("calProduct"));
			}
		
			calDimension.setCreateTime(new Date());
			long i = calDimensionFacade.create(calDimension);
			if(0==i){
				return operateError("不能添加重复的维度");
			}
			this.logSave("添加计费维度，计费接口编码："+calCostInterface.getInterfaceCode());
			return operateSuccess();
		}else{
			return operateError("请选择接口");
		}
		
	}
	
	/**
	 * 计费维度修改
	 */
	@Permission("boss:calDimension:edit")
	public String editCalDimension(){
		Integer id = getInteger("id");
		Map<String, Object> calDimensionMap = new HashMap<String, Object>();
		CalDimension calDimension = calDimensionFacade.getById(id);
		List<BankChannel> channelList =  bankChannelFacade.listBy(calDimensionMap);
		List<CalCostInterface> calCostInterfaceList = calCostInterfaceFacade.listAll();
		this.putData("calCostInterfaceList", calCostInterfaceList);
		this.pushData(calDimension);
		this.putData("channelList", channelList);
		return "CalDimensionEdit";
	}
	
	/**
	 * 计费维度修改保存
	 */
	@Permission("boss:calDimension:edit")
	public String calDimensionUpdate(){
		Integer id = getInteger("id");
		CalDimension calDimension = calDimensionFacade.getById(id);
		if(getString("calCostInterfaceCode")!=null){
			CalCostInterface calCostInterface = calCostInterfaceFacade.getByCalCostInterfaceCode(getString("calCostInterfaceCode"));
			if(calCostInterface!=null){
				calDimension.setCalCostInterfaceCode(calCostInterface.getInterfaceCode());
				calDimension.setCalProduct(getString("calProduct"));
			}
			long i = calDimensionFacade.update(calDimension);
			if(0==i){
				return operateError("该维度已重复");
			}
			this.logEdit("修改计费维度，计费接口编码："+calCostInterface.getInterfaceCode());
			return operateSuccess();
		}else{
			return operateError("请选择接口");
		}
		
	}
	
	/**
	 * 计费维度删除
	 */
	@Permission("boss:calDimension:delete")
	public void deleteCalDimension(){
		try{
			Integer id = getInteger("id");
			//TODO:要加维度关联校验，维度下面有约束不能删除
			List<CalFeeWay> calFeeWayList = calFeeWayFacade.getByDimensionId(id );
			if(calFeeWayList.size()>0){
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "维度下面有约束不能删除");
			}else{
				calDimensionFacade.deleteById(id);
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "删除成功");
				this.logEdit("修改计费维度，计费维度ID："+id);
			}
			
		}catch(Exception e){
			log.error("== deleteCalDimension Exception",e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "删除失败");
			this.logEditError("修改计费维度，计费维度ID："+getInteger("id"));
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}
}
