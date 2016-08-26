package wusc.edu.pay.core.boss.biz;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.dao.ScoreDao;
import wusc.edu.pay.core.boss.dao.ScoreLogDao;
import wusc.edu.pay.facade.boss.entity.Score;
import wusc.edu.pay.facade.boss.entity.ScoreLog;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;


/**
 * 类描述：积分管理Biz
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：上午11:45:41
 * @version 1.0
 */
@Component("scoreBiz")
public class ScoreBiz {
	@Autowired
	private ScoreDao scoreDao;
	@Autowired
	private ScoreLogDao scoreLogDao;

	/***
	 * 创建积分信息
	 * 
	 * @ score
	 * @return
	 */
	public long createScore(Score score) {
		return scoreDao.insert(score);
	}

	/***
	 * 修改积分信息
	 * 
	 * @ score
	 * @return
	 */
	public long updScore(Score score) {
		return scoreDao.update(score);
	}

	/***
	 * 根据条件查询
	 * 
	 * @ map
	 * @return
	 */
	public Score getBy(Map<String, Object> map) {
		return scoreDao.getBy(map);
	}

	/***
	 * 创建积分记录信息
	 * 
	 * @ score
	 * @return
	 */
	public long createScoreLog(ScoreLog scoreLog) {
		return scoreLogDao.insert(scoreLog);
	}

	/***
	 * 查询积分记录列表
	 * 
	 * @ page
	 * @ Map
	 * @return
	 * @throws BossBizException
	 */
	public PageBean queryPage(PageParam page, Map<String, Object> Map) {
		return scoreLogDao.listPage(page, Map);
	}

	/***
	 * 累加登录积分
	 * 
	 * @ accountId
	 * @ score
	 * @return
	 */
	public long loginAddScore(String  accountNo, Long score, String changeDesc) {
		Long result = 0L;
		// 增加积分记录表数据
		ScoreLog scoreLog = new ScoreLog();
		scoreLog.setAccountNo(accountNo); // 账户ID
		scoreLog.setChangeDesc(changeDesc); // 描述
		scoreLog.setChangeScore(score); // 积分
		if (this.createScoreLog(scoreLog) > 0) {
			// 根据账户查询积分信息表
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accountNo", accountNo);
			Score scoreInfo = this.getBy(map);
			if (scoreInfo != null) { // 在之前登录积分基础上累加登录积分
				scoreInfo.setLoginScore(scoreInfo.getLoginScore() + score);
				if (this.updScore(scoreInfo) > 0) result = 1L;
			} else { // 创建积分数据
				scoreInfo = new Score();
				scoreInfo.setAccountNo(accountNo);
				scoreInfo.setLoginScore(score);
				result = this.createScore(scoreInfo);
			}
		}
		return result;
	}
}
