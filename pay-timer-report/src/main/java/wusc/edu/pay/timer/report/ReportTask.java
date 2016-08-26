package wusc.edu.pay.timer.report;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.timer.report.util.SpringContextUtil;


/**
 * 
 * @描述: 报表定时任务.
 * @作者: WuShuicheng.
 * @创建: 2014-5-6,下午5:29:34
 * @版本: V1.0
 * 
 *
 */
public class ReportTask {
	
	private static final Log LOG = LogFactory.getLog(ReportTask.class);

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context;

		try {
			context = new ClassPathXmlApplicationContext(new String[] { "spring-context.xml" });
			// 初始化SpringContextUtil
			final SpringContextUtil ctxUtil = new SpringContextUtil();
			ctxUtil.setApplicationContext(context);
			
			context.start();
			LOG.info("== ReportTask start");
			
			ReportBiz reportBiz = (ReportBiz) context.getBean("reportBiz");
			reportBiz.executeReport();
			
			LOG.info("== ReportTask end");
			context.stop();
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("== DubboReference context start error:", e);
		}finally{
			LOG.info("== ReportTask System.exit");
			System.exit(0);
		}
	}	
}
