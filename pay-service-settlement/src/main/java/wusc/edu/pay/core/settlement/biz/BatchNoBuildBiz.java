package wusc.edu.pay.core.settlement.biz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("batchNoBuildBiz")
public class BatchNoBuildBiz {

	/**
	 * 结算--获取结算批次号，允许一天多次结算，通过次序区分，从1开始递增
	 * 
	 * @param accountNo
	 *            账户账号
	 * @param settleDay
	 *            结算日
	 * @param lastBatchCode
	 *            上次结算批次号,账户首次获取时可为null
	 * @return String 结算批次号 “账户账号-结算日-次序”
	 */
	public String getSettlementBatchCode(String accountNo, Date settleDay, String lastBatchCode) {
		return getCode(accountNo, settleDay, lastBatchCode);
	}

	/**
	 * 结算--获取打款请求Code，允许一天多次打款，通过次序区分，从1开始递增
	 * 
	 * @param accountNo
	 *            账户账号
	 * @param settleDay
	 *            结算日
	 * @param lastRemitCode
	 *            上次打款请求编号,账户首次获取时可为null
	 * @return String 打款请求Code “账户账号-结算日-次序”
	 */
	public String getRemitRequestCode(String accountNo, Date settleDay, String lastRemitCode) {
		return getCode(accountNo, settleDay, lastRemitCode);
	}

	/**
	 * 判断一个编号是否是在指定日期生成 编号类型(type)：1.结算Code 2.打款请求Code
	 * 
	 * @param date
	 *            指定日期
	 * @param code
	 *            待分析编号
	 * @param type
	 *            编号类型
	 * @return true: 是指定日期生成 false: 不是指定日期生成
	 */
	public boolean isCodeOnDay(Date date, String code, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String day = sdf.format(date);
		if (day.equals(code.substring(code.lastIndexOf("_") - 8, code.lastIndexOf("_")))) {
			return true;
		}
		return false;
	}

	
	/**
	 * 获取结算相关的打款请求编号、结算编号等， 允许一天多次结算、打款，通过次序区分，从1开始递增
	 * 
	 * @param accountNo
	 *            账户账号
	 * @param date
	 *            结算日
	 * @param lastNo
	 *            上次编号
	 * @return String 符合规则的编号
	 */
	private String getCode(String accountNo, Date date, String lastNo) {
		StringBuilder sb = new StringBuilder();
		// 定义以”yyyyMMdd“格式的 SimpleDateFormat，已准备对Date对象进行格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateString = sdf.format(date);
		// 非首次获取编号
		if (null != lastNo && !"".equals(lastNo)) {
			// 截取旧的编号的最后一个标识符”-“后的内容
			int position = lastNo.lastIndexOf("_");
			int seq = Integer.parseInt(lastNo.substring(position + 1));
			// 截取旧的编号中的时间部分
			String dateString2 = lastNo.substring(position - 8, position);

			// 如果是同一天，则将最后序号加1，否则直接新建一个编号
			if (dateString.equals(dateString2)) {
				int newSeq = seq + 1;
				sb.append(accountNo).append("_").append(dateString).append("_").append(newSeq);
			} else {
				sb.append(accountNo).append("_").append(dateString).append("_").append(1);
			}
		}
		// 首次获取编号
		else {
			sb.append(accountNo).append("_").append(dateString).append("_").append(1);
		}

		return sb.toString();
	}
}
