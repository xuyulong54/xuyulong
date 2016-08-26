package wusc.edu.pay.facade.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;


/***
 * pos报表对外接口
 * 
 * @author Administrator
 * 
 */
public interface PosReportFacade {

	/***
	 * 根据代理商统计终端报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_terminal_agent(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 根据厂家类型统计终端报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_terminal_factory(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 根据商户统计终端报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_terminal_merch(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 一级代理商分润报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_profit(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 交易区域汇总统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_area_summary(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 卡bin交易异常报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_cardbin_abnormal(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 错误代码统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_errorcode_summary(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 时段交易汇总报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_time_summary(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 代理商分润明细报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_profit_detail(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 收益统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_profit_count_report(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 商户审核报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_audit_report(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 根据特定的交易日期创建商户交易汇总表中的明细数据
	 * 
	 * @param dealDate
	 *            交易日期
	 * @return
	 */
	public int createMerchTransSummary(Date dealDate);

	/***
	 * 根据特定的交易日期创建渠道交易汇总表中的明细数据
	 * 
	 * @param dealDate
	 *            交易日期
	 * @return
	 */
	public int createChannelTransSummary(Date dealDate);

	/**
	 * 根据时间段来统计交易量
	 * 
	 * @param dealDate
	 *            交易日期
	 * @return
	 */
	public int createSummaryByTime(Date dealDate);

	/**
	 * 根据地区来统计交易量
	 * 
	 * @param dealDate
	 * @return
	 */
	public int createSummaryByArea(Date dealDate);

	/**
	 * 根据卡号来统计交易量
	 * 
	 * @param dealDate
	 * @return
	 */
	public int createSummaryByCard(Date dealDate);

	/**
	 * 根据响应码来统计交易量
	 * 
	 * @param dealDate
	 * @return
	 */
	public int createSummaryByRespCode(Date dealDate);

	/**
	 * 根据审核次数统计初审和终审情况
	 * 
	 * @param dealDate
	 * @return
	 */
	public int createAuditSummary(Date dealDate);

	/**
	 * 根据终端厂家和型号统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	public int createTermSummaryByVendor(Date dealDate);

	/**
	 * 根据一级代理商来统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	public int createTermSummaryByAgent(Date dealDate);

	/**
	 * 根据商户来统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	public int createTermSummaryByMerch(Date dealDate);

	/***
	 * 根据统计日期删除已统计的数据
	 * 
	 * @param dealDate
	 *            交易日期
	 * @return
	 */
	public int deleteSummaryByDate(Date dealDate);

	/***
	 * pos交易汇总报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean posTransSumReport(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 商户清算报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_sett_report(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 商户清算报表统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getCount_listPage_merchant_sett_report(Map<String, Object> paramMap);

	/***
	 * 代理商系统的 收益统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_plat_profit_report(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 代理商系统-商户交易统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_trade_report(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 代理商分润统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_profit_by_agent_report(PageParam pageParam, Map<String, Object> paramMap);

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
	public PageBean listPage_sales_report(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 统计收益统计报表
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getCount_listPage_profit_count_report(Map<String, Object> paramMap);

	/***
	 * @Title: count_merchant_trade_report
	 * @Description: 汇总商户交易统计
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	public Map<String, Object> count_merchant_trade_report(Map<String, Object> paramMap);

	/***
	 * @Title: count_agent_plat_profit_report
	 * @Description: 汇总收益报表
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	public Map<String, Object> count_agent_plat_profit_report(Map<String, Object> paramMap);

	/**
	 * 代理商利润明细统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getCount_listPage_Agent_Profit_Detail(Map<String, Object> paramMap);

	
	/**
	 * 一级代理分润统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getCount_listPage_agent_profit(Map<String, Object> paramMap);
}
