package wusc.edu.pay.facade.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.mobile.MobileBiz;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.mobile.service.MobileServiceFacade;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;

import com.alibaba.dubbo.rpc.RpcException;


/***
 * @ClassName: MobileServiceFacadeImpl 
 * @Description: 手机端对外公布接口实现类
 * @author Huang Bin 
 * @date 2015-3-27 上午9:51:20
 */
@Component("mobileServiceFacade")
public class MobileServiceFacadeImpl implements MobileServiceFacade {
	@Autowired
	private MobileBiz mobileBiz;
 
	/***
	 * @Title: addAgentMerchantInfo 
	 * @Description: 手机端的商户注册
	 * @param @param userInfo
	 * @param @param merchant
	 * @param @param relation
	 * @param @param p2_LoginPwd    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	public String addMobileMerchantInfo(UserInfo userInfo,
			MerchantOnline merchant, AgentMerchantRelation relation,
			String p2_LoginPwd) throws UserBizException, RpcException {
		
		return mobileBiz.addMobileMerchantInfo(userInfo, merchant, relation, p2_LoginPwd);
		
	}
	
	
}
