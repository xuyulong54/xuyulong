package wusc.edu.pay.webservice.merchant.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.webservice.merchant.biz.FastPayBiz;
import wusc.edu.pay.webservice.merchant.enums.FastPayMessageEnum;


/**
 * 快捷支付绑定银行卡Action
 * @作者 Peter
 * @时间 2014-01-04
 * */
public class FastPayBindBankAction extends FastPayBaseAction {
	private static final long serialVersionUID = -7796094341688066243L;
	private static final Log log = LogFactory.getLog(FastPayBindBankAction.class);
	
//	@Autowired
//	private EncyptionBiz encyptionBiz;
	@Autowired
	private FastPayBiz fastPayBiz;
	
	public String execute(){
		HttpSession session = getRedisSession();
		if(session == null ){
			log.error("获取不到sessionId对应的session");
			return null;
		}
//		String workKey = (String)session.getAttribute("workKey");
//		String publicKey6 = (String)session.getAttribute("publicKey6");
//		String privateKey5 = (String)session.getAttribute("privateKey5");
		//调用解密验签方法
		Map<String , Object> paramMap = new HashMap<String , Object>();
		Map<String , Object> returnMap = new HashMap<String , Object>();
		paramMap.put("cardType", "1");
		paramMap.put("cardNo","6222033300998877221");
		boolean flag = false;
		if(flag){
			
		}else{
			FastPayMessageEnum errMsg = valideParam(paramMap);
		   if(errMsg != null){
			   //参数错误
		   }else{
			   returnMap = fastPayBiz.bindBankCard(paramMap, session);
			   //更新session缓存
			   saveRedisSession(session.getId(), session);
		   }
		}
		//调用加密加签方法
		String signStr = returnMap.toString();//通过加密加签方法将returnParam生成的字符串
		outPrint(getHttpResponse(),signStr);//将数据返回
		return null;
	}
	
	private FastPayMessageEnum valideParam(Map<String, Object> paramMap){
		String cardType = (String)paramMap.get("cardType");
		if(StringUtil.isEmpty(cardType)){
			//卡类型为空
		}else{
			if("".equals(cardType)){
				//借记卡
			}else if("".equals(cardType)){
				//信用卡
			}else{
				//卡类型错误
			}
		}
		return null;
	}
}
