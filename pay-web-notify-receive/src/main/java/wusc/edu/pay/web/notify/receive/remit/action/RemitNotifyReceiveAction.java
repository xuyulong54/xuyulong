package wusc.edu.pay.web.notify.receive.remit.action;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.remit.entity.SettlRequestParam;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitFacade;
import wusc.edu.pay.facade.settlement.service.SettBusinessFacade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @描述: 打款通知接收.
 * @作者: WuShuicheng.
 * @创建: 2014-11-4,下午4:35:20
 * @版本: V1.0
 * 
 */
public class RemitNotifyReceiveAction extends Struts2ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8307213986406912756L;

	private static Log LOG = LogFactory.getLog(RemitNotifyReceiveAction.class);

	@Autowired
	private RemitFacade remitFacade;

	@Autowired
	private SettBusinessFacade settBusinessFacade;

	/**
	 * 打款创建
	 * 
	 * @return
	 * @throws RemitBizException
	 * @throws SQLException
	 */
	public String remitCreate() throws RemitBizException, SQLException {

		String msgBase64 = this.getString_UrlDecode_UTF8("msg");
		if (StringUtils.isBlank(msgBase64)) {
			throw new RuntimeException("通知请求参数[msg]为空");
		}

		String json = new String(Base64.decodeBase64(msgBase64));
		LOG.info("notifyReceive:" + json);

		List<SettlRequestParam> listSettlRequestParam = (List<SettlRequestParam>) JSONArray.parseArray(json, SettlRequestParam.class);

		remitFacade.create(listSettlRequestParam);

		// 回写成功字符串
		this.write(SUCCESS);
		return null;
	}

	/**
	 * 打款完成
	 * 
	 * @return
	 * @throws RemitBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String remitComplete() throws RemitBizException, SQLException {

		String msgBase64 = this.getString_UrlDecode_UTF8("msg");
		if (StringUtils.isBlank(msgBase64)) {
			throw new RuntimeException("通知请求参数[msg]为空");
		}

		String json = new String(Base64.decodeBase64(msgBase64));
		LOG.info("notifyReceive:" + json);

		Map<String, Object> map = (Map<String, Object>) JSONObject.parse(json);
		String remitRequestNo = map.get("remitRequestNo").toString();
		Date remitConfirmTime = DateUtils.getDateByStr(map.get("remitConfirmTime").toString());
		Integer remitStatus = Integer.parseInt(map.get("remitStatus").toString());
		String remitRemark = "";
		if (map.get("remitRemark") != null) {
			map.get("remitRemark").toString();
		}

		// 调用结算服务的，结算完成方法
		settBusinessFacade.remitCallBack(remitRequestNo, remitConfirmTime, remitStatus, remitRemark);

		// 回写成功字符串
		this.write(SUCCESS);
		return null;
	}

	public static void main(String[] args) {

		List<SettlRequestParam> listSettlRequestParam = new ArrayList<SettlRequestParam>();
		listSettlRequestParam.add(new SettlRequestParam());

		String msg = "W3siYWNjb3VudE5vIjoiODAwODAwNzEwMDAwMDc5MDAxMDEiLCJhbW91bnQiOjE5ODA0LjMxLCJiYW5rQWNjb3VudE5hbWUiOiLliJjnvo7ngbUiLCJiYW5rQWNjb3VudE5vIjoiNjIxMjI2MjAwNjAwMTg5MTMxMSIsImJhbmtBY2NvdW50VHlwZSI6MSwiYmFua0NoYW5uZWxObyI6IjEwMjU5ODUwMjMwNSIsImJhbmtOYW1lIjoi5Lit5Zu95bel5ZWG6ZO26KGM6IKh5Lu95pyJ6ZmQ5YWs5Y%2B45rKz5rqQ5Lic5rqQ5pSv6KGMIiwiY2l0eSI6Iuays%2Ba6kOW4giIsImlzVXJnZW50IjoyLCJwcm92aW5jZSI6IuW5v%2BS4nOecgSIsInJlcXVlc3RObyI6IjIwMTUwMzEzMTAwMDQ3MzYiLCJ0cmFkZVNvdXJjZSI6MiwidHJhZGVUeXBlIjoyLCJ1c2VyTm8iOiI4ODgxMDAwNTM5OTEzNTAifV0%3D";
		String urlMsg = URLDecoder.decode(msg);
		System.out.println(urlMsg);
		System.out.println(new String(Base64.decodeBase64(urlMsg.getBytes())));
		msg = new String(Base64.decodeBase64(urlMsg.getBytes()));

		List<SettlRequestParam> listSettlRequestParam1 = (List<SettlRequestParam>) JSONArray.parseArray(msg, SettlRequestParam.class);
	}

}
