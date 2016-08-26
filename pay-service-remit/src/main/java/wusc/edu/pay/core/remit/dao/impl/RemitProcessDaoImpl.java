package wusc.edu.pay.core.remit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.remit.dao.RemitProcessDao;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.entity.RemitRequest;


/**
 * 打款处理记录实体
 * 
 * @author： Peter
 * @ClassName: RemitProcessDaoImpl.java
 * @Date： 2014-7-22 下午4:19:50
 * @version： V1.0
 */
@Repository("remitProcessDao")
public class RemitProcessDaoImpl extends BaseDaoImpl<RemitProcess> implements RemitProcessDao {

	public List<RemitProcess> listByBatchNo(String batchNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		return super.listBy(paramMap);
	}

	@Override
	public Map<String, Object> getAmountByChannelCodeAndBatchNos(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = (Map<String, Object>) getSessionTemplate().selectList(getStatement("getAmountByChannelCodeAndBatchNos"), paramMap).get(0);
		return resultMap;
	}

	@Override
	public List<RemitProcess> getByChannelCodeAndBatchNo(Map<String, Object> paramMap) {
		return getSessionTemplate().selectList(getStatement("getByChannelCodeAndBatchNo"), paramMap);
	}

	@Override
	public Map<String, Object> getAmountByChannelCodeAndRequestNos(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = (Map<String, Object>) getSessionTemplate().selectList(getStatement("getAmountByChannelCodeAndRequestNos"), paramMap).get(0);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getChannelCodesByBatchNo(String batchNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		return getSessionTemplate().selectList("getChannelCodesByBatchNo", paramMap);
	}

	@Override
	public List<Map<String, Object>> getChannelCodesByRequestNos(String requestNos) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("requestNos", requestNos);
		return getSessionTemplate().selectList("getChannelCodesByRequestNos", paramMap);
	}

	/**
	 * 根据打款请求号查询
	 * 
	 * @param requestNo
	 * @return
	 */
	public RemitProcess getByRequestNo(String requestNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("requestNo", requestNo);
		return super.getBy(paramMap);
	}

	@Override
	public List<Map<String, Object>> getChannelCodesByMap(Map<String, Object> paramMap) {
		return getSessionTemplate().selectList("getChannelCodesByMap", paramMap);
	}

	@Override
	public void batchUpdate(List<RemitProcess> remitProcesss) {
		this.update(remitProcesss);
		/*
		 * Map<String, Object> paramMap = new HashMap<String, Object>();
		 * //TODO:批量更新，sql中的foreach赋值有问题 paramMap.put("lists", remitProcesss);
		 * getSessionTemplate().update("batchUpdate", paramMap);
		 * //this.getSqlSession().update("batchUpdate", paramMap);
		 */
	}

	@Override
	public void batchUpdateToStatus(List<RemitProcess> remitProcessList, int status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lists", remitProcessList);
		paramMap.put("status", status);
		getSessionTemplate().update("batchUpdateProcessStatus", paramMap);

	}

}
