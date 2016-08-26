package wusc.edu.pay.core.fee.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.fee.dao.FeeNodeDao;
import wusc.edu.pay.core.fee.dao.UserFeeSettingDao;
import wusc.edu.pay.facade.fee.dto.FeeModelDTO;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;


@Component("userFeeSettingBiz")
public class UserFeeSettingBiz {

	@Autowired
	private UserFeeSettingDao userFeeSettingDao;
	@Autowired
	private FeeNodeDao feeNodeDao;

	/**
	 * 查询用户费率设置
	 * 
	 * @param userNo
	 *            费率调用者编号
	 * @param userType
	 *            用户类型
	 * @param calFeeItem
	 *            计费项 CalculateFeeItemEnum
	 * @param hourStart
	 *            用户费率设置开始时间
	 * @return
	 * @throws Exception
	 */
	public List<UserFeeSetting> getUserFeeSetting(String userNo, Integer userType, Integer calFeeItem, Date hourStart){
		// 查询出所有的商户费率设置(可以做缓存处理)
		List<UserFeeSetting> listUserFeeSettings = userFeeSettingDao.queryAllSettings(hourStart);
		
		// 过滤出属于该用户的用户费率设置
		listUserFeeSettings = filterUserFeeSetting(listUserFeeSettings, userNo, userType);
		if (!listUserFeeSettings.isEmpty()) {
			Iterator<UserFeeSetting> iter = listUserFeeSettings.iterator();
			while(iter.hasNext()){
				UserFeeSetting customer = iter.next();
				
				if(!(customer.getCalculateFeeItem() == calFeeItem)){
					iter.remove();
				}
			}
			
		}
		if (listUserFeeSettings !=null && listUserFeeSettings.isEmpty()) {
			throw new FeeBizException(FeeBizException.FEE_USERSETTING_NOT_EXIST,"没有相应的用户费率设置");
		} 
		return listUserFeeSettings;

	}

	/**
	 * 过滤属于该用户的费率设置
	 * 
	 * @param listUserFeeSettings
	 *            用户费率设置
	 * @param userNo
	 *            费率调用者编号
	 * @param userType
	 *            用户类型
	 * @return
	 */
	private List<UserFeeSetting> filterUserFeeSetting(List<UserFeeSetting> listUserFeeSettings, String userNo, Integer userType) {
		List<UserFeeSetting> listTemp = new ArrayList<UserFeeSetting>();
		for (UserFeeSetting userFeeSetting : listUserFeeSettings) {
			if (userNo.equals(userFeeSetting.getUserNo())) {
				listTemp.add(userFeeSetting);
			}
		}
		return listTemp;
	}

	/**
	 *  根据userNo、userType、收费项去查找客户费率设置
	 * @param userNo
	 * @param userType
	 * @param calFeeItem
	 * @return
	 */
	public List<UserFeeSetting> getUserFeeSettingList(String userNo, Integer userType, Integer calFeeItem) {
		return userFeeSettingDao.queryFeeUserByConsumerNo(userNo, userType ,calFeeItem);
	}

	/**
	 * 查询用户节点设置列表
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean queryUserFeeSettingList(PageParam pageParam,
			Map<String, Object> map) {
		return userFeeSettingDao.listPage(pageParam, map);
	}

	/**
	 * 创建实体
	 * @param userFeeSetting
	 */
	public void createUserFeeSetting(UserFeeSetting userFeeSetting) {
		userFeeSettingDao.insert(userFeeSetting);
	}

	/**
	 * 更新
	 * @param userFeeSetting
	 */
	public void updateUserSetting(UserFeeSetting userFeeSetting) {
		userFeeSettingDao.update(userFeeSetting);
	}

	/**
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public List<FeeModelDTO> queryUserFeeSettingAndNode(PageParam pageParam,
			Map<String, Object> map) {
		List<Object> userFeeSettingList = userFeeSettingDao.listPage(pageParam, map).getRecordList();
		List<FeeModelDTO> listFeeModelDTO = new ArrayList<FeeModelDTO>();
		for(Object obj : userFeeSettingList){
			UserFeeSetting userFeeSetting = (UserFeeSetting)obj;
			FeeNode feeNode = feeNodeDao.getById(userFeeSetting.getFeeNodeId());
			FeeModelDTO feeModelDTO = new FeeModelDTO();
			feeModelDTO.setUserSettingId(userFeeSetting.getId());
			feeModelDTO.setStatus(userFeeSetting.getStatus());
			feeModelDTO.setNodeId(userFeeSetting.getFeeNodeId());
			feeModelDTO.setCalculateFeeItem(userFeeSetting.getCalculateFeeItem());
			feeModelDTO.setUserName(userFeeSetting.getUserName());
			feeModelDTO.setUserNo(userFeeSetting.getUserNo());
			feeModelDTO.setUserType(userFeeSetting.getUserType());
			feeModelDTO.setNodeName(feeNode.getNodeName());
			feeModelDTO.setNodeType(feeNode.getNodeType());
			feeModelDTO.setDescription(feeNode.getRemark());
			listFeeModelDTO.add(feeModelDTO);
		}
		return listFeeModelDTO;
	}

	public PageBean queryUserFeeSettingAndNodeListPage(PageParam pageParam,
			Map<String, Object> map) {
		return userFeeSettingDao.queryUserFeeSettingAndNodeListPage(pageParam,map);
	}

	
	
}
