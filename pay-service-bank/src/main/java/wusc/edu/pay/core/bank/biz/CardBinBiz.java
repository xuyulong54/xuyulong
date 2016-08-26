package wusc.edu.pay.core.bank.biz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.bank.dao.CardBinDao;
import wusc.edu.pay.facade.bank.entity.CardBin;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;


/**
 * 
 * @描述: 银行卡卡Bin信息管理业务逻辑层 .
 * @作者: HuPitao, WuShuicheng .
 * @创建时间: 2014-4-16, 下午1:49:03
 */
@Component("cardBinBiz")
public class CardBinBiz extends BaseBizImpl<CardBin> {

	@Autowired
	private CardBinDao cardBinDao;

	@Override
	protected BaseDao<CardBin> getDao() {
		return cardBinDao;
	}

	/**
	 * 根据卡Bin值查找卡Bin信息.
	 * @param cardBin
	 * @return
	 */
	public CardBin getByCardBin(String cardBin, Integer status) {
		if(StringUtils.isBlank(cardBin)){
			throw new BankBizException(BankBizException.BANK_SERVICE_PARAMS_ERROR, "cardBin不能为空:%s", cardBin);
		}
		return cardBinDao.getByCardBin(cardBin, status);
	}

}
