package wusc.edu.pay.core.report.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.CardBinAbnormal;



public interface CardBinAbnormalDao extends BaseDao<CardBinAbnormal>{

	int createCardBinAbnormal(Date dealDate);
}
