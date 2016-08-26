package wusc.edu.pay.facade.boss.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.biz.ScoreBiz;
import wusc.edu.pay.facade.boss.entity.Score;
import wusc.edu.pay.facade.boss.entity.ScoreLog;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.boss.service.ScoreFacade;


/**
 * 类描述：
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：上午11:58:09
 * @version 1.0
 */
@Component("scoreFacade")
public class ScoreFacadeImpl implements ScoreFacade {
	@Autowired
	private ScoreBiz scoreBiz;

	/***
	 * 创建积分信息
	 * 
	 * @param score
	 * @return
	 */
	public long createScore(Score score) {
		return scoreBiz.createScore(score);
	}

	/***
	 * 修改积分信息
	 * 
	 * @param score
	 * @return
	 */
	public long updScore(Score score) {
		return scoreBiz.updScore(score);
	}

	/***
	 * 根据条件查询积分信息
	 * 
	 * @param map
	 * @return
	 */
	public Score getBy(Map<String, Object> map) throws BossBizException {
		return scoreBiz.getBy(map);
	}

	/***
	 * 创建积分记录表信息
	 * 
	 * @param scoreLog
	 * @return
	 * @throws BossBizException
	 */
	public long createScoreLog(ScoreLog scoreLog) throws BossBizException {
		return scoreBiz.createScoreLog(scoreLog);
	}

	/***
	 * 查询积分记录列表-分页
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 * @throws BossBizException
	 */
	public PageBean queryPage(PageParam pageParam, Map<String, Object> paramMap) throws BossBizException {
		return scoreBiz.queryPage(pageParam, paramMap);
	}

	/***
	 * 账户登录增加登录积分
	 * 
	 * @param accountId
	 *            账户ID
	 * @param score
	 *            积分数
	 * @param changeDesc
	 *            积分变更描述
	 * @throws BossBizException
	 */
	public long loginAddScore(String accountNo, Long score, String changeDesc) throws BossBizException {
		return scoreBiz.loginAddScore(accountNo, score, changeDesc);
	}
}
