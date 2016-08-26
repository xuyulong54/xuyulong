package wusc.edu.pay.core.banklink.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @描述: 工厂类 .
 * @作者: WuShuicheng.
 * @创建: 2015-3-11,下午5:35:05
 * @版本: V1.0
 *
 */
@Component("banklinkBizFactory")
public class BanklinkBizFactory implements BeanFactoryAware {

	private BeanFactory beanFactory;

	public Object getService(String payInterface) {
		return beanFactory.getBean(payInterface);
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
