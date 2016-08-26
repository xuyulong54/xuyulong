package wusc.edu.pay.fee.facade.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.entity.FeeFormula;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.fee.service.FeeCalculateWayFacade;
import wusc.edu.pay.facade.fee.service.FeeFormulaeFacade;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;


public class TsFeeQuery extends TestCase {

	ClassPathXmlApplicationContext context;

	FeeManagerFacade feeManagerFacade;
	FeeQueryFacade feeQueryFacade;
	FeeCalculateWayFacade feeCalculateWayFacade;
	FeeFormulaeFacade feeFormulaeFacade;

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		feeManagerFacade = (FeeManagerFacade) context.getBean("feeManagerFacade");
		feeQueryFacade = (FeeQueryFacade) context.getBean("feeQueryFacade");
		feeCalculateWayFacade = (FeeCalculateWayFacade) context.getBean("feeCalculateWayFacade");
		feeFormulaeFacade = (FeeFormulaeFacade) context.getBean("feeFormulaeFacade");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	public void testA() {
		//创建节点
		FeeNode node = new FeeNode();
		node.setCalculateFeeItem(1);
		node.setNodeName("xxx");
		node.setNodeType(1);
		node.setRemark("xxx");
		node.setStatus(100);
//		feeManagerFacade.createFeeNode(node);
		
		//分页查询节点
		Map<String , Object> map = new HashMap<String, Object>();
//		PageBean pageBean = feeQueryFacade.queryFeeNodeListPage(new PageParam(1, 15), map);
//		FeeNode updateNode = (FeeNode) pageBean.getRecordList().get(0);
		FeeNode node2 = feeQueryFacade.queryFeeNodeByNodeId(1l);
		System.out.println(node2.getNodeName());
	}
	
	public void testCreateFeeDimension(){
		//创建维度
		FeeDimension dimension = new FeeDimension();
		dimension.setBankChannelCode("xxx");
		dimension.setFeeNodeId(1L);
		dimension.setFrpCode("xxx");
		dimension.setPayProduct("xxx");
		dimension.setPayProductName("xxx");
		dimension.setStatus(100);
//		feeManagerFacade.createFeeDimension(dimension);
		//分页查询
		Map<String , Object> map = new HashMap<String, Object>();
//		PageBean pageBean = feeQueryFacade.queryFeeDimensionListPage(new PageParam(1, 15), map);
//		FeeDimension dimension1 = (FeeDimension) pageBean.getRecordList().get(0);
//		dimension1.setBankChannelCode("xxxx");
//		feeManagerFacade.updateFeeDimension(dimension1);
		
		FeeDimension dimension2 =feeQueryFacade.queryFeeDimensionById(1l);
		System.out.println(dimension2.getBankChannelCode());
	}
	
	public void testCreateUserFeeSetting (){
		//创建用户费率设置
		UserFeeSetting userFeeSetting = new UserFeeSetting();
		userFeeSetting.setCalculateFeeItem(1);
		userFeeSetting.setFeeNodeId(1L);
		userFeeSetting.setStatus(100);
		userFeeSetting.setUserName("xxx");
		userFeeSetting.setUserNo("888000000000000");
		userFeeSetting.setUserType(1);
//		feeManagerFacade.createUserFeeSetting(userFeeSetting);
		
		Map<String , Object> map = new HashMap<String, Object>();
		PageBean pageBean = feeQueryFacade.queryUserFeeSettingList(new PageParam(1, 15), map);
		UserFeeSetting userFeeSetting1 = (UserFeeSetting) pageBean.getRecordList().get(0);
		userFeeSetting1.setUserName("xxxx");
		feeManagerFacade.updateUserSetting(userFeeSetting1);
	}
	
	//新增计费方式
	public void testCreateFeeCalculateWay(){
		FeeCalculateWay way = new FeeCalculateWay();
		way.setBillCycleType(1);
		way.setCalculateType(1);
		way.setChargeType(1);
		way.setCustomizeBillCycleType(1);
		way.setCustomizeBillDay("12");
		way.setCustomizeCycleType(1);
		way.setCustomizeDay("1");
		way.setEffectiveDateEnd(DateUtils.addDay(new Date(), 10));
		way.setEffectiveDateStart(DateUtils.addDay(new Date(), -1));
		way.setFeeDimensionId(1l);
		way.setFeeFreeAmount(0D);
		way.setFeeRole(1);
		way.setIsDelete(false);
		way.setIsRefundFee(true);
		way.setLadderCycleType(1);
		way.setLadderName("xxx");
		way.setModifyTime(new Date());
		way.setProductName("xxx");
		
//		feeCalculateWayFacade.createFeeCalculateWay(way);
		
		Map<String , Object> map = new HashMap<String, Object>();
		PageBean pageBean = feeCalculateWayFacade.queryFeeCalculateWayList(new PageParam(1, 15), map);
		FeeCalculateWay way1 = (FeeCalculateWay) pageBean.getRecordList().get(0);
		way1.setCalculateType(2);
		feeCalculateWayFacade.updateFeeCalculateWay(way1);
		
		
	}
	
	//创建公式
	public void testCreateFeeFormulae(){
		FeeFormula formula = new FeeFormula();
		formula.setCalculateWayId(1l);
		formula.setFixedFee(0D);
		formula.setFormulaType(1);
		formula.setMaxAmount(10000D);
		formula.setMaxLadderAmount(1000D);
		formula.setMinAmount(1D);
		formula.setMinLadderAmount(0D);
		formula.setPercentFee(0.001);
		formula.setSingleMaxFee(1D);
		formula.setSingleMinFee(10D);
		formula.setStatus(100);
//		feeFormulaeFacade.createFeeFormulae(formula) ;
		
		Map<String , Object> map = new HashMap<String, Object>();
		PageBean pageBean = feeFormulaeFacade.listPage(new PageParam(1, 15), map);
		FeeFormula formula1 = (FeeFormula) pageBean.getRecordList().get(0);
		formula1.setMaxAmount(100000D);
		feeFormulaeFacade.updateFeeFormulae(formula1);
		
	}
	
	
}
