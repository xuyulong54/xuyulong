package wusc.edu.pay.core.settlement.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;


@Repository("settRecordDao")
public class SettRecordDaoImpl extends BaseDaoImpl<SettRecord> implements SettRecordDao {

	/**
	 * 生成结算批次号.<br/>
	 * @param settTypeValue 结算类型值.<br/>
	 * @return batchNo .
	 */
	public String buildSettBatchNo(Integer settTypeValue) {
		String settBatchNo = settTypeValue + DateUtils.formatDate(new Date(), "yyyyMMdd") + super.getSeqNextValue("SETT_BATCHNO_SEQ");
		return settBatchNo;
	}

	/**
	 * 根据结算批次号和结算记录状态获取结算记录列表.<br/>
	 * @param batchNo 结算批次号（必填）.<br/>
	 * @param settStatus 结算记录状态（可选）.<br/>
	 * @return SettRecordList.
	 */
	@Override
	public List<SettRecord> listBy_batchNo_settStatus(String batchNo, SettRecordStatusEnum settStatus) {
		// 结算批次号不能为空（为空）
		if (StringUtil.isBlank(batchNo)){
			return new ArrayList<SettRecord>();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		paramMap.put("settStatus", settStatus.getValue());
		return super.listBy(paramMap);
	}

	/**
	 * 根据打款请求号获取结算记录.<br/>
	 * @param remitNo 打款请求号.<br/>
	 * @return SettRecord .
	 */
	@Override
	public SettRecord getByRemitNo(String remitNo) {
		return super.getSessionTemplate().selectOne(getStatement("getByRemitNo"), remitNo);
	}

	/**
	 * 根据批次号和账户编号查询结算记录
	 */
	@Override
	public SettRecord getSettRecordByBatchNoAndAccountNo(String batchNo, String accountNo) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("batchNo", batchNo);
		parameter.put("accountNo", accountNo);
		return super.getSessionTemplate().selectOne(getStatement("getSettRecordByBatchNoAndAccountNo"), parameter);
	}

	/**
	 * 根据结算批次号，更新结算记录状态.<br/>
	 * @param batchNo
	 * @param statusEnum
	 */
	@Override
	public void updateSettStatusByBatchNo(String batchNo, SettRecordStatusEnum statusEnum) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		paramMap.put("settStatus", statusEnum.getValue());
		super.getSessionTemplate().update(getStatement("updateSettStatusByBatchNo"), paramMap);
	}

}
