package wusc.edu.pay.core.account.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.account.dao.AccountFrozenHistoryDao;
import wusc.edu.pay.facade.account.entity.AccountFrozenHistory;


@Repository("accountFrozenHistoryDao")
public class AccountFrozenHistoryDaoImpl extends BaseDaoImpl<AccountFrozenHistory> implements AccountFrozenHistoryDao {

}
