package wusc.edu.pay.core.remit.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.remit.entity.RemitError;


public interface RemitErrorDao extends BaseDao<RemitError> {

	public RemitError getByRequestNo(String requestNo);
}
