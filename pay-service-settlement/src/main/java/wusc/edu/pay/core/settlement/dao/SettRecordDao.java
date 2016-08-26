package wusc.edu.pay.core.settlement.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;


public interface SettRecordDao extends BaseDao<SettRecord> {

	/**
	 * 生成结算批次号.<br/>
	 * @param settTypeValue 结算类型值.<br/>
	 * @return batchNo .
	 */
	public String buildSettBatchNo(Integer settTypeValue);

	/**
	 * 根据结算批次号和结算记录状态获取结算记录列表.<br/>
	 * @param batchNo 结算批次号（必填）.<br/>
	 * @param settStatus 结算记录状态（可选）.<br/>
	 * @return SettRecordList.
	 */
	public List<SettRecord> listBy_batchNo_settStatus(String batchNo, SettRecordStatusEnum settStatus);

	/**
	 * 根据打款请求号获取结算记录.<br/>
	 * @param remitNo 打款请求号.<br/>
	 * @return SettRecord .
	 */
	public SettRecord getByRemitNo(String remitNo);

	/**
	 * 根据结算批次号、账户编号查询结算记录
	 * @param batchNo
	 * @param accountNo 
	 * @return
	 */
	public SettRecord getSettRecordByBatchNoAndAccountNo(String batchNo, String accountNo);

	/**
	 * 根据结算批次号，更新结算记录状态.<br/>
	 * @param batchNo
	 * @param statusEnum
	 */
	public void updateSettStatusByBatchNo(String batchNo, SettRecordStatusEnum statusEnum);

}
