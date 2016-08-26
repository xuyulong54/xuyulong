package wusc.edu.pay.facade.banklink.netpay.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * 
 * @描述: 银行后置异常.
 * @作者: HuPitao.
 * @创建: 2014-7-22,下午1:56:07
 * @版本: V1.0
 * 
 */
public class BankLinkBizException extends BizException {

	private static final long serialVersionUID = -7779332048157371401L;

	public static final BankLinkBizException PAYINTERFACE_IS_NULL = new BankLinkBizException(10130001, "支付接口为空");
	public static final BankLinkBizException BANK_ORDERNO_IS_NULL = new BankLinkBizException(10130002, "银行订单号为空");
	public static final BankLinkBizException PAYMENTTRXNO_IS_NULL = new BankLinkBizException(10130003, "平台支付流水号为空");
	public static final BankLinkBizException PRODUCTNAME_IS_NULL = new BankLinkBizException(10130004, "商品名称为空");
	public static final BankLinkBizException PAYERIP_IS_NULL = new BankLinkBizException(10130005, "支付IP为空");
	public static final BankLinkBizException PAYER_BANKACCOUNTNO_IS_NULL = new BankLinkBizException(10130006, "客户银行账号为空");
	public static final BankLinkBizException MERCHANTNO_IS_NULL = new BankLinkBizException(10130007, "商户编号为空");
	public static final BankLinkBizException ORDERDATE_IS_NULL = new BankLinkBizException(10130008, "下单日期为空");
	public static final BankLinkBizException CURRENCY_IS_NULL = new BankLinkBizException(10130009, "币种为空");
	public static final BankLinkBizException PAYAMOUNT_IS_NULL = new BankLinkBizException(10130010, "金额为空");
	public static final BankLinkBizException BANK_SIGN_ERR = new BankLinkBizException(10130011, "银行签名异常");
	public static final BankLinkBizException BANK_QUERY_ERR = new BankLinkBizException(10130012, "银行查询异常");
	public static final BankLinkBizException BANK_CONNECT_ERR = new BankLinkBizException(10130013, "银行连接异常");
	public static final BankLinkBizException PARSE_ERR = new BankLinkBizException(10130014, "银行连接异常");
	
	public BankLinkBizException() {
	}

	public BankLinkBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public BankLinkBizException(int code, String msg) {
		super(code, msg);
	}
	
	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public BankLinkBizException newInstance(String msgFormat, Object... args) {
		return new BankLinkBizException(this.code, msgFormat, args);
	}

}
