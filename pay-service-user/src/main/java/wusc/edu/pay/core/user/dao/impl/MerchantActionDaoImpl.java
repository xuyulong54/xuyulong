package wusc.edu.pay.core.user.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.MerchantActionDao;
import wusc.edu.pay.facade.user.entity.MerchantAction;


/**
 * 
 * @描述: 商户权限管理--权限功能点表数据访问层接口实现类 .
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午3:41:08 .
 * @版本: 1.0 .
 */
@Repository("merchantActionDao")
public class MerchantActionDaoImpl extends BaseDaoImpl<MerchantAction> implements MerchantActionDao {

	/**
	 * 根据实体ID集字符串获取获取对象列表.
	 * 
	 * @param idStr
	 *            .
	 * @return
	 */
	public List<MerchantAction> listByIds(String idStr) {
		List<String> ids = Arrays.asList(idStr.split(","));
		return this.getSqlSession().selectList(getStatement("findByIds"), ids);
	}

	@Override
	public List<MerchantAction> listMerActionByMerType(String merType) {
		Map<String, Object> params = new HashMap<String, Object>();
	//	params.put("merType", merType);
		return super.getSqlSession().selectList(getStatement("listMerActionByMerType"), params);
	}

}
