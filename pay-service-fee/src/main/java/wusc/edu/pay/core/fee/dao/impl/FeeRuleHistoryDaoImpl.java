package wusc.edu.pay.core.fee.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.fee.dao.FeeRuleHistoryDao;
import wusc.edu.pay.facade.fee.entity.FeeRuleHistory;


@Repository("feeRuleHistoryDao")
public class FeeRuleHistoryDaoImpl extends BaseDaoImpl<FeeRuleHistory> implements FeeRuleHistoryDao {
	/**
	 * 查询费率规则历史
	 * 
	 * @param userNo
	 *            费率调用者编号
	 * @param userType
	 *            用户类型
	 * @param calculateFeeItem
	 *            计费项 CalculateFeeItemEnum
	 * @param payProduct
	 *            计费产品编号
	 * @param frpCode
	 *            支付方式编号
	 * @param start
	 *            开始时间
	 * @return
	 */
	@Override
	public List<FeeRuleHistory> queryFeeRuleHistory(String userNo, Integer userType, Integer calculateFeeItem, String payProduct, String frpCode, Date start) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("userType", userType);
		paramMap.put("calculateFeeItem", calculateFeeItem);
		paramMap.put("payProduct", payProduct);
		paramMap.put("frpCode", frpCode);
		paramMap.put("start", start);

		return getSessionTemplate().selectList(getStatement("queryFeeRuleHistory"), paramMap);
	}

}
