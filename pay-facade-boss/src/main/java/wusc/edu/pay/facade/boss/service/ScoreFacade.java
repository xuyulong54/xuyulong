package wusc.edu.pay.facade.boss.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.boss.entity.Score;
import wusc.edu.pay.facade.boss.entity.ScoreLog;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;


/**
 * 类描述：积分管理对外接口
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：上午11:49:55
 * @version 1.0
 */
public interface ScoreFacade {

	/***
	 * 创建积分信息
	 * 
	 * @param score
	 * @return
	 */
	public long createScore(Score score);

	/***
	 * 修改积分信息
	 * 
	 * @param score
	 * @return
	 */
	public long updScore(Score score);

	/***
	 * 根据条件查询积分信息
	 * 
	 * @param map
	 * @return
	 */
	public Score getBy(Map<String, Object> map) throws BossBizException;

	/***
	 * 创建积分记录表信息
	 * 
	 * @param scoreLog
	 * @return
	 * @throws BossBizException
	 */
	public long createScoreLog(ScoreLog scoreLog) throws BossBizException;

	/***
	 * 查询积分记录列表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 * @throws BossBizException
	 */
	public PageBean queryPage(PageParam pageParam, Map<String, Object> paramMap) throws BossBizException;

	/***
	 * 账户登录增加登录积分
	 * 
	 * @param accountId  账户ID
	 * @param score		   积分
	 * @param changeDesc 变更描述
	 * @throws BossBizException
	 */
	public long loginAddScore(String accountNo, Long score, String changeDesc) throws BossBizException;
}
