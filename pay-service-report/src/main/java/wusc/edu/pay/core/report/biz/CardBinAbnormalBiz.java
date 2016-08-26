package wusc.edu.pay.core.report.biz;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.CardBinAbnormalDao;


@Component("cardBinAbnormalBiz")
public class CardBinAbnormalBiz {

	@Autowired
	private CardBinAbnormalDao cardBinAbnormalDao;

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return cardBinAbnormalDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 创建卡bin统计数据
	 * @param dealDate
	 * @return
	 */
	public int createCardBinAbnormal(Date dealDate){
		return cardBinAbnormalDao.createCardBinAbnormal(dealDate);
	}

}
