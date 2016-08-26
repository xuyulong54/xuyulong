package wusc.edu.pay.core.limit.biz;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.AmountCumulateDao;
import wusc.edu.pay.core.limit.periodstrategy.Period;
import wusc.edu.pay.core.limit.periodstrategy.impl.CreatePeriodStrategy;
import wusc.edu.pay.facade.limit.entity.AmountCumulate;
import wusc.edu.pay.facade.limit.entity.AmountLimit;


@Component(value = "amountCumulateBiz")
public class AmountCumulateBiz {
	@Autowired
	private AmountCumulateDao amountCumulateDao;
	
	@Autowired
	private CreatePeriodStrategy createPeriodStrategy;
	
	/****
	 * 查询列表方法
	 */
	public PageBean listPage(PageParam pageParam,
			Map<String, Object> paramMap) {
		return amountCumulateDao.listPage(pageParam, paramMap);
	}

	/***
	 * 增加
	 * @param entity
	 * @return
	 */
	public long saveAmountCumulate(AmountCumulate entity) {
		return amountCumulateDao.insert(entity);
	}

	/***
	 * 修改
	 * @param entity
	 * @return
	 */
	public long updateAmountCumulate(AmountCumulate entity) {
		return amountCumulateDao.update(entity);
	}

	/***
	 * 删除
	 * @param entity
	 * @return
	 */
	public long deleteAmountCumulate(Long id) {
		return amountCumulateDao.deleteById(id);
	}

	/***
	 * 根据ID查
	 * @param entity
	 * @return
	 */
	public AmountCumulate getById(Long id) {
		return amountCumulateDao.getById(id);
	}

	/**
	 * 累计交易流量
	 * @param merchantNo
	 * @param amountLimit
	 * @param tradeAmount
	 * @param orderDate
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor=Exception.class )
	public void accumulateFlow(String merchantNo,String bizFunction,
			AmountLimit amountLimit, BigDecimal tradeAmount, Date orderDate) {
		// 查询金额限制实体对应的金额累计记录
		AmountCumulate amountCumulate = amountCumulateDao
			.getAmountCumulateByLimitIdAndDateAndMerchantNo(merchantNo,amountLimit.getId(), orderDate);

		if (amountCumulate != null) {
			// 数据库中记录已存在，直接累加
			amountCumulate.increaseCumulateAmount(tradeAmount);
		} else {
			// 数据库中记录不存在，新建一个累计记录
			amountCumulate = new AmountCumulate();
			amountCumulate.setAmountLimitId(amountLimit.getId());
			amountCumulate.setMerchantNo(merchantNo);
			amountCumulate.setCumulateAmount(tradeAmount);
		}
		
		
		if (amountCumulate.isNew()) {
			createCumulativePeriod(merchantNo, amountLimit, bizFunction, amountCumulate, orderDate);
		}
		this.saveOrUpdate(amountCumulate);
	
	}

	public void saveOrUpdate(AmountCumulate tradeAmountCumulate) {
		if (tradeAmountCumulate.isNew())
			amountCumulateDao.insert(tradeAmountCumulate);
		else
			amountCumulateDao.update(tradeAmountCumulate);
	}
	
	
	/**
	 * 创建累计周期
	 */
	private void createCumulativePeriod(String merchantNo, AmountLimit amountLimit, String bizFunction, AmountCumulate tradeAmountCumulate, Date orderDate) {
		// 生成累计周期
		Period period = createPeriodStrategy.createPeriod(amountLimit.getLimitType(), orderDate);

		tradeAmountCumulate.setBeginTime(period.getBeginDate());
		tradeAmountCumulate.setEndTime(period.getEndDate());
	}

}
