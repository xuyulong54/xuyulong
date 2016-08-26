package wusc.edu.pay.core.settlement.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.settlement.dao.SettErrorRecordDao;
import wusc.edu.pay.facade.settlement.entity.SettErrorRecord;


@Repository("settErrorRecordDao")
public class SettErrorRecordDaoImpl extends BaseDaoImpl<SettErrorRecord> implements SettErrorRecordDao {


}
