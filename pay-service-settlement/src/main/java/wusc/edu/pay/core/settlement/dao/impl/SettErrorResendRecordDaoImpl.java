package wusc.edu.pay.core.settlement.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.settlement.dao.SettErrorResendRecordDao;
import wusc.edu.pay.facade.settlement.entity.SettErrorResendRecord;


@Repository("settErrorResendRecordDao")
public class SettErrorResendRecordDaoImpl extends BaseDaoImpl<SettErrorResendRecord> implements SettErrorResendRecordDao {


}
