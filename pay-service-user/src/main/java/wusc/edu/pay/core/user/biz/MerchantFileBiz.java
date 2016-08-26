package wusc.edu.pay.core.user.biz;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.MerchantFileDao;
import wusc.edu.pay.facade.user.entity.MerchantFile;



/***
 * 商户资质文件  biz 类
 * @author huangbin
 *
 */
@Component("merchantFileBiz")
public class MerchantFileBiz {
	
	@Autowired
	private MerchantFileDao merchantFileDao;

	/***
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MerchantFile getById(long id) {
		return merchantFileDao.getById(id);
	}

	/***
	 * 查询列表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantFileDao.listPage(pageParam, paramMap);
	}

	/***
	 * 更新实体信息
	 * @param entity
	 * @return
	 */
	public long update(MerchantFile entity) {
		return merchantFileDao.update(entity);
	}

	/***
	 * 添加新记录
	 * @param entity
	 * @return
	 */
	public long create(MerchantFile entity) {
		return merchantFileDao.insert(entity);
	}

	/***
	 * 根据商户编号查询资质文件信息
	 * @param merchantNo
	 * @return
	 */
	public MerchantFile getByMerchantNo(String merchantNo) {
		return merchantFileDao.getByMerchantNo(merchantNo);
	}

}
