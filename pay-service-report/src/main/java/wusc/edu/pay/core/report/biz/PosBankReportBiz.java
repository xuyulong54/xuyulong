package wusc.edu.pay.core.report.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.PosBankReportDao;
import wusc.edu.pay.facade.report.entity.PosBankReport;


/***
 * 
 * @描述: POS银行报表数据.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午7:52:24 .
 * @版本: V1.0.
 *
 */
@Component("posBankReportBiz")
public class PosBankReportBiz {
	
	@Autowired
	private PosBankReportDao posBankReportDao;
	
	public long create(PosBankReport entity) {
		return posBankReportDao.insert(entity);
	}

	public PosBankReport getById(long id) {
		return posBankReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return posBankReportDao.listPage(pageParam, paramMap);
	}

	public List<PosBankReport> listByDate(Map<String, Object> posBankMap) {
		return posBankReportDao.listBy(posBankMap);
	}

}
