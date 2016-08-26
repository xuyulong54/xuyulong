package wusc.edu.pay.core.remit.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.remit.entity.RemitRequest;


/**
 * 打款请求实体
 * 
 * @author： Peter
 * @ClassName: RemitRequestDao.java
 * @Date： 2014-7-22 下午4:20:52
 * @version： V1.0
 */
public interface RemitRequestDao extends BaseDao<RemitRequest> {

	public List<RemitRequest> listBatchNoIsNull();

	public List<RemitRequest> listByBatchNo(String batchNo);

	public RemitRequest getByRequestNo(String requestNo);

	/**
	 * 生成打款请求号.<br/>
	 * 
	 * @return remitNo 打款请求号.<br/>
	 */
	public String buildRemitRequestNo();

	/**
	 * 批量添加制单信息
	 * 
	 * @param remitRequest
	 * @return
	 */
	public long batchInsert(List<RemitRequest> remitRequest);

	/**
	 * 批量更新拒绝状态
	 */
	public void batchUpdateToUnApprove(List<String> lists, String confirm);

	/**
	 * 批量更新审核通过状态
	 */
	public void batchUpdateToAudited(List<String> lists, String confirm);

	/**
	 * 根据多个id获取批量请求数据
	 * 
	 * @param lists
	 * @return
	 */
	public List<RemitRequest> listByIn(List<String> lists);

	/**
	 * 批量更新是否已重新制单
	 * 
	 * @param paramMap
	 */
	public void batchUpdateRemitRequestStatus(Map<String, Object> paramMap);

	/**
	 * 批量更新状态
	 * 
	 * @param paramMap
	 */
	public void batchUpdateToStatus(List<RemitRequest> remitRequests, int status);


}
