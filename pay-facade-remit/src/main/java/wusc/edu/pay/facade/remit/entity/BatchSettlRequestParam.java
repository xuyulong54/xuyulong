package wusc.edu.pay.facade.remit.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 批量打款规则传入参数
 * @author： Peter
 * @ClassName: BatchSettlRequestParam.java
 * @Date： 2014-8-7 下午2:51:56 
 * @version：  V1.0
 */
public class BatchSettlRequestParam implements Serializable {
	private static final long serialVersionUID = 6875744263845341685L;
	
	/**批量数据明细*/
	private List<SettlRequestParam> settReqeustList;
	/**总笔数*/
	private Integer totalNum;
	/**总金额*/
	private Double totalAmount;
	/**批量数据明细*/
	public List<SettlRequestParam> getSettReqeustList() {
		return settReqeustList;
	}
	/**批量数据明细*/
	public void setSettReqeustList(List<SettlRequestParam> settReqeustList) {
		this.settReqeustList = settReqeustList;
	}
	/**总笔数*/
	public Integer getTotalNum() {
		return totalNum;
	}
	/**总笔数*/
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	/**总金额*/
	public Double getTotalAmount() {
		return totalAmount;
	}
	/**总金额*/
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
