package wusc.edu.pay.core.remit.biz.sub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.remit.dao.RemitRequestDao;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;


@Component("remitCheckBiz")
public class RemitCheckBiz {

	@Autowired
	private RemitRequestDao remitRequestDao;

	private static final Log log = LogFactory.getLog(RemitCheckBiz.class);

	/**
	 * 批量制单审核通过
	 * 
	 * @param ids
	 * @param checkLoginName
	 *            审核人登录名
	 */
	@Transactional(rollbackFor = Exception.class)
	public void checkSuccess(String[] ids, String checkLoginName) {

		log.info("==>checkSuccess");

		List<RemitRequest> list = new ArrayList<RemitRequest>();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];

			if (StringUtil.isBlank(id)) {
				continue;
			}

			RemitRequest remitRequest = remitRequestDao.getById(Long.parseLong(id));
			if (remitRequest == null) {
				continue;
			}

			list.add(remitRequest);
		}

		for (int i = 0; i < list.size(); i++) {
			this.checkSuccess(list.get(i), checkLoginName);
		}

		log.info("==>checkSuccess<==");
	}

	/**
	 * 审核通过
	 * 
	 * @param remitRequest
	 * @param checkLoginName
	 */
	@Transactional(rollbackFor = Exception.class)
	private void checkSuccess(RemitRequest remitRequest, String checkLoginName) {

		log.info("==>checkSuccess");

		remitRequest.setStatus(RemitRequestStatusEnum.AUDITED.getValue()); // 已审核
		remitRequest.setConfirmDate(new Date());
		remitRequest.setConfirm(checkLoginName);
		remitRequestDao.update(remitRequest);

		log.info("==>checkSuccess<==");

	}

	/**
	 * 批量制单审核不通过
	 * 
	 * @param ids
	 * 
	 * @param checkLoginName
	 *            审核人登录名
	 */
	public void checkFail(String[] ids, String checkLoginName) {

		log.info("==>batchCheckFail");

		List<RemitRequest> list = new ArrayList<RemitRequest>();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];

			if (StringUtil.isBlank(id)) {
				continue;
			}

			RemitRequest remitRequest = remitRequestDao.getById(Long.parseLong(id));
			if (remitRequest == null) {
				continue;
			}

			list.add(remitRequest);
		}

		for (int i = 0; i < list.size(); i++) {
			this.checkFail(list.get(i), checkLoginName);
		}

		log.info("==>checkFail<==");
	}

	/**
	 * 审核不通过
	 * 
	 * @param remitRequest
	 * @param checkLoginName
	 *            审核人登录名
	 */
	@Transactional(rollbackFor = Exception.class)
	private void checkFail(RemitRequest remitRequest, String checkLoginName) {

		log.info("==>checkFail");

		remitRequest.setStatus(RemitRequestStatusEnum.UNAPPROVE.getValue());
		remitRequest.setConfirmDate(new Date());
		remitRequest.setConfirm(checkLoginName);
		remitRequestDao.update(remitRequest);

		log.info("==>checkFail<==");

	}

}
