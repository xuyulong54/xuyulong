package wusc.edu.pay.core.fee.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.fee.dao.FeeNodeDao;
import wusc.edu.pay.facade.fee.entity.FeeNode;


/**
 * 计费节点biz定义
 * 
 */
@Component("feeNodeBiz")
public class FeeNodeBiz {

	@Autowired
	private FeeNodeDao feeNodeDao;

	/**
	 * list查询计费节点
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean ListPage(PageParam pageParam, Map<String, Object> map) {
		return feeNodeDao.listPage(pageParam, map);
	}

	/**
	 * 根据节点ID查询计费节点
	 * 
	 * @param feeNodeId
	 * @return
	 */
	public FeeNode getById(Long feeNodeId) {
		return feeNodeDao.getById(feeNodeId);
	}

	/**
	 * 创建节点
	 * @param node
	 */
	public long createFeeNode(FeeNode node) {
		return feeNodeDao.insert(node);
	}

	public boolean checkUnique(FeeNode node) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nodeName", node.getNodeName());
		param.put("calculateFeeItem", node.getCalculateFeeItem());
		List<FeeNode> feeNodeList = feeNodeDao.listBy(param);
		if(feeNodeList==null || feeNodeList.size()==0){
			return true;
		}else{
			return false;
		}
	}

}
