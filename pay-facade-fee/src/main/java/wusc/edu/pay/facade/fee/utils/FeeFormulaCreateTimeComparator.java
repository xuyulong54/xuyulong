package wusc.edu.pay.facade.fee.utils;

import java.util.Comparator;
import java.util.Date;

import wusc.edu.pay.facade.fee.entity.FeeFormula;


/**
 * 费率公式内资金比较器
 */
public class FeeFormulaCreateTimeComparator implements Comparator<FeeFormula> {

	@Override
	public int compare(FeeFormula paramT1, FeeFormula paramT2) {
		Date startT1 = paramT1.getCreateTime();
		Date startT2 = paramT2.getCreateTime();
		if(!startT1.after(startT2)){
			return 1;
		}
		else{
			return -1;
		}
	}
}
