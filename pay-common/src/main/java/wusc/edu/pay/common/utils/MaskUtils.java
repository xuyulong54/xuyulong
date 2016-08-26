package wusc.edu.pay.common.utils;

/**
 * 
 * @author healy
 * 
 */
public class MaskUtils {

	/**
	 * 隐藏手机号
	 * 
	 * @param cellphoneNo
	 * @return
	 */
	public static String maskCellphone(String cellphoneNo) {
		if (cellphoneNo == null || cellphoneNo.trim().length() != 11) {
			return cellphoneNo;
		}
		return new StringBuilder().append(cellphoneNo.substring(0, 3)).append("****").append(cellphoneNo.substring(cellphoneNo.length() - 4)).toString();
	}

	/**
	 * 隐藏邮箱信息
	 * 
	 * @param email
	 * @return
	 */
	public static String maskEmail(String email) {
		return email;
	}

	/**
	 * 隐藏卡号信息
	 * 
	 * @param cardNo
	 * @return
	 */
	private static String maskCardNo(String cardNo) {
		if (cardNo == null || cardNo.trim().length() <= 8) {
			return cardNo;
		}
		cardNo = cardNo.trim();
		int length = cardNo.length();
		String firstFourNo = cardNo.substring(0, 4);
		String lastFourNo = cardNo.substring(length - 4);
		StringBuffer mask = new StringBuffer("");
		for (int i = 0; i < length - 8; i++) {
			mask.append("*");
		}
		return firstFourNo + mask.toString() + lastFourNo;
	}

	/**
	 * 隐藏身份证号码
	 * 
	 * @param cardNo
	 * @return
	 */
	public static String maskIDCardNo(String idCardNo) {
		return maskCardNo(idCardNo);
	}

	/**
	 * 隐藏银行卡号码
	 * 
	 * @param cardNo
	 * @return
	 */
	public static String maskBankCardNo(String bankCardNo) {
		return maskCardNo(bankCardNo);
	}
}
