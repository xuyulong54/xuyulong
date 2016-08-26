package com.cost.facade;

import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TsBase {
	ClassPathXmlApplicationContext context;

	@Before
	public void preTs() throws Exception {
		context = new ClassPathXmlApplicationContext("consumer.xml");
	}

}
