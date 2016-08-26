/**
 * wusc.edu.pay.core.remit.dao.impl.RemitErrorDaoImpl.java
 */
package wusc.edu.pay.core.remit.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.remit.dao.RemitErrorDao;
import wusc.edu.pay.facade.remit.entity.RemitError;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * 
 * @author Administrator
 * 
 */
@Repository("remitErrorDao")
public class RemitErrorDaoImpl extends BaseDaoImpl<RemitError> implements RemitErrorDao {

	public RemitError getByRequestNo(String requestNo) throws RemitBizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("requestNo", requestNo);
		return super.getBy(paramMap);
	}
}
