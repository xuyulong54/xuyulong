package wusc.edu.pay.facade.bank.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.bank.entity.BankChannel;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;


/**
 * 
 * @描述: 银行渠道信息，Dubbo服务接口 .
 * @作者: huqian .
 * @创建时间: 2013-8-1 .
 * @版本: 1.0 .
 */

public interface BankChannelFacade {

	/**
	 * 根据银行渠道编号查找银行渠道信息.
	 * 
	 * @param bankChannelCode
	 * @return BankChannel .
	 */
	public BankChannel getByBankChannelCode(String bankChannelCode) throws BankBizException;

	
	/**
	 * 分页查询.
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

	/**
	 * 根据银行渠道ID获取银行渠道信息.
	 * 
	 * @param id
	 *            银行渠道ID.
	 * @return BankChannel.
	 */
	BankChannel getById(long id) throws BankBizException;

	/**
	 * 更新银行渠道信息.
	 * 
	 * @param entity
	 * @return
	 */
	long update(BankChannel entity) throws BankBizException;

	/**
	 * 新增银行渠道信息.
	 * 
	 * @param entity
	 * @return
	 */
	long create(BankChannel entity) throws BankBizException;

	/**
	 * 根据银行渠道编码删除银行渠道信息.
	 * 
	 * @param bankChannelCode
	 */
	void deleteChannelByCode(String bankChannelCode) throws BankBizException;

	/**
	 * 根据银行渠道去判断该银行渠道是否可用
	 * 
	 * @param bankChannelCode
	 * @return
	 */
	public boolean isUsableBankChannel(String bankChannelCode) throws BankBizException;

	/***
	 * 根据银行渠道名称查询银行渠道信息
	 * 
	 * @param channelName
	 * @return BankChannel .
	 */
	public BankChannel getByBankChannelName(String channelName) throws BankBizException;

	/**
	 * 根据银行协议ID获取银行渠道信息.
	 * 
	 * @param bankAgreementId
	 * @return BankChannel .
	 */
	public BankChannel getByBankAgreementId(long bankAgreementId) throws BankBizException;

	public List<BankChannel> listBy(Map<String, Object> paramMap) throws BankBizException;

	/**
	 * 根据协议表中的业务类型和账户表中的账户性质查询出对应的渠道
	 * 
	 * @param linkType
	 */
	public List<BankChannel> listChannalByAgreementBusTypeAndAccountType(int linkType, int accountType)
			throws BankBizException;
	
	/**
	 * 根据银行渠道编号模糊查找 
	 * @param bankChannelCode .
	 * @param status .
	 * @return List .
	 */
	public List likeBy(String bankChannelCode, Integer status)throws BankBizException;
}
