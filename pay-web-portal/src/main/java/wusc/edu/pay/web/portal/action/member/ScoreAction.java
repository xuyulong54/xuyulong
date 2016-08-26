package wusc.edu.pay.web.portal.action.member;

import java.util.HashMap;
import java.util.Map;

import wusc.edu.pay.web.portal.base.BaseAction;


/**
 * 积分管理
 * 
 * @author liliqiong
 * @date 2013-12-26
 * @version 1.0
 */
public class ScoreAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 积分展示
	 * 
	 * @return
	 */
	public String viewScore() {
		// 积分
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountNo", getCurrentUserInfo().getAccountNo());
		putData("loginScore", getScore().getLoginScore());
		return "viewScore";

	}

}
