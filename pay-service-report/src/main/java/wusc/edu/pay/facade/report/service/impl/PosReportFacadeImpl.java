package wusc.edu.pay.facade.report.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.AreaSummaryBiz;
import wusc.edu.pay.core.report.biz.CardBinAbnormalBiz;
import wusc.edu.pay.core.report.biz.ChannelTradeSummaryBiz;
import wusc.edu.pay.core.report.biz.ErrorCodeSummaryBiz;
import wusc.edu.pay.core.report.biz.MerchTradeSummaryBiz;
import wusc.edu.pay.core.report.biz.MerchantAuditSummaryBiz;
import wusc.edu.pay.core.report.biz.TerminalSummaryByAgentBiz;
import wusc.edu.pay.core.report.biz.TerminalSummaryByFactoryBiz;
import wusc.edu.pay.core.report.biz.TerminalSummaryByMerchBiz;
import wusc.edu.pay.core.report.biz.TimeSummaryBiz;
import wusc.edu.pay.facade.report.service.PosReportFacade;


/***
 * pos报表对外接口
 * @author Administrator
 *
 */
@Component("posReportFacade")
public class PosReportFacadeImpl implements PosReportFacade {
	
	@Autowired
	private TerminalSummaryByAgentBiz terminalSummaryByAgentBiz; // 根据代理商统计终端报表
	@Autowired
	private TerminalSummaryByFactoryBiz terminalSummaryByFactoryBiz; // 根据厂家类型统计终端报表
	@Autowired
	private TerminalSummaryByMerchBiz terminalSummaryByMerchBiz; // 根据商户统计终端报表
	@Autowired
	private MerchTradeSummaryBiz merchTradeSummaryBiz;
	
	@Autowired
	private AreaSummaryBiz areaSummaryBiz;	//统计区域数据
	@Autowired
	private CardBinAbnormalBiz cardBinAbnormalBiz;	//异常卡bin
	@Autowired
	private ErrorCodeSummaryBiz ErrorCodeSummaryBiz;	//交易失败统计
	@Autowired
	private TimeSummaryBiz timeSummaryBiz;	//统计时间段数据
	@Autowired
	private MerchantAuditSummaryBiz merchantAuditSummaryBiz;
	
	@Autowired
	private ChannelTradeSummaryBiz channelTradeSummaryBiz;

	
	
	/***
	 * 根据代理商统计终端报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_terminal_agent(PageParam pageParam, Map<String, Object> paramMap) {
		return terminalSummaryByAgentBiz.listPage(pageParam, paramMap);
	}
	
	/***
	 * 根据厂家类型统计终端报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_terminal_factory(PageParam pageParam, Map<String, Object> paramMap) {
		return terminalSummaryByFactoryBiz.listPage(pageParam, paramMap);
	}
	
	/***
	 * 根据商户统计终端报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_terminal_merch(PageParam pageParam, Map<String, Object> paramMap) {
		return terminalSummaryByMerchBiz.listPage(pageParam, paramMap);
	}

	/***
	 * 一级代理商分润报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_profit(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.listPage(pageParam, paramMap);
	}
	
	/***
	 * 代理商分润明细报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_profit_detail(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.listPage_agent_profit_detail(pageParam, paramMap);
	}
	
	

	/***
	 * 收益统计报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_profit_count_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.listPage_profit_count_report(pageParam, paramMap);
	}
	
	
	

	/***
	 * pos交易汇总报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean posTransSumReport(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.posTransSumReport(pageParam, paramMap);
	}
	
	/**
	 * pos交易汇总报表统计
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getposTransSumReportCount(Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.getposTransSumReportCount(paramMap);
	}
	
	
	/***
	 * 统计销售人员拓展的代理商个数
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_sales_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.listPage_sales_report(pageParam, paramMap);
	}

	/***
	 * 商户清算报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_sett_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.listPage_merchant_sett_report(pageParam, paramMap);
	}
	
	
	/***
	 * 代理商系统的 收益统计报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_agent_plat_profit_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.listPage_agent_plat_profit_report(pageParam, paramMap);
	}
	
	/***
	 * 代理商系统-商户交易统计报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_merchant_trade_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.listPage_merchant_trade_report(pageParam, paramMap);
	}
	
	

	/***
	 * 代理商分润统计报表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage_profit_by_agent_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.listPage_profit_by_agent_report(pageParam, paramMap);
	}

	@Override
	public PageBean listPage_area_summary(PageParam pageParam, Map<String, Object> paramMap) {
		return areaSummaryBiz.listPage(pageParam, paramMap);
	}

	@Override
	public PageBean listPage_cardbin_abnormal(PageParam pageParam, Map<String, Object> paramMap) {
		return cardBinAbnormalBiz.listPage(pageParam, paramMap);
	}

	@Override
	public PageBean listPage_errorcode_summary(PageParam pageParam, Map<String, Object> paramMap) {
		return ErrorCodeSummaryBiz.listPage(pageParam, paramMap);
	}

	@Override
	public PageBean listPage_time_summary(PageParam pageParam, Map<String, Object> paramMap) {
		return timeSummaryBiz.listPage(pageParam, paramMap);
	}
	
	@Override
	public PageBean listPage_merchant_audit_report(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantAuditSummaryBiz.listPage(pageParam, paramMap);
	}

	@Override
	public int createMerchTransSummary(Date dealDate) {
		return merchTradeSummaryBiz.createMerchTransSummary(dealDate);
	}

	@Override
	public int createChannelTransSummary(Date dealDate) {
		return channelTradeSummaryBiz.createChannelTransSummary(dealDate);
	}

	@Override
	public int createSummaryByTime(Date dealDate) {
		return timeSummaryBiz.createAreaSummary(dealDate);
	}

	@Override
	public int createSummaryByArea(Date dealDate) {
		return areaSummaryBiz.createAreaSummary(dealDate);
	}

	@Override
	public int createSummaryByCard(Date dealDate) {
		return cardBinAbnormalBiz.createCardBinAbnormal(dealDate);
	}

	@Override
	public int createSummaryByRespCode(Date dealDate) {
		return ErrorCodeSummaryBiz.createErrorCodeSummary(dealDate);
	}

	@Override
	public int createAuditSummary(Date dealDate) {
		return merchantAuditSummaryBiz.createAuditSummary(dealDate);
	}

	@Override
	public int createTermSummaryByVendor(Date dealDate) {
		return terminalSummaryByFactoryBiz.createTermSummaryByVendor(dealDate);
	}

	@Override
	public int createTermSummaryByAgent(Date dealDate) {
		return terminalSummaryByAgentBiz.createTermSummaryByAgent(dealDate);
	}

	@Override
	public int createTermSummaryByMerch(Date dealDate) {
		return terminalSummaryByMerchBiz.createTermSummaryByMerch(dealDate);
	}

	@Override
	public int deleteSummaryByDate(Date dealDate) {
		return merchTradeSummaryBiz.deleteSummaryByDate(dealDate);
	}

	@Override
	public Map<String, Object> getCount_listPage_merchant_sett_report(Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.getCount_listPage_merchant_sett_report(paramMap);
	}

	@Override
	public Map<String, Object> getCount_listPage_profit_count_report(Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.getCount_listPage_profit_count_report(paramMap);
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
		return merchTradeSummaryBiz.count_merchant_trade_report(paramMap);
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
		return merchTradeSummaryBiz.count_agent_plat_profit_report(paramMap);
	}

	@Override
	public Map<String, Object> getCount_listPage_Agent_Profit_Detail(Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.getCount_listPage_Agent_Profit_Detail(paramMap);
	}

	@Override
	public Map<String, Object> getCount_listPage_agent_profit(Map<String, Object> paramMap) {
		return merchTradeSummaryBiz.getCount_listPage_agent_profit(paramMap) ;
	}
	
}
