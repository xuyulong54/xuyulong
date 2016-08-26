package wusc.edu.pay.web.boss.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 交易成功
 * 
 * @author xiehui
 * @time 2013-8-14,下午3:29:03
 */
@Scope("prototype")
public class PaymentRecordStatAction extends BossBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6870447986546457654L;
	@Autowired
	private PaymentQueryFacade paymentQueryFacade;

	/**
	 * 可按条件查询并分页列出通知记录信息.
	 * 
	 * @return paymentRecordStatList or operateError.
	 */
	public String listPaymentRecordStat() {
		// 可选查询参数
		super.pageBean = paymentQueryFacade.queryPaymentRecordListPage(this.getPageParam(), null);
		this.pushData(pageBean);
		return "listPaymentRecordStat";
	}
}
