package wusc.edu.pay.core.report.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.report.entity.MerchTradeSummary;


/***
 * 代理商利润统计报表 dao 接口
 * 
 * @author Administrator
 * 
 */
public interface MerchTradeSummaryDao extends BaseDao<MerchTradeSummary> {

	/***
	 * 代理商分润明细报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listPage_agent_profit_detail(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 收益统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listPage_profit_count_report(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 创建商户汇总明细数据
	 * 
	 * @param dealDate
	 * @return
	 */
	int createMerchTransSummary(Date dealDate);

	int deleteSummaryByDate(Date dealDate);

	/***
	 * pos交易汇总报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean posTransSumReport(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 商户清算报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listPage_merchant_sett_report(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 统计商户清算报表
	 * 
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getCount_listPage_merchant_sett_report(Map<String, Object> paramMap);

	/***
	 * 代理商系统的 收益统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listPage_agent_plat_profit_report(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 代理商系统-商户交易统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listPage_merchant_trade_report(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 代理商分润统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listPage_profit_by_agent_report(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * pos交易汇总报表统计
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List getposTransSumReportCount(Map<String, Object> paramMap);

	/***
	 * 统计销售人员拓展的代理商个数
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listPage_sales_report(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 统计收益报表
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getCount_listPage_profit_count_report(Map<String, Object> paramMap);

	/***
	 * @Title: count_merchant_trade_report
	 * @Description: 汇总商户交易统计
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	Map<String, Object> count_merchant_trade_report(Map<String, Object> paramMap);

	/***
	 * @Title: count_agent_plat_profit_report
	 * @Description: 汇总收益报表
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	Map<String, Object> count_agent_plat_profit_report(Map<String, Object> paramMap);
	
	/**
	 * 代理商分润明细统计
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getCount_listPage_Agent_Profit_Detail(Map<String, Object> paramMap);
	
	/**
	 * 一级代理商分润统计
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getCount_listPage_agent_profit(Map<String, Object> paramMap);
	
	
	
	
	
	

}
