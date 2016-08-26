package wusc.edu.pay.core.remit.biz.sub;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.remit.dao.RemitRequestDao;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


@Component("remitReCreateBiz")
public class RemitReCreateBiz {
	
	@Autowired
	private RemitRequestDao remitRequestDao;

	private static final Log log = LogFactory.getLog(RemitReCreateBiz.class);

	/**
	 * 重新制单
	 * 
	 * @param ids
	 * @param loginName
	 */
	public void reCreate(String[] ids, String loginName){
		
		log.info("==>reCreate");
		
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];

			if (StringUtil.isBlank(id)) {
				continue;
			}

			RemitRequest remitRequest = remitRequestDao.getById(Long.parseLong(id));
			
			if (remitRequest == null) {
				throw RemitBizException.REMIT_REQUEST_RECORD_NOT_EXIST.print();
			}
			
			remitRequest.setStatus(RemitRequestStatusEnum.UNAUDIT.getValue());
			remitRequest.setBatchNo("");
			remitRequest.setConfirm("");
			remitRequest.setConfirmDate(null);
			remitRequest.setCopied(PublicStatusEnum.ACTIVE.getValue());
			remitRequest.setCreator(loginName);
			remitRequest.setCreateDate(new Date());
			
			remitRequestDao.update(remitRequest);
			
			log.info("==>reCreate<==");
		}
	}
}
