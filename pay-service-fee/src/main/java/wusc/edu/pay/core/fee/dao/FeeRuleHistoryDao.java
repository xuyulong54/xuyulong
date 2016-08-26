package wusc.edu.pay.core.fee.dao;

import java.util.Date;
import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.fee.entity.FeeRuleHistory;


public interface FeeRuleHistoryDao extends BaseDao<FeeRuleHistory> {

	List<FeeRuleHistory> queryFeeRuleHistory(String userNo, Integer userType, Integer calculateFeeItem, String payProduct, String frpCode, Date start);

}
