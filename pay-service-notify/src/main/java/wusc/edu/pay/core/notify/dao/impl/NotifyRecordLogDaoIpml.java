package wusc.edu.pay.core.notify.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.notify.dao.NotifyRecordLogDao;
import wusc.edu.pay.facade.notify.entity.NotifyRecordLog;


@Repository("notifyRecordLogDao")
public class NotifyRecordLogDaoIpml extends BaseDaoImpl<NotifyRecordLog> implements NotifyRecordLogDao {

	public long insert(NotifyRecordLog entity) {
		return this.getSqlSession().insert(getStatement(SQL_INSERT), entity);
	}

	public long update(NotifyRecordLog entity) {
		return 0;
	}

	public NotifyRecordLog getById(long id) {
		return null;
	}
}
