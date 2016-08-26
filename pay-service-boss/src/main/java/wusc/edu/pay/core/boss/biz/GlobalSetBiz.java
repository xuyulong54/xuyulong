package wusc.edu.pay.core.boss.biz;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.dao.GlobalSetDao;
import wusc.edu.pay.facade.boss.entity.GlobalSet;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;




/***
 * 
 *类描述：全局设置Biz接口
 *@author: blank
 *@date： 日期：2013-11-12 时间：下午2:25:38
 *@version 1.0
 */
@Component("globalSetBiz")
public class GlobalSetBiz {
	@Autowired
	private GlobalSetDao globalSetDao;
	
	
	public long create(GlobalSet set){
		return globalSetDao.insert(set);
	}
	
	public long update(GlobalSet set){
		return globalSetDao.update(set);
	}
	
	public GlobalSet getById(long id){
		return globalSetDao.getById(id);
	}
	
	/***
	 * 查询列表方法-分页
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryGlobalSetPage(PageParam pageParam, Map<String, Object> paramMap) {
		return globalSetDao.listPage(pageParam, paramMap);
	}

	/***
	 * 根据键名查询全局设置表数据
	 * @param keyValue 键名
	 * @return
	 * @throws BossBizException
	 */
	public GlobalSet getBySetKey(String keyValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("setKey", keyValue);
		return globalSetDao.getBy(map);
	}
}
