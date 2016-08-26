package wusc.edu.pay.core.remit.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.remit.entity.RemitProcess;


/**
 * 打款处理记录实体
 * 
 * @author： Peter
 * @ClassName: RemitProcessDao.java
 * @Date： 2014-7-22 下午4:19:10
 * @version： V1.0
 */
public interface RemitProcessDao extends BaseDao<RemitProcess> {

	public List<RemitProcess> listByBatchNo(String batchNo);

	/**
	 * @Title: 根据打款通道编号和打款批次号汇总走该通道的总金额
	 * @Description:
	 * @param @param paramMap
	 * @param @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getAmountByChannelCodeAndBatchNos(Map<String, Object> paramMap);

	/**
	 * @Title: 根据打款通道和打款请求汇总打款金额
	 * @Description:
	 * @param @param paramMap
	 * @param @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getAmountByChannelCodeAndRequestNos(Map<String, Object> paramMap);

	/**
	 * @Title: 根据打款通道编号和打款批次号汇总处理记录
	 * @Description:
	 * @param @param paramMap
	 * @param @return
	 * @return List<RemitProcess>
	 * @throws
	 */
	public List<RemitProcess> getByChannelCodeAndBatchNo(Map<String, Object> paramMap);

	/**
	 * @Title: 根据批次号获取该批次请求选择的所有渠道
	 * @Description:
	 * @param @param batchNo
	 * @param @return
	 * @return String[]
	 * @throws
	 */
	public List<Map<String, Object>> getChannelCodesByBatchNo(String batchNo);

	/**
	 * @Title: 根据请求号获取该批请求选择的所有渠道
	 * @Description:
	 * @param @param requestNos
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> getChannelCodesByRequestNos(String requestNos);

	/**
	 * @Title: 根据条件获取该批请求选择的所有渠道
	 * @Description:
	 * @param @param requestNos
	 * @param @return
	 * @param @throws RemitBizException
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> getChannelCodesByMap(Map<String, Object> paramMap);

	public void batchUpdate(List<RemitProcess> remitProcesss);

	/**
	 * 批量更新状态
	 * 
	 * @param paramMap
	 */
	public void batchUpdateToStatus(List<RemitProcess> remitProcessList, int status);

	/**
	 * 根据打款请求号查询
	 * 
	 * @param requestNo
	 * @return
	 */
	public RemitProcess getByRequestNo(String requestNo);
}
