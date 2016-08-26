package wusc.edu.pay.core.settlement.biz.sub;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.settlement.dao.SettErrorRecordDao;
import wusc.edu.pay.facade.settlement.DTO.LaunchSettleVo;
import wusc.edu.pay.facade.settlement.entity.SettErrorRecord;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;


/**
 * 
 * @描述: 结算异常记录处理biz.
 * @创建时间:2015-3-11,下午3:06:04.
 * @版本:
 */
@Component("settErrorRecordBiz")
public class SettErrorRecordBiz {
	@Autowired
	private SettErrorRecordDao settErrorRecordDao;

	private static final Log log = LogFactory.getLog(SettErrorRecordBiz.class);
	
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(256), new ThreadPoolExecutor.CallerRunsPolicy()); // 线程池
	
	
	/**
	 * 保存结算日志
	 * 
	 * @param errDesc
	 * @param settleParamVo
	 */
	public void addSettErrorRecord(String errDesc, LaunchSettleVo settleParamVo) {
		SettRule rule = settleParamVo.getSettRule();
		SettErrorRecord settErrorRecord = new SettErrorRecord();
		settErrorRecord.setUserNo(settleParamVo.getSettRule().getUserNo());
		settErrorRecord.setAccountNo(rule.getAccountNo());
		settErrorRecord.setEndDate(settleParamVo.getSettleEndDate());
		settErrorRecord.setErrDesc(errDesc);
		settErrorRecord.setRemark(settleParamVo.getRemark());
		settErrorRecord.setSettDate(settleParamVo.getCurrentDate());
		settErrorRecord.setSettMode(settleParamVo.getSettType());
		settErrorRecord.setBeginDate(rule.getLastSettDate());
		SettlementLogHandlerThread mt1 = new SettlementLogHandlerThread(settErrorRecord, settErrorRecordDao);
		threadPool.execute(mt1);
	}

	/**
	 * 保存结算日志
	 * 
	 * @param errDesc
	 * @param record
	 */
	public void addSettErrorRecord(String errDesc, SettRecord record) {
		SettErrorRecord settErrorRecord = new SettErrorRecord();
		settErrorRecord.setAccountNo(record.getAccountNo());
		settErrorRecord.setUserNo(record.getUserNo());
		settErrorRecord.setErrDesc(errDesc);
		settErrorRecord.setRemark(record.getRemark());
		settErrorRecord.setSettDate(record.getSettDate());
		settErrorRecord.setSettMode(record.getSettMode());
		settErrorRecord.setBeginDate(record.getBeginDate());
		settErrorRecord.setEndDate(record.getEndDate());
		SettlementLogHandlerThread mt1 = new SettlementLogHandlerThread(settErrorRecord, settErrorRecordDao);
		threadPool.execute(mt1);
	}

	/**
	 * 保存结算日志 thread
	 */
	public class SettlementLogHandlerThread implements Runnable {
		private SettErrorRecord settErrorRecord;
		private SettErrorRecordDao settErrorRecordDao;

		public SettlementLogHandlerThread(SettErrorRecord settErrorRecord, SettErrorRecordDao settErrorRecordDao) {
			this.settErrorRecord = settErrorRecord;
			this.settErrorRecordDao = settErrorRecordDao;
		}

		@Override
		public void run() {
			try {
				settErrorRecordDao.insert(settErrorRecord);
			} catch (Exception ex) {
				log.error("save settlelog failure", ex);
			}
		}
	}
}
