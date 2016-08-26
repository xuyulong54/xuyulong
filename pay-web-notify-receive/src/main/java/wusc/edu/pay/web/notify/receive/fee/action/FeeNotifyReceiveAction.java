package wusc.edu.pay.web.notify.receive.fee.action;

import java.sql.SQLException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.fee.service.CalculateFeeFacade;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @描述: 商户计费通知接收.
 * @作者: WuShuicheng.
 * @创建: 2014-11-4,下午4:35:20
 * @版本: V1.0
 * 
 */
public class FeeNotifyReceiveAction extends Struts2ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8307213986406912756L;
	private static Log LOG = LogFactory.getLog(FeeNotifyReceiveAction.class);
	@Autowired
	private CalculateFeeFacade calculateFeeFacade;

	/**
	 * 接收通知并处理
	 * 
	 * @return
	 * @throws FeeBizException
	 * @throws SQLException
	 */
	public String notifyReceive() throws FeeBizException, SQLException {

		String msgBase64 = this.getString_UrlDecode_UTF8("msg");
		if (StringUtils.isBlank(msgBase64)) {
			throw new RuntimeException("通知请求参数[msg]为空");
		}

		String json = new String(Base64.decodeBase64(msgBase64));
		LOG.info("notifyReceive:" + json);

		FeeCalculateResultDTO feeCalculateResultDTO = JSONObject.parseObject(json, FeeCalculateResultDTO.class);
		if (feeCalculateResultDTO == null) {
			throw new RuntimeException("通知请求参数[msg]转换的对象为空");
		}

		LOG.info("==>param.getTradeCode():" + feeCalculateResultDTO.getFeeOrder().getTrxFlowNo());
		calculateFeeFacade.dealWithFeeOrder(feeCalculateResultDTO);

		// 回写成功字符串
		this.write(SUCCESS);
		return null;
	}

}
