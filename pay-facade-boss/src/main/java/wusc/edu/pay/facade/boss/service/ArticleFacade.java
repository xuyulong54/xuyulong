package wusc.edu.pay.facade.boss.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.boss.entity.Article;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;


/**
 * 类描述：1:文章 2:招聘 3:常见问题 4:产品 管理 对外发布接口
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：下午2:37:13
 * @version 1.0
 */
public interface ArticleFacade {

	public long create(Article entity) throws BossBizException;

	public long update(Article entity) throws BossBizException;

	public Article getById(long id) throws BossBizException;

	public Article getBy(Map<String, Object> map) throws BossBizException;

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BossBizException;
	
	public List<Article> listBy(Map<String, Object> paramMap);

}
