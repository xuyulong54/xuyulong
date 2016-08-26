package wusc.edu.pay.facade.mobile.service;

import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;

import com.alibaba.dubbo.rpc.RpcException;


/***
 * @ClassName: MobileServiceFacade 
 * @Description: 手机端对外公布接口
 * @author Huang Bin 
 * @date 2015-3-27 上午9:50:03 
 */
public interface MobileServiceFacade {

	/***
	 * @Title: addMobileMerchantInfo 
	 * @Description: 手机端的商户注册
	 * @param @param userInfo
	 * @param @param merchant
	 * @param @param relation
	 * @param @param p2_LoginPwd    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	String addMobileMerchantInfo(UserInfo userInfo, MerchantOnline merchant, 
			AgentMerchantRelation relation, String p2_LoginPwd) throws UserBizException, RpcException;
	
	
	
}
