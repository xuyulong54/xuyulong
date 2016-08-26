package wusc.edu.pay.core.account.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.account.dao.AccountFrozenRecordDao;
import wusc.edu.pay.facade.account.entity.AccountFrozenRecord;


@Repository("accountFrozenRecordDao")
public class AccountFrozenRecordDaoImpl extends BaseDaoImpl<AccountFrozenRecord> implements AccountFrozenRecordDao {

}
