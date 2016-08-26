package wusc.edu.pay.web.boss.action;
//package wusc.edu.pay.web.boss.action;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.context.annotation.Scope;
//
//import wusc.edu.pay.common.page.PageBean;
//import wusc.edu.pay.web.boss.base.BossBaseAction;
///**
// * 通知日志
// * @author xiehui
// * @time 2013-8-14,上午9:44:10
// */
//@SuppressWarnings("serial")
//@Scope("prototype")
//public class NotifyRecordLogAction extends BossBaseAction{
//	private static Log log = LogFactory.getLog(NotifyRecordLogAction.class);
//	/**
//	 * 可按条件查询并分页列出通知记录信息.
//	 * @return listNotifyRecordLog .
//	 */
//	public  String listNotifyRecordLog(){
//		
//		try {
//			// 可选查询参数
//			String trxNo=getString("trxNo");
//			Map<String,Object> paramMap = new HashMap<String,Object>();
//			paramMap.put("trxNo",trxNo);
//			super.pageBean = getPaySvc().getNotifyRecordLogService().listPage(this.getPageParam(), paramMap);
//			this.pushData(pageBean);
//			return "listNotifyRecordLog";
//		} catch (Exception e) {
//			log.error("== listNotifyRecordLog exception:", e);
//			return operateError("获取信息异常");
//			
//		}
//		
//	}
//	
//}
