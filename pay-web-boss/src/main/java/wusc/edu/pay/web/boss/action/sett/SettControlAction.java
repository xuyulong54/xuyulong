package wusc.edu.pay.web.boss.action.sett;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.service.SettManagementFacade;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 
 * Desc: 结算控制
 * @author lichao
 * Date: 2014-8-5
 *
 */
public class SettControlAction  extends BossBaseAction  {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SettManagementFacade settManagementFacade ;
	@Autowired
	private SettQueryFacade settQueryFacade;
	
	
	/**
	 * 分页查询结算控制
	 */
	@Permission("sett:control:view")
	public String listSettControl(){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer settModeType = getInteger("settType");
		map.put("settModeType", settModeType);
		PageBean listSettControl = settQueryFacade.querySettCortrolListPage(this.getPageParam(),map);
		this.pushData(listSettControl);
		this.putData("settType", settModeType);
		this.putData("SettModeTypeEnum", SettModeTypeEnum.toList());
		return "listSettControl";
	}
	
	/**
	 * 新增结算控制数据（UI）
	 */
	public String addSettControlUI(){
		this.putData("SettModeTypeEnum", SettModeTypeEnum.toList());
		return "addSettControlUI";
	}

	/**
	 * 新增结算控制数据（settModeType唯一）
	 * @throws ParseException 
	 */
	@Permission("sett:control:add")
	public String addSettControl() throws ParseException{
		String beginTimes = getString("beginTime");
		String endTimes = getString("endTime");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date beginTime = sdf.parse(beginTimes);
		Date endTime = sdf.parse(endTimes);
		Integer settModeType = getInteger("settModeType");
		SettControl settControl = settQueryFacade.getBySettModeType(settModeType);
		if(settControl == null){
			SettControl sc = new SettControl();
			sc.setBeginTime(beginTime);
			sc.setEndTime(endTime);
			sc.setSettModeType(settModeType);
			settManagementFacade.createSettControl(sc);
			this.logSave("创建结算控制，汇总开始时间："+beginTime+"，汇总结束时间："+endTime);
			return this.operateSuccess("添加成功！");
		}else{
			return this.operateError("已存在该结算控制方式！");
		}
		
	}
	
	/**
	 * 修改结算系统控制数据，只修改每日汇总时间段的起止时间。UI
	 */
	public String updateSettControlUI(){
		Integer settModeType = getInteger("settModeType");
		SettControl settControl = settQueryFacade.getBySettModeType(settModeType);
		this.pushData(settControl);
		this.putData("SettModeTypeEnum", SettModeTypeEnum.toList());
		return "updateSettControlUI";
	}
	
	/**
	 * 修改结算系统控制数据，只修改每日汇总时间段的起止时间。
	 * @throws ParseException 
	 */
	@Permission("sett:control:edit")
	public String updateSettControl() throws ParseException{
		String beginTimes = getString("beginTime");
		String endTimes = getString("endTime");
		Integer settModeType = getInteger("settModeType");
		SettControl settControl = settQueryFacade.getBySettModeType(settModeType);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date beginTime = sdf.parse(beginTimes);
		Date endTime = sdf.parse(endTimes);
		settControl.setBeginTime(beginTime);
		settControl.setEndTime(endTime);
		settManagementFacade.updateSettControl(settControl);
		this.logEdit("修改结算控制，汇总开始时间："+beginTime+"，汇总结束时间："+endTime);
		return this.operateSuccess("修改成功！");
	}

}
