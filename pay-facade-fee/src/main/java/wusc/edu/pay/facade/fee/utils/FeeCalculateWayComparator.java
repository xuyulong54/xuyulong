package wusc.edu.pay.facade.fee.utils;

import java.util.Comparator;
import java.util.Date;

import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;


/**
 * 计费方式比较器
 */
public class FeeCalculateWayComparator implements Comparator<FeeCalculateWay> {

	@Override
	public int compare(FeeCalculateWay paramT1, FeeCalculateWay paramT2) {
		Date startT1 = paramT1.getEffectiveDateStart();
		Date startT2 = paramT2.getEffectiveDateStart();
		if(!startT1.after(startT2)){
			return 1;
		}
		else{
			return -1;
		}
	}

}
