package com.boss.biz.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.core.boss.biz.ArticleBiz;
import wusc.edu.pay.facade.boss.entity.Article;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context-boss.xml"})
@Transactional 
public class BossBizTestBak {

	@Autowired
	private ArticleBiz articleBiz;
	
	@Test 
	public void testArticle(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1");
		map.put("status", "100");
		Article a = articleBiz.getBy(map);
		System.out.println(a.getId());
	}
	
}
