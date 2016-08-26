package wusc.edu.pay.facade.remit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.remit.biz.RemitBiz;
import wusc.edu.pay.core.remit.biz.RemitRequestBiz;
import wusc.edu.pay.core.remit.biz.sub.RemitCheckBiz;
import wusc.edu.pay.core.remit.biz.sub.RemitReCreateBiz;
import wusc.edu.pay.core.remit.biz.sub.RemitReviewBiz;
import wusc.edu.pay.core.remit.biz.sub.RemitRouterBiz;
import wusc.edu.pay.facade.remit.entity.SettlRequestParam;
import wusc.edu.pay.facade.remit.service.RemitFacade;


/**
 * @描述: 打款对外服务接口实现类.
 * @作者: huqian .
 * @创建时间: 2015-3-12,上午11:44:57 .
 * @版本: 1.0 .
 */
@Component("remitFacade")
public class RemitFacadeImpl implements RemitFacade {

	@Autowired
	private RemitCheckBiz remitCheckBiz;
	@Autowired
	private RemitRequestBiz remitRequestBiz;
	@Autowired
	private RemitReviewBiz remitReviewBiz;
	@Autowired
	private RemitBiz remitBiz;
	@Autowired
	private RemitRouterBiz remitRouterBiz;
	@Autowired
	private RemitReCreateBiz remitReCreateBiz;

	/**
	 * 创建单笔打款请求_接口生成_自动审核,复核
	 * 
	 * @param settlRequestParam
	 */
	public void create(SettlRequestParam settlRequestParam) {
		remitRequestBiz.createAndAutoCheck(settlRequestParam);
	}

	/**
	 * 创建打款请求_接口生成_自动审核,复核
	 * 
	 * @param listSettlRequestParam
	 */
	public void create(List<SettlRequestParam> listSettlRequestParam) {
		remitRequestBiz.createAndAutoCheck(listSettlRequestParam);
	}

	/**
	 * 批量制单审核通过
	 * 
	 * @param ids
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void checkSuccess(String[] ids, String checkLoginName) {
		remitCheckBiz.checkSuccess(ids, checkLoginName);
	}

	/**
	 * 批量制单审核不通过
	 * 
	 * @param ids
	 * 
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void checkFail(String[] ids, String checkLoginName) {
		remitCheckBiz.checkFail(ids, checkLoginName);
	}

	/**
	 * 复核通过
	 * 
	 * @param ids
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void reviewSuccess(String[] ids, String reviewLoginName) {
		remitReviewBiz.reviewSuccess(ids, reviewLoginName);
	}

	/**
	 * 复核失败
	 * 
	 * @param ids
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void reviewFail(String[] ids, String reviewLoginName) {
		remitReviewBiz.reviewFail(ids, reviewLoginName);
	}

	/**
	 * 打款成功
	 * 
	 * @param batchNo
	 * @param loginName
	 */
	public void remitSuccess(String batchNo, String loginName) {
		remitBiz.remitSuccess(batchNo, loginName);
	}

	/**
	 * 打款失败
	 * 
	 * @param batchNo
	 * @param loginName
	 * @param remitRemark
	 */
	public void remitFail(String batchNo, String loginName, String remitRemark) {
		remitBiz.remitFail(batchNo, loginName, remitRemark);
	}

	/**
	 * 发起批次
	 */
	@Override
	public void lanunchBatch() {
		remitRouterBiz.lanunchBatch();		
	}
	
	/**
	 * 重新制单
	 * 
	 * @param ids
	 * @param loginName
	 */
	public void reCreate(String[] ids, String loginName) {
		remitReCreateBiz.reCreate(ids, loginName);
	}
}
