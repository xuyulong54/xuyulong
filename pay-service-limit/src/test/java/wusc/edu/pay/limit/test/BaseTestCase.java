package wusc.edu.pay.limit.test;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTestCase extends TestCase {
	protected static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			new String[] { "spring/spring-context.xml" });
}
