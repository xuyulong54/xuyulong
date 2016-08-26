package wusc.edu.pay.facade.boss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.biz.ArticleBiz;
import wusc.edu.pay.facade.boss.entity.Article;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.boss.service.ArticleFacade;


/**
 * 类描述：
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：下午2:39:33
 * @version 1.0
 */
@Component("articleFacade")
public class ArticleFacadeImpl implements ArticleFacade {
	@Autowired
	private ArticleBiz articleBiz;

	public long create(Article article) {
		return articleBiz.create(article);
	}

	public long update(Article article) {
		return articleBiz.update(article);
	}

	public Article getById(long id) {
		return articleBiz.getById(id);
	}

	public Article getBy(Map<String, Object> map) {
		return articleBiz.getBy(map);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BossBizException {
		return articleBiz.listPage(pageParam, paramMap);
	}

	@Override
	public List<Article> listBy(Map<String, Object> paramMap) {
		return articleBiz.listBy(paramMap) ;
	}

}
