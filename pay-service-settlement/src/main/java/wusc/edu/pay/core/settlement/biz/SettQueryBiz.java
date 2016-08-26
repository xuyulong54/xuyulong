package wusc.edu.pay.core.settlement.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.settlement.dao.SettControlDao;
import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.core.settlement.dao.SettDayAdjustSettingDao;
import wusc.edu.pay.core.settlement.dao.SettErrorRecordDao;
import wusc.edu.pay.core.settlement.dao.SettHolidaySettingDao;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.core.settlement.dao.SettRuleDao;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettTypeEnum;


/**
 * 
 * @描述: 结算服务查询业务逻辑 .
 * @作者: WuShuicheng.
 * @创建: 2014-7-30,上午11:47:41
 * @版本: V1.0
 *
 */
@Component("settQueryBiz")
public class SettQueryBiz {

//	private static final Log LOG = LogFactory.getLog(SettQueryBiz.class);
	
	@Autowired
	private SettRuleDao settRuleDao;
	@Autowired
	private SettDailyCollectDao settDailyCollectDao;
	@Autowired
	private SettHolidaySettingDao settHolidaySettingDao;
	@Autowired
	private SettDayAdjustSettingDao settDayAdjustSettingDao;
	@Autowired
	private SettControlDao settControlDao;
	@Autowired
	private SettRecordDao settRecordDao;
	@Autowired
	private SettErrorRecordDao settErrorRecordDao;

	/**
	 * 获取所有可用的的结算规则.
	 */
	public List<SettRule> listAllAvailableSettRules() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isSett", PublicStatusEnum.ACTIVE.getValue());
		return settRuleDao.listBy(paramMap);
	}
	
	/**
	 * 获取所有可用的结算规则.<br/>
	 * @param settType 结算类型.<br/>
	 * @return SettRuleList.
	 */
	public List<SettRule> listAllAvailableSettRulesBySettType(SettTypeEnum settType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isSett", PublicStatusEnum.ACTIVE.getValue());
		paramMap.put("settType", settType.getValue());
		return settRuleDao.listBy(paramMap);
	}

	/**
	 *  根据账户编号查询结算规则 
	 * @param accountNo
	 * @return
	 */
	public SettRule getSettRuleByAccountNo(String accountNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", accountNo);
		return settRuleDao.getBy(paramMap);
	}

	/**
	 * 根据账户编号查询结算规则 
	 * @param merchantNo
	 * @return
	 */
	public SettRule getSettRuleByMerchantNo(String merchantNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", merchantNo);
		return settRuleDao.getBy(paramMap);
	}

	/**
	 * 分页查询结算规则
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean querySettRuleListPage(PageParam pageParam, Map<String, Object> map) {
		return settRuleDao.listPage(pageParam, map);
	}

	/**
	 * 分页查询节假日
	 * @return
	 */
	public PageBean querySettHolidayListPage(PageParam pageParam, Map<String, Object> map) {
		return settHolidaySettingDao.listPage(pageParam, map);
	}

	/**
	 * 分页查询结算日调整
	 * @return
	 */
	public PageBean querySettDayAdjustSettingListPage(PageParam pageParam, Map<String, Object> map) {
		return settDayAdjustSettingDao.listPage(pageParam, map);
	}

	/**
	 * 根据账户编号获取结算日期区间内的所有未结算日汇总数据.<br/>
	 * @param accountNo 账户编号.<br/>
	 * @param beginCollectDate 汇总日期(开始).<br/>
	 * @param endCollectDate 汇总日期(结束).<br/>
	 * @return
	 */
	public List<SettDailyCollect> listUnSettDailyCollectByAccountNo(String accountNo, String beginCollectDate, String endCollectDate) {
		return settDailyCollectDao.listUnSettDailyCollectByAccountNo(accountNo, beginCollectDate, endCollectDate);
	}

	/**
	 * 分页查询结算控制
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean querySettCortrolListPage(PageParam pageParam,
			Map<String, Object> map) {
		return settControlDao.listPage(pageParam, map);
	}

	public SettControl getBySettModeType(Integer settModeType) {
		return settControlDao.getBySettModeType(SettModeTypeEnum.getEnum(settModeType));
	}

	public PageBean listPageSettDailyCollect(PageParam pageParam,
			Map<String, Object> map) {
		return settDailyCollectDao.listPage(pageParam, map);
	}

	public PageBean listPageSettRecord(PageParam pageParam, Map<String, Object> map) {
		return settRecordDao.listPage(pageParam, map);
	}

	public PageBean listPageSettErrorRecord(PageParam pageParam,
			Map<String, Object> map) {
		return settErrorRecordDao.listPage(pageParam, map);
	}

	public SettRecord getSettRecordByBatchNoAndAccountNo(String batchNo,String accountNo) {
		return settRecordDao.getSettRecordByBatchNoAndAccountNo(batchNo,accountNo);
	}

	
	/**
	 * 根据结束时间统计每日汇总记录获取结算金额
	 * @param accountNo
	 * @param endDate
	 */
	public Double getSettAmount(String accountNo, String beginCollectDate,String endCollectDate) {
		return settDailyCollectDao.getSettAmount(accountNo,beginCollectDate,endCollectDate);
	}

	
}
