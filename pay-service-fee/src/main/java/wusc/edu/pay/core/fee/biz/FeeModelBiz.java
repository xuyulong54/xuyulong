package wusc.edu.pay.core.fee.biz;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.fee.dao.FeeNodeDao;
import wusc.edu.pay.core.fee.dao.UserFeeSettingDao;
import wusc.edu.pay.facade.fee.dto.FeeModelDTO;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;


/**
 * 费率模型Biz定义
 * 
 */
@Component("feeModelBiz")
public class FeeModelBiz {
	private static final Log log = LogFactory.getLog(FeeModelBiz.class);
	@Autowired
	private UserFeeSettingDao userFeeSettingDao;
	@Autowired
	private FeeNodeDao feeNodeDao;
	
	public FeeModelDTO getFeeConsumerInfo(String userNo, Integer calFeeItem, Integer userType){
		//获取当前费率调用方在当前所属系统下在费率系统中的所有客户费率设置
		List<UserFeeSetting> customers = userFeeSettingDao.queryFeeUserByConsumerNo(userNo, userType ,calFeeItem);
		
		if(null != customers && !customers.isEmpty()){
			Iterator<UserFeeSetting> iter = customers.iterator();
			while(iter.hasNext()){
				UserFeeSetting customer = iter.next();
				//得到节点
				FeeNode node = feeNodeDao.getById(customer.getFeeNodeId());
				
				if(null != node){
					FeeModelDTO model = createFeeModelDTO(node, customer);
					return model;
				}
				iter.remove();
			}
			if(customers.isEmpty()){
				return null;
			}
		}
		else{
			return null;
		}
		return null;
	}
	
	
	/**
	 * 创建费率模型Dto 
	 */
	private FeeModelDTO createFeeModelDTO(FeeNode node, UserFeeSetting customer){
		FeeModelDTO model = new FeeModelDTO();
		model.setUserNo(customer.getUserNo());
		model.setUserName(customer.getUserName());
		model.setUserType(customer.getUserType());
		model.setCalculateFeeItem(node.getCalculateFeeItem());
		model.setDescription(node.getRemark());
		model.setNodeId(node.getId());
		model.setNodeName(node.getNodeName());
		model.setNodeType(node.getNodeType());
		return model;
	}
}
