package wusc.edu.pay.core.banklink.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * 
 * @author： Peter
 * @ClassName: DocumentUtil.java
 * @Date： 2014-11-13 下午1:58:27 
 * @version：  V1.0
 */
public class DocumentUtil  {
	
	private Document  document ;
	

	/**
	 * 根据节点名称全路径 获取值
	 * @param document
	 * @param nodePath
	 * @return
	 */
	public String getNodeTextByPath(String nodePath){
		Node node = document.selectSingleNode(nodePath);
		if(node != null){
			return node.getText();
		}
		return null;
	}

	/**
	 * 构造方法传入xml格式字符串生成Document对象
	 * @param xmlStr
	 * @throws DocumentException
	 */
	public DocumentUtil (String xmlStr) throws DocumentException{
		document = DocumentHelper.parseText(xmlStr);
	}
	
	/**
	 * 根据节点名称全路径 获取集合值
	 * @param nodePath
	 * @return
	 */
	public List getNodeListByPath(String nodePath){
		List nodeList = document.selectNodes(nodePath);
		return nodeList;
	}
	
	/**
	 * 根据节点名称全路径，将该节点下节点名称及节点值封装到一个Map中，并将Map放到List里面，适合多循环节点
	 * @param nodePath
	 * @return
	 */
	public List<Map<String,String>> getListMapByPath(String nodePath){
		
		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		
		List list = document.selectNodes(nodePath);
		
		for(int i = 0 ; i < list.size(); i++){
			Element ele = (Element)list.get(i);
			Map<String , String> map = new HashMap<String , String>();
			for (Iterator iter = ele.nodeIterator(); iter.hasNext();) {
				  Node node = (Node)iter.next();
				  map.put(node.getName(), node.getText());
				 }
			listMap.add(map);
		}
		return listMap;
	}
	
	public static void main(String[] args) {
		String str = "<?xml  version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?><ICBCAPI><pub><APIName>接口名称</APIName><APIVersion>接口版本号</APIVersion></pub><in><orderNum>订单号</orderNum><tranDate>交易日期</tranDate><ShopCode>商家号码</ShopCode><ShopAccount>商城账号</ShopAccount></in><out><tranSerialNum>指令序号1</tranSerialNum><tranStat>订单处理状态1</tranStat><bankRem>指令错误信息1</bankRem><amount>订单总金额1</amount><currType>支付币种1</currType><tranTime>返回通知日期时间1</tranTime><ShopAccount>商城账号1</ShopAccount><PayeeName>商城户名1</PayeeName><JoinFlag>校验联名标志1</JoinFlag><MerJoinFlag>商城联名标志1</MerJoinFlag><CustJoinFlag>客户联名标志1</CustJoinFlag><CustJoinNum>联名会员号1</CustJoinNum><CertID>商户签名证书id1</CertID></out><out><tranSerialNum>指令序号2</tranSerialNum><tranStat>订单处理状态2</tranStat><bankRem>指令错误信息2</bankRem><amount>订单总金额2</amount><currType>支付币种2</currType><tranTime>返回通知日期时间2</tranTime><ShopAccount>商城账号2</ShopAccount><PayeeName>商城户名2</PayeeName><JoinFlag>校验联名标志2</JoinFlag><MerJoinFlag>商城联名标志2</MerJoinFlag><CustJoinFlag>客户联名标志2</CustJoinFlag><CustJoinNum>联名会员号2</CustJoinNum><CertID>商户签名证书id2</CertID></out><out><tranSerialNum>指令序号3</tranSerialNum><tranStat>订单处理状态3</tranStat><bankRem>指令错误信息3</bankRem><amount>订单总金额3</amount><currType>支付币种3</currType><tranTime>返回通知日期时间3</tranTime><ShopAccount>商城账号3</ShopAccount><PayeeName>商城户名3</PayeeName><JoinFlag>校验联名标志3</JoinFlag><MerJoinFlag>商城联名标志3</MerJoinFlag><CustJoinFlag>客户联名标志3</CustJoinFlag><CustJoinNum>联名会员号3</CustJoinNum><CertID>商户签名证书id3</CertID></out><out><tranSerialNum>指令序号4</tranSerialNum><tranStat>订单处理状态4</tranStat><bankRem>指令错误信息4</bankRem><amount>订单总金额4</amount><currType>支付币种4</currType><tranTime>返回通知日期时间4</tranTime><ShopAccount>商城账号4</ShopAccount><PayeeName>商城户名4</PayeeName><JoinFlag>校验联名标志4</JoinFlag><MerJoinFlag>商城联名标志4</MerJoinFlag><CustJoinFlag>客户联名标志4</CustJoinFlag><CustJoinNum>联名会员号4</CustJoinNum><CertID>商户签名证书id4</CertID></out></ICBCAPI>";
		try {
			DocumentUtil doc = new DocumentUtil(str);
			
			List<Map<String,String>> listMap = doc.getListMapByPath("/ICBCAPI/out");
			
			System.out.println(listMap);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
