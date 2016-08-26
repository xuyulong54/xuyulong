package wusc.edu.pay.facade.bank.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.bank.entity.BankSettlement;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;


/**
 * 
 * @描述: 银行结算信息管理，Dubbo服务接口.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午5:58:36
 */
public interface BankSettlementFacade {
	/**
	 * 创建银行结算信息
	 * 
	 * @param entity
	 * @return
	 */
	public long create(BankSettlement entity) throws BankBizException;

	/**
	 * 修改银行结算信息
	 * 
	 * @param entity
	 * @return
	 */
	public long update(BankSettlement entity) throws BankBizException;

	/**
	 * 分页查询银行结算信息
	 * 
	 * @param pageParam
	 *            分页实体对象
	 * @param paramMap
	 *            查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

	/**
	 * 根据ID查找银行结算信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public BankSettlement getById(long id) throws BankBizException;

	/**
	 * 根据银行渠道编号获取银行结算信息.
	 * 
	 * @param bankChannelCode
	 *            银行渠道编号.
	 * @return BankSettlement.
	 */
	public BankSettlement getByBankBankChannelCode(String bankChannelCode) throws BankBizException;

	/**
	 * 获取银行渠道状态为可用(100)的银行结算信息，包括以下字段内容:<br/>
	 * "bankChannelCode:银行渠道编号"、<br/>
	 * "tradeGainCheckFileTime:业务对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "fundGainCheckFileTime:清算对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "settleCycle:结算周期：T+X"
	 * 
	 * @return List<Map>.
	 */
	public List listAvailableBankSettlementInfo() throws BankBizException;
	
	/**
	 * 获取银行渠道可用状态下的银行账号
	 * @return
	 * @throws BankBizException
	 */
	public List listAvailableBankAccount() throws BankBizException;
}
