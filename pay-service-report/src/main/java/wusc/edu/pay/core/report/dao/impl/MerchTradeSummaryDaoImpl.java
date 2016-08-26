package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.MerchTradeSummaryDao;
import wusc.edu.pay.facade.report.entity.MerchTradeSummary;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;


/***
 * 代理商利润统计报表 dao 接口
 * 
 * @author Administrator
 * 
 */
@Repository("merchTradeSummaryDao")
public class MerchTradeSummaryDaoImpl extends BaseDaoImpl<MerchTradeSummary> implements MerchTradeSummaryDao {

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat fieldFormat = new SimpleDateFormat("yyyyMMdd");

	/***
	 * 代理商分润明细报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_profit_detail(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "listPage_Agent_Profit_Detail");
	}

	/***
	 * 收益统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_profit_count_report(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "listPage_Profit_Count_Report");
	}

	public int createMerchTransSummary(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.MERCH_TRADE_SUMMARY_REPORT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", fieldFormat.format(dealDate));
		return getSessionTemplate().insert("createMerchTransSummary", param);
	}

	public int deleteSummaryByDate(Date dealDate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("date", dateFormat.format(dealDate));
		return getSessionTemplate().delete("deleteSummaryByDate", param);
	}

	/***
	 * pos交易汇总报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean posTransSumReport(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "posTransSumReport");
	}

	/**
	 * pos交易汇总报表统计
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getposTransSumReportCount(Map<String, Object> paramMap) {
		return this.getSessionTemplate().selectList(getStatement("getposTransSumReportCount"), paramMap);
		//return getSessionTemplate().selectOne(this.getStatement("getposTransSumReportCount"), paramMap);
	}

	/***
	 * 统计销售人员拓展的代理商个数
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_sales_report(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "listPage_sales_report");
	}

	/***
	 * 商户清算报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_sett_report(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "listPage_merchant_sett_report");
	}

	/***
	 * 代理商系统的 收益统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_plat_profit_report(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "listPage_agent_plat_profit_report");
	}

	/***
	 * 代理商系统-商户交易统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_trade_report(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "listPage_merchant_trade_report");
	}

	/***
	 * 代理商分润统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_profit_by_agent_report(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "listPage_profit_by_agent_report");
	}

	/**
	 * 统计商户清算报表
	 * 
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getCount_listPage_merchant_sett_report(Map<String, Object> paramMap) {
		return getSessionTemplate().selectOne("getCount_listPage_merchant_sett_report", paramMap);
	}

	@Override
	public Map<String, Object> getCount_listPage_profit_count_report(Map<String, Object> paramMap) {
		return getSessionTemplate().selectOne("getCount_listPage_profit_count_report", paramMap);
	}

	/***
	 * @Title: count_merchant_trade_report
	 * @Description: 汇总商户交易统计
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	public Map<String, Object> count_merchant_trade_report(Map<String, Object> paramMap) {
		return getSessionTemplate().selectOne("count_merchant_trade_report", paramMap);
	}

	/***
	 * @Title: count_agent_plat_profit_report
	 * @Description: 汇总收益报表
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	public Map<String, Object> count_agent_plat_profit_report(Map<String, Object> paramMap) {
		return getSessionTemplate().selectOne("count_agent_plat_profit_report", paramMap);
	}

	@Override
	public Map<String, Object> getCount_listPage_Agent_Profit_Detail(Map<String, Object> paramMap) {
		return getSessionTemplate().selectOne("getCount_listPage_Agent_Profit_Detail", paramMap);
	}

	@Override
	public Map<String, Object> getCount_listPage_agent_profit(Map<String, Object> paramMap) {
		
		
		return getSessionTemplate().selectOne("getCount_listPage_agent_profit", paramMap);
	}

}
