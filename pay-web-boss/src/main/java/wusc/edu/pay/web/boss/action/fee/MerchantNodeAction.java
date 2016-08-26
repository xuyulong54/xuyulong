package wusc.edu.pay.web.boss.action.fee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.enums.FeeModelTypeEnum;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 
 * Desc: 商户计费设置管理（挂节点）
 * @author lichao
 * Date: 2014-7-11
 *
 */
public class MerchantNodeAction extends BossBaseAction {

	private static final long serialVersionUID = -3996740878295848956L;
	@Autowired
	private FeeQueryFacade  feeQueryFacade ;
	@Autowired
	private FeeManagerFacade feeManagerFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;
	
	/**
	 * 商户节点
	 * @return
	 */
	public String merchantNodeList(){
		String userNo = getString("userNo");
		Integer status = getInteger("status");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("userSettingStatus", status);
		super.pageBean = feeQueryFacade.queryUserFeeSettingAndNodeListPage(getPageParam(), map);
		this.pushData(pageBean);
		this.putData("userNo", userNo);
		this.putData("status", status);
		this.putData("FeeModelTypeEnum",FeeModelTypeEnum.toList());
		this.putData("CalculateFeeItemEnum", CalculateFeeItemEnum.toList());
		return "merchantNodeList";
	}
	
	
	/**
	 * 为商户添加计费节点UI
	 * @return
	 */
	public String addMerchantNodeUI(){
		String userNo = getString("userNo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		List<Object> userFeeSettingList = feeQueryFacade.queryUserFeeSettingList(new PageParam(1, 15), map).getRecordList();
		List<Long> nodeIdList = new ArrayList<Long>();
		for(Object obj : userFeeSettingList){
			UserFeeSetting userFeeSetting = (UserFeeSetting)obj;
			nodeIdList.add(userFeeSetting.getFeeNodeId());
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("notInList", nodeIdList);
		paramMap.put("nodeName", getString("name"));
		paramMap.put("calculateFeeItem", getString("calculateFeeItem"));
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue());
		PageBean pagebean = feeQueryFacade.queryFeeNodeListPage(this.getPageParam(), paramMap);
		this.pushData(pagebean);
		this.pushData(paramMap);
		this.putData("userNo", userNo);
		this.putData("FeeModelTypeEnum",FeeModelTypeEnum.toList());
		this.putData("CalculateFeeItemEnum", CalculateFeeItemEnum.toList());
		return "addMerchantNode";
	}
	
	/**
	 * 为商户添加节点
	 * @return
	 */
	public String addMerchantNode(){
		String nodeIds = getString("nodeIds");
		if(nodeIds ==null){
			return this.operateError("请先选择节点！");
		}
		String[]  nodeIdArr = nodeIds.split("-");
		String userNo = getString("userNo");
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(userNo);
		for(int i=0; i<nodeIdArr.length; i++){
			UserFeeSetting userFeeSetting = new UserFeeSetting();
			userFeeSetting.setFeeNodeId(Long.parseLong( nodeIdArr[i].split("\\$")[0] ));
			userFeeSetting.setCalculateFeeItem(Integer.parseInt(nodeIdArr[i].split("\\$")[1]));
			userFeeSetting.setUserName((userInfo.getRealName()==null)?"":userInfo.getRealName());
			userFeeSetting.setUserNo(userNo);
			userFeeSetting.setUserType(UserTypeEnum.MERCHANT.getValue());
			userFeeSetting.setStatus(PublicStatusEnum.ACTIVE.getValue());
			feeManagerFacade.createUserFeeSetting(userFeeSetting);
			this.logSave("为商户["+userNo+"]添加节点，费率节点ID："+ userFeeSetting.getFeeNodeId());
		}
		return this.operateSuccess("添加成功！");
	}
	
	/**
	 * 设置节点是否有效
	 * @return
	 */
	public String isValid(){
		Long userSettingId = getLong("id");
		Integer status = getInteger("status");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userSettingId);
		List<Object> userFeeSettingList = feeQueryFacade.queryUserFeeSettingList(getPageParam(), map).getRecordList();
		for(Object obj : userFeeSettingList){
			UserFeeSetting userFeeSetting = (UserFeeSetting)obj;
			if(status==PublicStatusEnum.ACTIVE.getValue()){
				userFeeSetting.setStatus(PublicStatusEnum.INACTIVE.getValue());
				this.logEdit("设置节点无效，费率节点ID："+userFeeSetting.getFeeNodeId());
			}else{
				userFeeSetting.setStatus(PublicStatusEnum.ACTIVE.getValue());
				this.logEdit("设置节点有效，费率节点ID："+userFeeSetting.getFeeNodeId());
			}
			feeManagerFacade.updateUserSetting(userFeeSetting);
		}
		return this.operateSuccess("设置成功！");
	}
}
