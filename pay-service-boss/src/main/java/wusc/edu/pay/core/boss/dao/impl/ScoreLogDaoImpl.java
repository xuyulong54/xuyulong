package wusc.edu.pay.core.boss.dao.impl; 

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.boss.dao.ScoreLogDao;
import wusc.edu.pay.facade.boss.entity.ScoreLog;


/**
 *类描述：积分信息Dao实现
 *@author: huangbin
 *@date： 日期：2013-12-3 时间：上午11:37:50
 *@version 1.0
 */
@Repository("scoreLogDao")
public class ScoreLogDaoImpl extends BaseDaoImpl<ScoreLog> implements ScoreLogDao {

}
 