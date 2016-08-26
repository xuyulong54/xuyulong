package wusc.edu.pay.api.merchant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import wusc.edu.pay.api.merchant.utils.Context;


public class Recharge {

	/**
	 * 生成充值url,post方式
	 * 
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws HttpException
	 */
	public String buildPayUrlPost(Map<String, String> map) throws UnsupportedEncodingException {

		String p1_Amount = map.get("p1_Amount");
		String p2_LoginName = map.get("p2_LoginName");
		String p3_ReturnUrl = map.get("p3_ReturnUrl");
		String p4_OrderNo = map.get("p4_OrderNo");
		String hmac = map.get("hmac");

		String html = "";
		html += "<form name='toPay' action='" + Context.RECHARGE_URL + "' method='POST'>" + "\r";
		html += "<input type='hidden' name='p1_Amount' value='" + p1_Amount + "'>" + "\r";
		html += "<input type='hidden' name='p2_LoginName' value='" + URLEncoder.encode(p2_LoginName, "utf-8") + "'>" + "\r";
		html += "<input type='hidden' name='p3_ReturnUrl' value='" + URLEncoder.encode(p3_ReturnUrl, "utf-8") + "'>" + "\r";
		html += "<input type='hidden' name='p4_OrderNo' value='" + URLEncoder.encode(p4_OrderNo, "utf-8") + "'>" + "\r";
		html += "<input type='hidden' name='hmac' value='" + URLEncoder.encode(hmac, "utf-8") + "'>" + "\r";
		html += "</form>";

		return html;
	}

	/**
	 * 生成充值url,get方式
	 * 
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws HttpException
	 */
	public String buildPayUrlGet(Map<String, String> map) throws UnsupportedEncodingException {

		String p1_Amount = map.get("p1_Amount");
		String p2_LoginName = map.get("p2_LoginName");
		String p3_ReturnUrl = map.get("p3_ReturnUrl");
		String p4_OrderNo = map.get("p4_OrderNo");
		String hmac = map.get("hmac");

		String url = Context.RECHARGE_URL;
		url += "?p1_Amount=" + p1_Amount;
		url += "&p2_LoginName=" + URLEncoder.encode(p2_LoginName, "utf-8");
		url += "&p3_ReturnUrl=" + URLEncoder.encode(p3_ReturnUrl, "utf-8");
		url += "&p4_OrderNo=" + URLEncoder.encode(p4_OrderNo, "utf-8");
		url += "&hmac=" + URLEncoder.encode(hmac, "utf-8");

		return url;
	}
}
