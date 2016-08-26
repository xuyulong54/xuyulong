package wusc.edu.pay.facade.agent.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 代理商利润分配vo
 * 
 * @author Administrator
 * 
 */
public class AgentSplidProfitVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4793762972985842033L;

	private String merchantNo; // 商户编号

	private String firstAgentNo; // 一级代理商编号
	private String secondAgentNo; // 二级代理商编号
	private String thirdAgentNo; // 三级代理商编号

	private BigDecimal firstSplidProfit = BigDecimal.ZERO; // 一级代理商分润
	private BigDecimal secondSplidProfit = BigDecimal.ZERO; // 二级代理商分润
	private BigDecimal thirdSplidProfit = BigDecimal.ZERO; // 三级代理商分润

	private String firstAgentName; // 一级代理商名称
	private String secondAgentName; // 二级代理商名称
	private String thirdAgentName; // 三级代理商名称

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getFirstAgentNo() {
		return firstAgentNo;
	}

	public void setFirstAgentNo(String firstAgentNo) {
		this.firstAgentNo = firstAgentNo;
	}

	public String getSecondAgentNo() {
		return secondAgentNo;
	}

	public void setSecondAgentNo(String secondAgentNo) {
		this.secondAgentNo = secondAgentNo;
	}

	public String getThirdAgentNo() {
		return thirdAgentNo;
	}

	public void setThirdAgentNo(String thirdAgentNo) {
		this.thirdAgentNo = thirdAgentNo;
	}

	public BigDecimal getFirstSplidProfit() {
		return firstSplidProfit;
	}

	public void setFirstSplidProfit(BigDecimal firstSplidProfit) {
		this.firstSplidProfit = firstSplidProfit;
	}

	public BigDecimal getSecondSplidProfit() {
		return secondSplidProfit;
	}

	public void setSecondSplidProfit(BigDecimal secondSplidProfit) {
		this.secondSplidProfit = secondSplidProfit;
	}

	public BigDecimal getThirdSplidProfit() {
		return thirdSplidProfit;
	}

	public void setThirdSplidProfit(BigDecimal thirdSplidProfit) {
		this.thirdSplidProfit = thirdSplidProfit;
	}

	public String getFirstAgentName() {
		return firstAgentName;
	}

	public void setFirstAgentName(String firstAgentName) {
		this.firstAgentName = firstAgentName;
	}

	public String getSecondAgentName() {
		return secondAgentName;
	}

	public void setSecondAgentName(String secondAgentName) {
		this.secondAgentName = secondAgentName;
	}

	public String getThirdAgentName() {
		return thirdAgentName;
	}

	public void setThirdAgentName(String thirdAgentName) {
		this.thirdAgentName = thirdAgentName;
	}

}
