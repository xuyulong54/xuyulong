package wusc.edu.pay.core.report.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.MerchTradeSummaryDao;


/***
 * 代理商利润统计报表 Biz 类
 * 
 * @author Administrator
 * 
 */
@Component("merchTradeSummaryBiz")
public class MerchTradeSummaryBiz {
	@Autowired
	private MerchTradeSummaryDao merchTradeSummaryDao;

	/***
	 * 列表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.listPage(pageParam, paramMap);
	}

	/***
	 * 代理商分润明细报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_profit_detail(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.listPage_agent_profit_detail(pageParam, paramMap);
	}

	/***
	 * 收益统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_profit_count_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.listPage_profit_count_report(pageParam, paramMap);
	}

	/**
	 * 创建商户汇总明细数据
	 * 
	 * @param dealDate
	 * @return
	 */
	public int createMerchTransSummary(Date dealDate) {
		return merchTradeSummaryDao.createMerchTransSummary(dealDate);
	}

	public int deleteSummaryByDate(Date dealDate) {
		return merchTradeSummaryDao.deleteSummaryByDate(dealDate);
	}

	/***
	 * pos交易汇总报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean posTransSumReport(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.posTransSumReport(pageParam, paramMap);
	}

	/**
	 * pos交易汇总报表统计
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getposTransSumReportCount(Map<String, Object> paramMap) {
		return merchTradeSummaryDao.getposTransSumReportCount(paramMap);
	}

	/***
	 * 商户清算报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_sett_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.listPage_merchant_sett_report(pageParam, paramMap);
	}

	/***
	 * 代理商系统的 收益统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_plat_profit_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.listPage_agent_plat_profit_report(pageParam, paramMap);
	}

	/***
	 * 代理商系统-商户交易统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_trade_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.listPage_merchant_trade_report(pageParam, paramMap);
	}

	/***
	 * 代理商分润统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_profit_by_agent_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.listPage_profit_by_agent_report(pageParam, paramMap);
	}

	/***
	 * 统计销售人员拓展的代理商个数
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_sales_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryDao.listPage_sales_report(pageParam, paramMap);
	}

	/**
	 * 统计商户清算报表
	 * 
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getCount_listPage_merchant_sett_report(Map<String, Object> paramMap) {
		return merchTradeSummaryDao.getCount_listPage_merchant_sett_report(paramMap);
	}
	/**
	 * 统计收益报表
	 * 
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getCount_listPage_profit_count_report(Map<String, Object> paramMap) {
		return merchTradeSummaryDao.getCount_listPage_profit_count_report(paramMap);
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
		return merchTradeSummaryDao.count_merchant_trade_report(paramMap);
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
		return merchTradeSummaryDao.count_agent_plat_profit_report(paramMap);
	}

	public Map<String, Object> getCount_listPage_Agent_Profit_Detail(Map<String, Object> paramMap) {
		return merchTradeSummaryDao.getCount_listPage_Agent_Profit_Detail(paramMap);
	}

	public Map<String, Object> getCount_listPage_agent_profit(Map<String, Object> paramMap) {
		return merchTradeSummaryDao.getCount_listPage_agent_profit(paramMap);
	}

}
