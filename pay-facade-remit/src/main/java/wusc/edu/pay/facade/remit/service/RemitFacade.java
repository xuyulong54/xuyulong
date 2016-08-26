package wusc.edu.pay.facade.remit.service;

import java.util.List;

import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.remit.entity.SettlRequestParam;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * 
 * @描述: 打款对外服务接口.
 * @作者: huqian .
 * @创建时间: 2015-3-12,下午11:47:33 .
 * @版本: 1.0 .
 */
public interface RemitFacade {

	/**
	 * 创建单笔打款请求_接口生成_自动审核,复核
	 * 
	 * @param settlRequestParam
	 */
	public void create(SettlRequestParam settlRequestParam) throws RemitBizException;

	/**
	 * 创建打款请求_接口生成_自动审核,复核
	 * 
	 * @param listSettlRequestParam
	 */
	public void create(List<SettlRequestParam> listSettlRequestParam) throws RemitBizException;

	/**
	 * 批量制单审核通过
	 * 
	 * @param ids
	 *            打款请求号
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void checkSuccess(String[] ids, String checkLoginName) throws RemitBizException;

	/**
	 * 批量制单审核不通过
	 * 
	 * @param ids
	 *            打款请求号
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void checkFail(String[] ids, String checkLoginName) throws RemitBizException;

	/**
	 * 复核通过
	 * 
	 * @param ids
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void reviewSuccess(String[] ids, String reviewLoginName) throws RemitBizException;

	/**
	 * 复核失败
	 * 
	 * @param ids
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void reviewFail(String[] ids, String reviewLoginName) throws RemitBizException;

	/**
	 * 打款成功
	 * 
	 * @param batchNo
	 * @param loginName
	 */
	public void remitSuccess(String batchNo, String loginName) throws RemitBizException, AccountBizException;

	/**
	 * 打款失败
	 * 
	 * @param batchNo
	 * @param loginName
	 * @param remitRemark
	 */
	public void remitFail(String batchNo, String loginName, String remitRemark) throws RemitBizException, AccountBizException;
	
	/**
	 * 发起批次
	 */
	public void lanunchBatch();
	
	/**
	 * 重新制单
	 * 
	 * @param ids
	 * @param loginName
	 */
	public void reCreate(String[] ids, String loginName) throws RemitBizException;
}
