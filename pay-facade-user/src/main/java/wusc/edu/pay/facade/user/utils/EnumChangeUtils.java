package wusc.edu.pay.facade.user.utils;

import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;



/**
 * userType与accountType转换工具类
 * 
 * @version:
 */
public class EnumChangeUtils {

	/**
	 * accountType To userType
	 * @param cla
	 * @param source
	 * @return
	 */
	public static Integer getUserType(int accountType) {
		if (accountType != 0) {
			if(accountType == AccountTypeEnum.CUSTOMER.getValue()){
				return UserTypeEnum.CUSTOMER.getValue();
			}else if(accountType == AccountTypeEnum.MERCHANT.getValue()){
				return UserTypeEnum.MERCHANT.getValue();
			}else{
				return UserTypeEnum.POSAGENT.getValue();
			}
		}
		return null;
	}
	
	/**
	 * userType  to  accountType
	 * @param cla
	 * @param source
	 * @return
	 */
	public static Integer getAccountType(int userType) {
		if (userType != 0) {
			if(userType == UserTypeEnum.CUSTOMER.getValue()){
				return AccountTypeEnum.CUSTOMER.getValue();
			}else if(userType == UserTypeEnum.MERCHANT.getValue()){
				return AccountTypeEnum.MERCHANT.getValue();
			}else{
				return AccountTypeEnum.POS.getValue();
			}
		}
		return null;
	}

}
