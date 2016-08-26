package wusc.edu.pay.facade.settlement.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettTypeEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;


/**
 * 结算业务查询服务接口
 */
public interface SettQueryFacade {

	/**
	 * 根据账户编号获取单个结算规则
	 * 
	 * @param accountNo
	 *            结算账户编号
	 * @return
	 */
	public SettRule getSettRuleByAccountNo(String accountNo) throws SettBizException;

	/**
	 * 根据商户编号查询商户的结算规则
	 * 
	 * @param customerNo
	 * @return
	 */
	public SettRule getSettlRuleByMerchantNo(String merchantNo)  throws SettBizException;

	/**
	 * 获取所有可用的结算规则.<br/>
	 * 
	 * @return SettRuleList.
	 */
	public List<SettRule> listAllAvailableSettRules() throws SettBizException;
	
	/**
	 * 获取所有可用的结算规则.<br/>
	 * @param settType 结算类型.<br/>
	 * @return SettRuleList.
	 */
	public List<SettRule> listAllAvailableSettRulesBySettType(SettTypeEnum settType) throws SettBizException;

	/**
	 * 分页查询结算规则
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean querySettRuleListPage(PageParam pageParam, Map<String, Object> map) throws SettBizException;

	/**
	 * 分页查询节假日
	 * @return
	 */
	public PageBean querySettHolidayListPage(PageParam pageParam, Map<String, Object> map) throws SettBizException;

	/**
	 * 分页查询结算日调整
	 * @return
	 */
	public PageBean querySettDayAdjustSettingListPage(PageParam pageParam, Map<String, Object> map) throws SettBizException;

	/**
	 * 根据账户编号获取结算日期区间内的所有未结算日汇总数据.<br/>
	 * @param accountNo 账户编号.<br/>
	 * @param beginCollectDate 汇总日期(开始).<br/>
	 * @param endCollectDate 汇总日期(结束).<br/>
	 * @return
	 */
	public List<SettDailyCollect> listUnSettDailyCollectByAccountNo(String accountNo, String beginCollectDate, String endCollectDate) throws SettBizException;


	/**
	 * 生成结算批次号.<br/>
	 * @param settTypeEnum SettTypeEnum结算类型枚举.<br/>
	 * @return batchNo 批次号.<br/>
	 */
	public String buildSettBatchNo(SettTypeEnum settTypeEnum) throws SettBizException;

	/**
	 * 根据结算批次号和结算记录状态获取结算记录列表.<br/>
	 * @param batchNo 结算批次号（必填）.<br/>
	 * @param settStatus 结算记录状态（可选）.<br/>
	 * @return SettRecordList.
	 */
	public List<SettRecord> listSettRecordBy_batchNo_settStatus(String batchNo, SettRecordStatusEnum settStatus) throws SettBizException;

	/**
	 * 查询结算控制
	 * @return
	 */
	public PageBean querySettCortrolListPage(PageParam pageParam,
			Map<String, Object> map) throws SettBizException;

	/**
	 * 根据结算控制方式查询
	 * @param settModeType
	 * @return
	 */
	public SettControl getBySettModeType(Integer settModeType) throws SettBizException;
	
	/**
	 * 根据账户编号、时间段分页查询汇总记录
	 * @param pageParam
	 * @param map(accountNo账户编号，beginCollectDate统计开始日期，endCollectDate统计结束日期)
	 * @return
	 */
	public PageBean listPageSettDailyCollect(PageParam pageParam,Map<String, Object> map) throws SettBizException;
	
	/**
	 * 根据账户编号、时间段、结算状态分页查询结算记录
	 * @param pageParam
	 * @param map（accountNo账户编号，beginTime开始时间，endTime结算时间，settStatus结算状态）
	 * @return
	 */
	public PageBean listPageSettRecord(PageParam pageParam,Map<String, Object> map) throws SettBizException;

	/**
	 * 分页查询结算失败记录
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean listPageSettErrorRecord(PageParam pageParam,
			Map<String, Object> map) throws SettBizException;

	/**
	 * 根据结算批次号、账户编号查询结算记录
	 * @param batchNo
	 * @param accountNo
	 * @return
	 */
	public SettRecord getSettRecordByBatchNoAndAccountNo(String batchNo,String accountNo) throws SettBizException;

	/**
	 * 根据结束时间统计每日汇总记录获取结算金额
	 * @param accountNo
	 * @param endDate
	 */
	public Double getSettAmount(String accountNo ,String beginCollectDate,String endCollectDate) throws SettBizException;

}
