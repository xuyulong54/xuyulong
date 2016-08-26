package wusc.edu.pay.facade.bank.service;

import wusc.edu.pay.facade.bank.entity.BankChannel;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;

/**
 * 
 * @描述: 银行服务功能缓存接口.
 * @作者: WuShuicheng.
 * @创建: 2014-11-18,下午3:42:58
 * @版本: V1.0
 *
 */
public interface BankCacheFacade {
	
	/**
	 * 根据银行渠道编号从缓存中查找银行渠道信息.
	 * 
	 * @param bankChannelCode
	 * @return BankChannel .
	 */
	public BankChannel getBankChannelByChannelCodeInCache(String bankChannelCode) throws BankBizException;

}
