package wusc.edu.pay.facade.settlement.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.settlement.biz.SettQueryBiz;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettTypeEnum;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;


/**
 * 结算规则管理服务接口实现
 * 
 * @author pc-Along
 * 
 */
@Component("settQueryFacade")
public class SettQueryFacadeImpl implements SettQueryFacade {

	@Autowired
	private SettQueryBiz settQueryBiz;
	@Autowired
	private SettRecordDao settRecordDao;

	/** 根据账户编号查询结算规则 **/
	public SettRule getSettRuleByAccountNo(String accountNo) {
		return settQueryBiz.getSettRuleByAccountNo(accountNo);
	}

	/** 根据商户编号查询结算规则 **/
	public SettRule getSettlRuleByMerchantNo(String merchantNo) {
		return settQueryBiz.getSettRuleByMerchantNo(merchantNo);
	}

	/**
	 * 获取所有可用的结算规则.
	 */
	@Override
	public List<SettRule> listAllAvailableSettRules() {
		return settQueryBiz.listAllAvailableSettRules();
	}
	
	/**
	 * 获取所有可用的结算规则.<br/>
	 * @param settType 结算类型.<br/>
	 * @return SettRuleList.
	 */
	@Override
	public List<SettRule> listAllAvailableSettRulesBySettType(SettTypeEnum settType) {
		return settQueryBiz.listAllAvailableSettRulesBySettType(settType);
	}

	/**
	 * 分页查询结算规则
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean querySettRuleListPage(PageParam pageParam,
			Map<String, Object> map) {
		return settQueryBiz.querySettRuleListPage(pageParam, map);
	}

	/**
	 * 分页查询节假日
	 * 
	 * @return
	 */
	public PageBean querySettHolidayListPage(PageParam pageParam,
			Map<String, Object> map) {
		return settQueryBiz.querySettHolidayListPage(pageParam, map);
	}

	/**
	 * 分页查询结算日调整
	 * 
	 * @return
	 */
	public PageBean querySettDayAdjustSettingListPage(PageParam pageParam,
			Map<String, Object> map) {
		return settQueryBiz.querySettDayAdjustSettingListPage(pageParam, map);
	}

	/**
	 * 根据账户编号获取结算日期区间内的所有未结算日汇总数据.<br/>
	 * 
	 * @param accountNo
	 *            账户编号.<br/>
	 * @param beginCollectDate
	 *            汇总日期(开始).<br/>
	 * @param endCollectDate
	 *            汇总日期(结束).<br/>
	 * @return
	 */
	@Override
	public List<SettDailyCollect> listUnSettDailyCollectByAccountNo(
			String accountNo, String beginCollectDate, String endCollectDate) {
		return settQueryBiz.listUnSettDailyCollectByAccountNo(accountNo,
				beginCollectDate, endCollectDate);
	}

	/**
	 * 生成结算批次号.<br/>
	 * 
	 * @param settTypeEnum
	 *            SettTypeEnum结算类型枚举.<br/>
	 * @return batchNo 批次号.<br/>
	 */
	@Override
	public String buildSettBatchNo(SettTypeEnum settTypeEnum) {
		return settRecordDao.buildSettBatchNo(settTypeEnum.getValue());
	}

	/**
	 * 根据结算批次号和结算记录状态获取结算记录列表.<br/>
	 * 
	 * @param batchNo
	 *            结算批次号（必填）.<br/>
	 * @param settStatus
	 *            结算记录状态（可选）.<br/>
	 * @return SettRecordList.
	 */
	@Override
	public List<SettRecord> listSettRecordBy_batchNo_settStatus(String batchNo,
			SettRecordStatusEnum settStatus) {
		return settRecordDao.listBy_batchNo_settStatus(batchNo, settStatus);
	}

	@Override
	public PageBean querySettCortrolListPage(PageParam pageParam,
			Map<String, Object> map) {
		return settQueryBiz.querySettCortrolListPage(pageParam, map);
	}

	@Override
	public SettControl getBySettModeType(Integer settModeType) {
		return settQueryBiz.getBySettModeType(settModeType);
	}

	@Override
	public PageBean listPageSettDailyCollect(PageParam pageParam,
			Map<String, Object> map) {
		return settQueryBiz.listPageSettDailyCollect(pageParam,map);
	}

	@Override
	public PageBean listPageSettRecord(PageParam pageParam, Map<String, Object> map) {
		return settQueryBiz.listPageSettRecord(pageParam,map);
	}

	@Override
	public PageBean listPageSettErrorRecord(PageParam pageParam,
			Map<String, Object> map) {
		return settQueryBiz.listPageSettErrorRecord(pageParam,map);
	}

	@Override
	public SettRecord getSettRecordByBatchNoAndAccountNo(String batchNo,String accountNo) {
		return settQueryBiz.getSettRecordByBatchNoAndAccountNo(batchNo,accountNo);
	}
	
	/**
	 * 根据结束时间统计每日汇总记录获取结算金额
	 * @param accountNo
	 * @param endDate
	 */
	public Double getSettAmount(String accountNo ,String beginCollectDate,String endCollectDate){
		return settQueryBiz.getSettAmount(accountNo ,beginCollectDate,endCollectDate);
	}
	

}
