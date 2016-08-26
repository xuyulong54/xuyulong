package wusc.edu.pay.web.boss.action.sett;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.settlement.service.SettManagementFacade;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/*
 * 结算假日管理
 */
public class SettHolidaySettingAction extends BossBaseAction {
	private static final long serialVersionUID = 2759712929164416036L;

	@Autowired
	private SettQueryFacade settQueryFacade;

	@Autowired
	private SettManagementFacade settManagementFacade;

	/**
	 * 分页查询节假日
	 * 
	 * @return
	 */
	@Permission("sett:holidaysetting:view")
	public String listSettHolidaySetting() {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String holiday = getString("holiday");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("holiday",holiday);
		super.pageBean = settQueryFacade.querySettHolidayListPage(this.getPageParam(), map);
		this.pushData(pageBean);
		return "listSettHoliday";
	}

	/**
	 * 到添加节假日的页面
	 * 
	 * @return
	 */
	public String addSettHolidayUI() {

		return "addSettHolidayUI";
	}

	/**
	 * 添加节假日方法
	 * 
	 * @return
	 */
	@Permission("sett:holidaysetting:add")
	public String addSettHoliday() {
		// 如果节假日
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		Date endDate;
		try {
			startDate = sdf.parse(getString("startDate"));// 节假日的开始时间
			endDate = sdf.parse(getString("endDate"));
		} catch (ParseException e) {
			return operateError("假日时间转换错误");
		}// 假日的结束时间
		String remark =getString("remark");//节假日的描述
		settManagementFacade.createSettHolidaySetting(startDate, endDate, remark);
		this.logSave("添加节假日，开始时间："+startDate+"，结束时间："+endDate);
		return operateSuccess();
	}
	
	/**
	 * 删除节假日
	 * @return
	 */
	@Permission("sett:holidaysetting:delete")
	public String deleteSettHoliday(){
		String id = getString("id");
		String [] ids = {id};
		settManagementFacade.delSettlementHoliday(ids);
		this.logDelete("删除节假日，ID："+id);
		return operateSuccess("删除成功！");
	}

}
