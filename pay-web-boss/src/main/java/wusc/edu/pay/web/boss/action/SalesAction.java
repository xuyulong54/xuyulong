package wusc.edu.pay.web.boss.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.facade.boss.entity.MerchantSales;
import wusc.edu.pay.facade.boss.entity.Sales;
import wusc.edu.pay.facade.boss.service.MerchantSalesFacade;
import wusc.edu.pay.facade.boss.service.SalesFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * #######该类暂时没用，系统管理中的销售人员管理用的是POS包下的SalesAction#######
 * #######jsp页面也是，现在用的都是/page/pos/sales 路径下的jsp页面	  #######
 * 
 * 
 * 类描述：销售人员管理Action
 * 
 * @author: huangbin
 * @date： 日期：2013-11-26 时间：上午9:33:19
 * @version 1.0
 */
public class SalesAction extends BossBaseAction {
	private static final long serialVersionUID = -3591247696183994099L;
	@Autowired
	private SalesFacade salesFacade;
	@Autowired
	private MerchantSalesFacade merchantSalesFacade;

	/***
	 * 销售人员列表 分页
	 * 
	 * @return
	 */
	public String listSales() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String salesName = getString("salesName"); // 销售人员名称
		String salesNo = getString("salesNo");// 销售人员编号
		paramMap.put("salesName", salesName);
		paramMap.put("salesNo", salesNo);
		super.pageBean = salesFacade.querySalesPage(getPageParam(), paramMap);
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);
		return "salesList";
	}

	/***
	 * 移交该销售的所有商户
	 * 
	 * @return
	 */
	public String moveMerchantUI() {
		Long salesId = getLong("id");
		this.putData("salesId", salesId);
		return "moveMerchant";
	}

	/***
	 * 移交该销售的所有商户
	 * 
	 * @return
	 */
	public String moveMerchant() {
		// 页面参数
		Long salesId = getLong("salesId");// 原销售ID
		Long targetSalesId = getLong("salesNameTag.backSalesId");// 新销售ID

		// 查询销售人员下的所有商户
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("salesId", salesId); // 销售人员ID
		
		List<MerchantSales> merchantSalesList = merchantSalesFacade.listByCondition(paramMap);
		StringBuffer sb = new StringBuffer();
		// 循环更新商户-销售人员表
		if (!merchantSalesList.isEmpty()) {
			for (MerchantSales merchantSales : merchantSalesList) {
				merchantSales.setSalesId(targetSalesId); // 销售人员管理
				sb.append(merchantSales.getMerchantNo()+"，");
				merchantSalesFacade.update(merchantSales);
			}
			this.logEdit("将销售员【"+salesId+"】的商户【"+sb+"】移交给销售员【"+targetSalesId+"】");
			return operateSuccess();
		} else {
			return operateError("该销售无发展的商户信息");
		}
	}

	/***
	 * 销售人员列表
	 * 
	 * @return
	 */
	public String listSalesBy() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String salesName = getString("salesName"); // 销售人员名称
		String salesNo = getString("salesNo");// 销售人员编号
		paramMap.put("salesName", salesName);
		paramMap.put("salesNo", salesNo);
		paramMap.put("salesId", getString("salesId"));
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue()); // 默认激活状态
		super.pageBean = salesFacade.querySalesPage(getPageParam(), paramMap);
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);
		return "salesListBy";
	}

	/***
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	public String toAddUI() {
		return "salesAdd";
	}

	/***
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	public String toEditUI() {
		Long salesId = getLong("id");
		Sales sales = salesFacade.getById(salesId);
		this.putData("model", sales);
		return "salesAdd";
	}

	/***
	 * 添加和修改操作
	 * 
	 * @return
	 */
	public String doAdd() {
		Long salesId = getLong("id");
		Sales sales = null;
		boolean isCreate = false;
		if (salesId == null) {
			isCreate = true;
			sales = new Sales();
		} else {
			sales = salesFacade.getById(salesId);
		}
		String salesName = getString("salesName");
		String mobileNo = getString("mobile");
		String status = getString("status");
		if (StringUtil.isEmpty(salesName)) {
			return operateError("销售人员姓名不能为空");
		}
		if (StringUtil.isEmpty(mobileNo)) {
			return operateError("联系手机不能为空");
		}
		if (StringUtil.isEmpty(status)) {
			return operateError("状态不能为空");
		}
		sales.setDescription(getString("description"));
		sales.setSalesName(salesName);
		sales.setStatus(Integer.valueOf(status));
		sales.setMobile(mobileNo);
		if (isCreate) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			sales.setSalesNo("S"+sdf1.format(new Date())); // S表示是销售人员  M表示销售经理
			salesFacade.create(sales);
			super.logSave("增加销售人员.销售人员名称["+sales.getSalesName()+"],销售人员编号["+sales.getSalesNo()+"]");
		} else if(Integer.valueOf(status)==PublicStatusEnum.INACTIVE.getValue()) {//以下是判断销售人员被冻结前是否有客户在其名下
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("salesId", salesId); // 销售人员ID
			
			List<MerchantSales> listMerchantSales = merchantSalesFacade.listByCondition(paramMap);
			if(listMerchantSales.size()<1 || listMerchantSales.isEmpty()){
				salesFacade.update(sales);
				super.logEditError("修改销售人员资料.销售人员名称["+sales.getSalesName()+"],销售人员编号["+sales.getSalesNo()+"]");
			}else{
				return this.operateError("请先将该销售人员的客户移交给其它销售人员");
			}
		}else{
			salesFacade.update(sales);
			super.logEditError("修改销售人员资料.销售人员名称["+sales.getSalesName()+"],销售人员编号["+sales.getSalesNo()+"]");
		}
		return operateSuccess();
	}
}
