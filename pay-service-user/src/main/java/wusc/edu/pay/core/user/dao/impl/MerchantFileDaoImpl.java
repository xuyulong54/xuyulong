package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.MerchantFileDao;
import wusc.edu.pay.facade.user.entity.MerchantFile;



/***
 * 商户资质信息的Dao接口实现类
 * @author huangbin
 *
 */
@Repository("merchantFileDao")
public class MerchantFileDaoImpl extends BaseDaoImpl<MerchantFile> implements MerchantFileDao {

	/***
	 * 根据商户编号查询资质文件信息
	 * @param merchantNo
	 * @return
	 */
	public MerchantFile getByMerchantNo(String merchantNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", merchantNo);
		return super.getBy(paramMap);
	}
	
}
