package wusc.edu.pay.web.boss.action.remit.onlinepayment.springfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component("bankOnlineExportFactory")
public class BankOnlineExportFactory implements BeanFactoryAware {

	private BeanFactory beanFactory;

	public Object getService(String payInterface) {
		return beanFactory.getBean(payInterface);
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
