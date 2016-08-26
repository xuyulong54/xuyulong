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
 * 结算日调整管理
 */
public class SettDayAdjustSettingAction extends BossBaseAction {
	private static final long serialVersionUID = 2759712929164416036L;

	@Autowired
	private SettQueryFacade settQueryFacade;

	@Autowired
	private SettManagementFacade settManagementFacade;
	
	/**
	 * 分页查询结算日调整
	 * @return
	 */
	@Permission("sett:dayadjustsetting:view")
	public String listSettDayAdjustSetting() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oldDate", getString("oldDate"));
		map.put("newDate", getString("newDate"));
		super.pageBean = settQueryFacade.querySettDayAdjustSettingListPage(this.getPageParam(), map);
		this.pushData(pageBean);
		return "listSettDayAdjustSetting";
	}
	
	/**
	 * 添加结算日调整--UI
	 * @return
	 */
	public String addSettDayAdjustSettingUI(){
		return "addSettDayAdjustSettingUI";
	}
	
	/**
	 * 添加结算日--功能
	 * @return
	 */
	@Permission("sett:dayadjustsetting:add")
	public String addSettDayAdjustSetting(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date oldDate;//结算原始日期
		Date newDate;
		try {
			oldDate = sdf.parse(getString("oldDate"));
			newDate = sdf.parse(getString("newDate"));
		} catch (ParseException e) {
			return operateError("结算时间转换错误");
		}
		String remark =getString("remark");
		settManagementFacade.addAdjustSettlementDay(oldDate, newDate, remark);
		this.logSave("增加结算日调整，原结算日："+oldDate+"，改为："+newDate);
		return operateSuccess();
	}
	
	/**
	 * 删除
	 * @return
	 */
	@Permission("sett:dayadjustsetting:delete")
	public String delSettDayAdjustSetting(){
		String id = getString("id");
		String[] ids = {id};
		settManagementFacade.delAdjustSettlementDay(ids);
		this.logDelete("删除结算日调整，ID："+id);
		return this.operateSuccess("删除成功");
	}
}
