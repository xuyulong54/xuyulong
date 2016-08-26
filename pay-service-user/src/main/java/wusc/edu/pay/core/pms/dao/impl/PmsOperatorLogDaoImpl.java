/**
 * 
 */
package wusc.edu.pay.core.pms.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.pms.dao.PmsOperatorLogDao;
import wusc.edu.pay.facade.pms.entity.PmsOperatorLog;


/**
 * @描述: 权限管理-操作员操作日志数据访问层接口实现类.
 * @作者: WuShuicheng.
 * @创建时间: 2013-12-31,下午4:20:37.
 * @版本号: V1.0 .
 * 
 */
@Repository("pmsOperatorLogDao")
public class PmsOperatorLogDaoImpl extends BaseDaoImpl<PmsOperatorLog> implements PmsOperatorLogDao {

}
