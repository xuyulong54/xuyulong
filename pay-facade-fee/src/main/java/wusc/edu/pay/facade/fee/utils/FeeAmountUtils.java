package wusc.edu.pay.facade.fee.utils;

/**
 * 费率金额工具类
 */
public class FeeAmountUtils {

	/**
	 * 金额比较（小于） 目标值小于待比较基准值
	 * 
	 * @param target
	 *            目标值
	 * @param amount
	 *            待比较基准值
	 * @return
	 */
	public static boolean isThan(Double target, Double amount) {
		if (null == target || null == amount) {
			throw new IllegalArgumentException("Illegal argument exception, target and amount can not be null.");
		} else {
			return target.compareTo(amount) == -1;
		}
	}

	/**
	 * 判断目标金额是否在相关区间内
	 * 
	 * @param left
	 * @param right
	 * @param target
	 * @return
	 */
	public static boolean isBetween(Double left, Double right, Double target){
		if(null == left || null == target){
			throw new IllegalArgumentException("Illegal argument, the target and left amount can not be null!");
		}
		else if(null == right){
			return target.compareTo(left) == 1;
		}
		else{
			if(left.compareTo(0D) == 0 && target.compareTo(0D) == 0){
				return true;
			}
			else{
				return (target.compareTo(left) == 1)&&(target.compareTo(right) == 0 || target.compareTo(right) == -1);
			}
		}
	}

}
