package wusc.edu.pay.web.boss.action.cal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.BeanUtils;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalDimension;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.BillingCycleEnum;
import wusc.edu.pay.facade.cost.enums.CalModelEnum;
import wusc.edu.pay.facade.cost.enums.CalPeriodeEnum;
import wusc.edu.pay.facade.cost.enums.CalRoleEnum;
import wusc.edu.pay.facade.cost.enums.CalTypeEnum;
import wusc.edu.pay.facade.cost.enums.CostItemEnum;
import wusc.edu.pay.facade.cost.enums.SystemResourceTypeEnum;
import wusc.edu.pay.facade.cost.service.CalCostOrderFacade;
import wusc.edu.pay.facade.cost.service.CalDimensionFacade;
import wusc.edu.pay.facade.cost.service.CalFeeRateFormulaFacade;
import wusc.edu.pay.facade.cost.service.CalFeeWayFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 计费成本订单
 */
public class CalCostOrderAction  extends BossBaseAction{

	private static final long serialVersionUID = -8349988507072446887L;
	private static final Log log = LogFactory.getLog(CalCostOrderAction.class);
	
	@Autowired
	private CalCostOrderFacade calCostOrderFacade;
	@Autowired
	private CalDimensionFacade calDimensionFacade;
	@Autowired
	private CalFeeWayFacade calFeeWayFacade;
	@Autowired
	private CalFeeRateFormulaFacade calFeeRateFormulaFacade;
	
	@Permission("boss:calCostOrder:view")
	public String costOrderList(){
		try{
			Map<String, Object> costOrderMap = new HashMap<String, Object>();
			costOrderMap.put("merchantNo", getString("merchantNo"));
			costOrderMap.put("merchantName", getString("merchantName"));
			costOrderMap.put("calOrderType", getInteger("calOrderType"));
			costOrderMap.put("fromSystem", getInteger("fromSystem"));
			costOrderMap.put("status", getInteger("status"));
			costOrderMap.put("costItem", getInteger("costItem"));
			costOrderMap.put("trxNo", getString("trxNo"));
			costOrderMap.put("calInterface", getString("calInterface"));
			if(getString("beginDate")!=null){
				costOrderMap.put("beginDate",getString("beginDate"));
			}
			if(getString("endDate")!=null){
				costOrderMap.put("endDate",getString("endDate"));
			}
			
			super.pageBean =calCostOrderFacade.listPage(this.getPageParam(), costOrderMap);
			this.putData("systemResourceTypeEnumList", SystemResourceTypeEnum.toList());
			this.putData("costItemEnumList", CostItemEnum.toList());
			this.putData("trxStatusEnumList", PublicStatusEnum.toCalStatusList());
			this.putData("calOrderTypeEnumList", CalPeriodeEnum.toList());
			this.pushData(pageBean);
			this.pushData(costOrderMap);
		}catch(Exception e){
			log.error("==costOrderList Exception",e);
		}
		return "costOrderList";
	}
	
	@Permission("boss:calCostOrder:view")
	public String costOrderInfo(){
		Integer id = getInteger("id");		
		CalCostOrder calCostOrder = calCostOrderFacade.getById(id);
		CalFeeWay calFeeWay =new CalFeeWay();
		List<CalFeeRateFormula> calFeeRateFormulaList =new ArrayList<CalFeeRateFormula>();
		CalDimension calDimension = new CalDimension();
		if(calCostOrder.getFeeWayId()!=null){
			calFeeWay = calFeeWayFacade.getById(calCostOrder.getFeeWayId());
		}
		if(calFeeWay.getDimensionId()!=null){
			calDimension = calDimensionFacade.getById(calFeeWay.getDimensionId());
		}
		if(calFeeWay.getId()!=null){
			calFeeRateFormulaList = calFeeRateFormulaFacade.getByFeeWayId(calFeeWay.getId());
		}
		this.putData("calCostOrder", calCostOrder);
		this.putData("calFeeWay", calFeeWay);
		this.putData("calFeeRateFormulaList", calFeeRateFormulaList);
		this.putData("calDimension", calDimension);
		
		this.putData("systemResourceTypeEnumList", SystemResourceTypeEnum.toList());
		this.putData("costItemEnumList", CostItemEnum.toList());
		this.putData("trxStatusEnumList", PublicStatusEnum.toCalStatusList());
		this.putData("calOrderTypeEnumList", CalPeriodeEnum.toList());
		this.putData("billingCycleEnumList", BillingCycleEnum.toList());
		this.putData("calTypeEnumList", CalTypeEnum.toList());
		this.putData("calPeriodeEnumList", CalPeriodeEnum.toList());
		this.putData("calRoleEnumList", CalRoleEnum.toList());
		this.putData("calModelEnumList", CalModelEnum.toList());
		return "costOrderInfo";
	}
	
	/**
	 * 导出成本计费订单报表
	 */
	public void calCostOrderExportExecl() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String filename = "成本计费订单_" + sdf.format(new Date());
			this.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename + ".xls", "utf-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[12];
			dataFields[0] = new DataField("序号", "no",5);
			dataFields[1] = new DataField("交易时间", "trxTime",25);
			dataFields[2] = new DataField("计费方式", "calOrderType", 15);
			dataFields[3] = new DataField("系统来源", "fromSystem", 15);
			dataFields[4] = new DataField("银行接口", "calInterface", 30);
			dataFields[5] = new DataField("成本计费项", "costItem", 20);
			dataFields[6] = new DataField("商户编号", "merchantNo",25);
			dataFields[7] = new DataField("商户名称", "merchantName",30);
			dataFields[8] = new DataField("交易流水号", "trxNo",20);
			dataFields[9] = new DataField("订单金额", "amount",15);
			dataFields[10] = new DataField("计费金额", "fee",15);
			dataFields[11] = new DataField("计费状态", "status",10);

			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			final String status = getString("status");
			final String merchantName = getString("merchantName");
			final String merchantNo = getString("merchantNo");
			
			final String costItem = getString("costItem");
			final String calOrderType = getString("calOrderType");
			final String trxNo = getString("trxNo");
			final String calInterface = getString("calInterface");
			final String fromSystem = getString("fromSystem");
			
			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				@Override
				public List getData() {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("beginDate", beginDate);
					map.put("endDate", endDate);
					map.put("status", status);
					map.put("merchantName", merchantName);
					map.put("merchantNo", merchantNo);
					map.put("costItem", costItem);
					map.put("calOrderType", calOrderType);
					map.put("trxNo", trxNo);
					map.put("calInterface", calInterface);
					map.put("fromSystem", fromSystem);
					
					PageParam pageParam = new PageParam(1, exportMaxNumber);
					PageBean pageBean = calCostOrderFacade.listPage(pageParam, map);

					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (!list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							CalCostOrder ap = (CalCostOrder) list.get(i);
							CalCostOrder calCostOrder = BeanUtils.toBean(CalCostOrder.class, ap);
							mapParam.put("no", ++temp);
							mapParam.put("trxTime", sdf1.format(calCostOrder.getTrxTime()));
							mapParam.put("calOrderType", calCostOrder.getCalOrderType()!=null?CalPeriodeEnum.getEnum(calCostOrder.getCalOrderType()).getDesc():"");
							mapParam.put("fromSystem", calCostOrder.getFromSystem()!=null?SystemResourceTypeEnum.getEnum(Integer.parseInt(calCostOrder.getFromSystem())).getDesc():"");
							mapParam.put("calInterface", calCostOrder.getCalInterface());
							mapParam.put("costItem", calCostOrder.getCostItem()!=null?CostItemEnum.getEnum(calCostOrder.getCostItem()).getDesc():"");
							mapParam.put("merchantNo", calCostOrder.getMerchantNo());
							mapParam.put("merchantName", calCostOrder.getMerchantName());
							mapParam.put("trxNo", calCostOrder.getTrxNo());
							mapParam.put("amount", calCostOrder.getAmount());
							mapParam.put("fee", calCostOrder.getFee());
							mapParam.put("status", calCostOrder.getStatus()==100?"计费成功":"计费失败");
							lists.add(mapParam);
						}
					}
					return lists;
				}
			}, os, MODE.EXCEL).export();
			super.logSave("导出成本计费订单数据");
		} catch (UnsupportedEncodingException e) {
			log.error("下载报表", e);
		} catch (IOException e) {
			log.error("IO", e);
		}
	}

}
