package wusc.edu.pay.core.remit.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.remit.dao.RemitRequestDao;
import wusc.edu.pay.facade.remit.entity.RemitRequest;


/**
 * 打款请求实体
 * 
 * @author： Peter
 * @ClassName: RemitRequestDaoImpl.java
 * @Date： 2014-7-22 下午4:21:58
 * @version： V1.0
 */
@Repository("remitRequestDao")
public class RemitRequestDaoImpl extends BaseDaoImpl<RemitRequest> implements RemitRequestDao {

	/**
	 * 查询未批次的打款请求记录
	 */
	public List<RemitRequest> listBatchNoIsNull() {
		return super.getSqlSession().selectList("listBatchNoIsNull", null);
	}

	public List<RemitRequest> listByBatchNo(String batchNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		return super.listBy(paramMap);
	}

	public RemitRequest getByRequestNo(String requestNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("requestNo", requestNo);
		return super.getBy(paramMap);
	}

	/**
	 * 生成打款请求号.<br/>
	 * 
	 * @return remitRequestNo 打款请求号.<br/>
	 */
	@Override
	public String buildRemitRequestNo() {
		/*
		 * SqlSession sqlSession = super.getSqlSession(); String remitRequestNo
		 * = sqlSession.selectOne(getStatement("getRemitRequestNoSeq"));
		 * sqlSession.clearCache(); return remitRequestNo;
		 */
		String remitRequestNoSeq = super.getSeqNextValue("REMIT_REQUEST_NO_SEQ");
		String remitRequestNo = DateUtils.formatDate(new Date(), "yyyyMMdd") + remitRequestNoSeq;
		return remitRequestNo;
	}

	/**
	 * 批量添加制单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long batchInsert(List<RemitRequest> remitRequest) {
		int MAX_INSERT_LINE = 500;
		int size = (remitRequest == null) ? 0 : remitRequest.size();
		if (size == 0) {
			return 0;
		}
		int batch = (size % MAX_INSERT_LINE == 0) ? size / MAX_INSERT_LINE : size / MAX_INSERT_LINE + 1;
		long count = 0;
		for (int i = 0; i < batch; i++) {
			List<RemitRequest> tempRemitRequest = new ArrayList<RemitRequest>();
			int insertLine = (size > i * MAX_INSERT_LINE && size < (i + 1) * MAX_INSERT_LINE) ? size - i * MAX_INSERT_LINE : MAX_INSERT_LINE;
			for (int k = i * MAX_INSERT_LINE; k < insertLine; k++) {
				tempRemitRequest.add(remitRequest.get(k));
			}
			count += this.insert(tempRemitRequest);
			tempRemitRequest = null;
		}
		return count;
	}

	@Override
	public void batchUpdateToUnApprove(List<String> lists, String confirm) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lists", lists);
		paramMap.put("confirm", confirm);
		super.getSqlSession().update("batchUpdateToUnApprove", paramMap);
	}

	@Override
	public List<RemitRequest> listByIn(List<String> lists) {
		return super.getSqlSession().selectList("listByIdsIn", lists);
	}

	@Override
	public void batchUpdateToAudited(List<String> lists, String confirm) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lists", lists);
		paramMap.put("confirm", confirm);
		super.getSqlSession().update("batchUpdateToAudited", paramMap);
	}

	@Override
	public void batchUpdateRemitRequestStatus(Map<String, Object> paramMap) {
		super.getSqlSession().update("batchUpdateRemitRequestStatus", paramMap);
	}

	@Override
	public void batchUpdateToStatus(List<RemitRequest> remitRequests, int status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lists", remitRequests);
		paramMap.put("status", status);
		super.getSqlSession().update("batchUpdateToStatus", paramMap);
	}
	
}
