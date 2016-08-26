package wusc.edu.pay.facade.fee.utils;

import java.util.Comparator;

import wusc.edu.pay.facade.fee.entity.FeeFormula;


/**
 * 费率公式内资金比较器
 */
public class FeeFormulaComparator implements Comparator<FeeFormula> {

	@Override
	public int compare(FeeFormula o1, FeeFormula o2) {
		if(o1.getMinAmount().equals(o2.getMinAmount())){
			if(o1.getMinLadderAmount().compareTo(o2.getMinLadderAmount()) != 1){
				return -1;
			}
			else{
				return 1;
			}
		}
		else if(o1.getMinLadderAmount().equals(o2.getMinLadderAmount())){
			if(o1.getMinAmount().compareTo(o2.getMinAmount()) != 1){
				return -1;
			}
			else{
				return 1;
			}
		}
		return 0;
	}
}
