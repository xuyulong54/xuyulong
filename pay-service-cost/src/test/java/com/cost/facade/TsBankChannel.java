package com.cost.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalCostInterface;
import wusc.edu.pay.facade.cost.service.CalCostInterfaceFacade;

public class TsBankChannel extends TsBase {

	@Test
	public void create() throws Exception {
		CalCostInterfaceFacade calCostInterfaceFacade = (CalCostInterfaceFacade) context.getBean("calCostInterfaceFacade");

		CalCostInterface calCostInterface = new CalCostInterface();
		calCostInterface.setBillCycle(2);
		calCostInterface.setChargeType(1);
		calCostInterface.setCustomBillCycle(4);
		calCostInterface.setCustomBillDay(new Date());
		calCostInterface.setInterfaceCode("cal-interface");
		calCostInterface.setInterfaceName("interfaceName");
		calCostInterface.setModifyTime(new Date());
		calCostInterface.setPolicy(1);
		calCostInterface.setRemark("remark");
		calCostInterface.setStatus(100);

		Long result = calCostInterfaceFacade.create(calCostInterface);

		// 列表
		PageParam pageParam = new PageParam(1, 10);
		PageBean pageBean = calCostInterfaceFacade.listPage(pageParam, new HashMap<String, Object>());
		List<Object> list = pageBean.getRecordList();
		// 更新

		for (Object object : list) {
			CalCostInterface calInterface = (CalCostInterface) object;
			calInterface.setInterfaceCode("new interface code");
			calCostInterfaceFacade.update(calInterface);
		}
	}
}
