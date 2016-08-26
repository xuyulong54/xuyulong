package wusc.edu.pay.web.boss.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @描述:.度行银联号映射
 * @作者: LiLiQiong.
 * @创建时间: 2015-1-27 下午5:44:02
 * @版本: V1.0.
 * 
 */
public class DuTongBankChannelNo {

	public static Map<String, String[]> bankChannelNoMap = new HashMap<String, String[]>();
	static {
		bankChannelNoMap.put("中国工商银行", new String[] { "中国工商银行", "102100099996" });
		bankChannelNoMap.put("中国农业银行", new String[] { "中国农业银行", "103100000026" });
		bankChannelNoMap.put("中国银行", new String[] { "中国银行", "104100000004" });
		bankChannelNoMap.put("中国建设银行", new String[] { "中国建设银行", "105100000017" });
		bankChannelNoMap.put("交通银行", new String[] { "交通银行", "301290000007" });
		bankChannelNoMap.put("中信银行", new String[] { "中信银行", "302100011000" });
		bankChannelNoMap.put("中国光大银行", new String[] { "中国光大银行", "303100000006" });
		bankChannelNoMap.put("华夏银行", new String[] { "华夏银行", "304100040000" });
		bankChannelNoMap.put("中国民生银行", new String[] { "中国民生银行", "305100000013" });
		bankChannelNoMap.put("广发银行股份有限公司", new String[] { "广发银行股份有限公司", "306581000003" });
		bankChannelNoMap.put("平安银行（原深圳发展银行）", new String[] { "平安银行（原深圳发展银行）", "307584007998" });
		bankChannelNoMap.put("招商银行", new String[] { "招商银行", "308584000013" });
		bankChannelNoMap.put("兴业银行", new String[] { "兴业银行", "309391000011" });
		bankChannelNoMap.put("上海浦东发展银行", new String[] { "上海浦东发展银行", "310290000013" });
		bankChannelNoMap.put("北京银行", new String[] { "北京银行", "313100000013" });
		bankChannelNoMap.put("天津银行", new String[] { "天津银行", "313110000017" });
		bankChannelNoMap.put("河北银行股份有限公司", new String[] { "河北银行股份有限公司", "313121006888" });
		bankChannelNoMap.put("邯郸市商业银行", new String[] { "邯郸市商业银行", "313127000013" });
		bankChannelNoMap.put("邢台银行", new String[] { "邢台银行", "313131000016" });
		bankChannelNoMap.put("张家口市商业银行", new String[] { "张家口市商业银行", "313138000019" });
		bankChannelNoMap.put("承德银行", new String[] { "承德银行", "313141052422" });
		bankChannelNoMap.put("沧州银行", new String[] { "沧州银行", "313143005157" });
		bankChannelNoMap.put("廊坊银行", new String[] { "廊坊银行", "313146000019" });
		bankChannelNoMap.put("晋商银行网上银行", new String[] { "晋商银行网上银行", "313161000017" });
		bankChannelNoMap.put("晋城市商业银行", new String[] { "晋城市商业银行", "313168000003" });
		bankChannelNoMap.put("内蒙古银行", new String[] { "内蒙古银行", "313191000011" });
		bankChannelNoMap.put("包商银行股份有限公司", new String[] { "包商银行股份有限公司", "313192000013" });
		bankChannelNoMap.put("鄂尔多斯银行", new String[] { "鄂尔多斯银行", "313205057830" });
		bankChannelNoMap.put("大连银行", new String[] { "大连银行", "313222080002" });
		bankChannelNoMap.put("鞍山市商业银行", new String[] { "鞍山市商业银行", "313223007007" });
		bankChannelNoMap.put("锦州银行", new String[] { "锦州银行", "313227000012" });
		bankChannelNoMap.put("葫芦岛银行", new String[] { "葫芦岛银行", "313227600018" });
		bankChannelNoMap.put("营口银行", new String[] { "营口银行", "313228000276" });
		bankChannelNoMap.put("阜新银行结算中心", new String[] { "阜新银行结算中心", "313229000008" });
		bankChannelNoMap.put("吉林银行", new String[] { "吉林银行", "313241066661" });
		bankChannelNoMap.put("哈尔滨银行结算中心", new String[] { "哈尔滨银行结算中心", "313261000018" });
		bankChannelNoMap.put("龙江银行", new String[] { "龙江银行", "313261099913" });
		bankChannelNoMap.put("上海银行", new String[] { "上海银行", "313290000017" });
		bankChannelNoMap.put("南京银行", new String[] { "南京银行", "313301008887" });
		bankChannelNoMap.put("江苏银行股份有限公司", new String[] { "江苏银行股份有限公司", "313301099999" });
		bankChannelNoMap.put("苏州银行", new String[] { "苏州银行", "313305066661" });
		bankChannelNoMap.put("杭州银行", new String[] { "杭州银行", "313331000014" });
		bankChannelNoMap.put("宁波银行", new String[] { "宁波银行", "313332082914" });
		bankChannelNoMap.put("温州银行", new String[] { "温州银行", "313333007331" });
		bankChannelNoMap.put("嘉兴银行清算中心", new String[] { "嘉兴银行清算中心", "313335081005" });
		bankChannelNoMap.put("湖州银行", new String[] { "湖州银行", "313336071575" });
		bankChannelNoMap.put("绍兴银行", new String[] { "绍兴银行", "313337009004" });
		bankChannelNoMap.put("浙江稠州商业银行", new String[] { "浙江稠州商业银行", "313338707013" });
		bankChannelNoMap.put("台州银行", new String[] { "台州银行", "313345001665" });
		bankChannelNoMap.put("浙江泰隆商业银行", new String[] { "浙江泰隆商业银行", "313345010019" });
		bankChannelNoMap.put("浙江民泰商业银行", new String[] { "浙江民泰商业银行", "313345400010" });
		bankChannelNoMap.put("福建海峡银行", new String[] { "福建海峡银行", "313391080007" });
		bankChannelNoMap.put("厦门银行", new String[] { "厦门银行", "313393080005" });
		bankChannelNoMap.put("南昌银行", new String[] { "南昌银行", "313421087506" });
		bankChannelNoMap.put("赣州银行", new String[] { "赣州银行", "313428076517" });
		bankChannelNoMap.put("上饶银行", new String[] { "上饶银行", "313433076801" });
		bankChannelNoMap.put("齐鲁银行", new String[] { "齐鲁银行", "313451000019" });
		bankChannelNoMap.put("青岛银行", new String[] { "青岛银行", "313452060150" });
		bankChannelNoMap.put("齐商银行", new String[] { "齐商银行", "313453001017" });
		bankChannelNoMap.put("东营市商业银行", new String[] { "东营市商业银行", "313455000018" });
		bankChannelNoMap.put("烟台银行", new String[] { "烟台银行", "313456000108" });
		bankChannelNoMap.put("潍坊银行", new String[] { "潍坊银行", "313458000013" });
		bankChannelNoMap.put("济宁银行", new String[] { "济宁银行", "313461000012" });
		bankChannelNoMap.put("泰安市商业银行", new String[] { "泰安市商业银行", "313463000993" });
		bankChannelNoMap.put("莱商银行", new String[] { "莱商银行", "313463400019" });
		bankChannelNoMap.put("威海市商业银行", new String[] { "威海市商业银行", "313465000010" });
		bankChannelNoMap.put("德州银行", new String[] { "德州银行", "313468000015" });
		bankChannelNoMap.put("临商银行", new String[] { "临商银行", "313473070018" });
		bankChannelNoMap.put("日照银行", new String[] { "日照银行", "313473200011" });
		bankChannelNoMap.put("郑州银行", new String[] { "郑州银行", "313491000232" });
		bankChannelNoMap.put("开封市商业银行", new String[] { "开封市商业银行", "313492070005" });
		bankChannelNoMap.put("洛阳银行", new String[] { "洛阳银行", "313493080539" });
		bankChannelNoMap.put("漯河市商业银行", new String[] { "漯河市商业银行", "313504000010" });
		bankChannelNoMap.put("商丘市商业银行", new String[] { "商丘市商业银行", "313506082510" });
		bankChannelNoMap.put("南阳银行股份有限公司", new String[] { "南阳银行股份有限公司", "313513080408" });
		bankChannelNoMap.put("汉口银行", new String[] { "汉口银行", "313521000011" });
		bankChannelNoMap.put("长沙银行", new String[] { "长沙银行", "313551088886" });
		bankChannelNoMap.put("广州银行", new String[] { "广州银行", "313581003284" });
		bankChannelNoMap.put("珠海华润银行清算中心", new String[] { "珠海华润银行清算中心", "313585000990" });
		bankChannelNoMap.put("广东华兴银行", new String[] { "广东华兴银行", "313586000006" });
		bankChannelNoMap.put("广东南粤银行股份有限公司", new String[] { "广东南粤银行股份有限公司", "313591001001" });
		bankChannelNoMap.put("东莞银行", new String[] { "东莞银行", "313602088017" });
		bankChannelNoMap.put("广西北部湾银行", new String[] { "广西北部湾银行", "313611001018" });
		bankChannelNoMap.put("柳州银行", new String[] { "柳州银行", "313614000012" });
		bankChannelNoMap.put("桂林银行股份有限公司", new String[] { "桂林银行股份有限公司", "313617000018" });
		bankChannelNoMap.put("重庆银行股份有限公司", new String[] { "重庆银行股份有限公司", "313653000013" });
		bankChannelNoMap.put("自贡市商业银行清算中心", new String[] { "自贡市商业银行清算中心", "313655091983" });
		bankChannelNoMap.put("攀枝花市商业银行", new String[] { "攀枝花市商业银行", "313656000019" });
		bankChannelNoMap.put("德阳银行", new String[] { "德阳银行", "313658000014" });
		bankChannelNoMap.put("绵阳市商业银行", new String[] { "绵阳市商业银行", "313659000016" });
		bankChannelNoMap.put("贵阳银行", new String[] { "贵阳银行", "313701098010" });
		bankChannelNoMap.put("富滇银行", new String[] { "富滇银行", "313731010015" });
		bankChannelNoMap.put("长安银行", new String[] { "长安银行", "313791030003" });
		bankChannelNoMap.put("兰州银行股份有限公司", new String[] { "兰州银行股份有限公司", "313821001016" });
		bankChannelNoMap.put("青海银行", new String[] { "青海银行", "313851000018" });
		bankChannelNoMap.put("宁夏银行", new String[] { "宁夏银行", "313871000007" });
		bankChannelNoMap.put("乌鲁木齐市商业银行", new String[] { "乌鲁木齐市商业银行", "313881000002" });
		bankChannelNoMap.put("昆仑银行", new String[] { "昆仑银行", "313882000012" });
		bankChannelNoMap.put("昆山农村商业银行", new String[] { "昆山农村商业银行", "314305206650" });
		bankChannelNoMap.put("吴江农村商业银行", new String[] { "吴江农村商业银行", "314305400015" });
		bankChannelNoMap.put("常熟农村商业银行", new String[] { "常熟农村商业银行", "314305506621" });
		bankChannelNoMap.put("张家港农村商业银行", new String[] { "张家港农村商业银行", "314305670002" });
		bankChannelNoMap.put("广州农村商业银行", new String[] { "广州农村商业银行", "314581000011" });
		bankChannelNoMap.put("顺德农村商业银行", new String[] { "顺德农村商业银行", "314588000016" });
		bankChannelNoMap.put("重庆农村商业银行", new String[] { "重庆农村商业银行", "314653000011" });
		bankChannelNoMap.put("恒丰银行", new String[] { "恒丰银行", "315456000105" });
		bankChannelNoMap.put("浙商银行", new String[] { "浙商银行", "316331000018" });
		bankChannelNoMap.put("天津农商银行", new String[] { "天津农商银行", "317110010019" });
		bankChannelNoMap.put("渤海银行", new String[] { "渤海银行", "318110000014" });
		bankChannelNoMap.put("徽商银行", new String[] { "徽商银行", "319361000013" });
		bankChannelNoMap.put("北京顺义银座村镇银行", new String[] { "北京顺义银座村镇银行", "320100010011" });
		bankChannelNoMap.put("浙江三门银座村镇银行", new String[] { "浙江三门银座村镇银行", "320345790018" });
		bankChannelNoMap.put("江西赣州银座村镇银行", new String[] { "江西赣州银座村镇银行", "320428090311" });
		bankChannelNoMap.put("深圳福田银座村镇银行", new String[] { "深圳福田银座村镇银行", "320584002002" });
		bankChannelNoMap.put("重庆渝北银座村镇银行", new String[] { "重庆渝北银座村镇银行", "320653000104" });
		bankChannelNoMap.put("重庆黔江银座村镇银行", new String[] { "重庆黔江银座村镇银行", "320687000016" });
		bankChannelNoMap.put("上海农商银行", new String[] { "上海农商银行", "322290000011" });
		bankChannelNoMap.put("北京农村商业银行", new String[] { "北京农村商业银行", "402100000018" });
		bankChannelNoMap.put("吉林农村信用社", new String[] { "吉林农村信用社", "402241000015" });
		bankChannelNoMap.put("江苏省农村信用社联合社", new String[] { "江苏省农村信用社联合社", "402301099998" });
		bankChannelNoMap.put("浙江省农村信用社", new String[] { "浙江省农村信用社", "402331000007" });
		bankChannelNoMap.put("鄞州银行", new String[] { "鄞州银行", "402332010004" });
		bankChannelNoMap.put("安徽省农村信用社联合社", new String[] { "安徽省农村信用社联合社", "402361018886" });
		bankChannelNoMap.put("福建省农村信用社", new String[] { "福建省农村信用社", "402391000068" });
		bankChannelNoMap.put("山东省农联社", new String[] { "山东省农联社", "402451000010" });
		bankChannelNoMap.put("湖北农信", new String[] { "湖北农信", "402521000032" });
		bankChannelNoMap.put("深圳农商行", new String[] { "深圳农商行", "402584009991" });
		bankChannelNoMap.put("东莞农村商业银行", new String[] { "东莞农村商业银行", "402602000018" });
		bankChannelNoMap.put("广西农村信用社（合作银行）", new String[] { "广西农村信用社（合作银行）", "402611099974" });
		bankChannelNoMap.put("海南省农村信用社", new String[] { "海南省农村信用社", "402641000014" });
		bankChannelNoMap.put("云南省农村信用社", new String[] { "云南省农村信用社", "402731057238" });
		bankChannelNoMap.put("黄河农村商业银行", new String[] { "黄河农村商业银行", "402871099996" });
		bankChannelNoMap.put("中国邮政储蓄银行", new String[] { "中国邮政储蓄银行", "403100000004" });
		bankChannelNoMap.put("外换银行（中国）有限公司", new String[] { "外换银行（中国）有限公司", "591110000016" });
		bankChannelNoMap.put("友利银行", new String[] { "友利银行", "593100000020" });
		bankChannelNoMap.put("新韩银行中国", new String[] { "新韩银行中国", "595100000007" });
		bankChannelNoMap.put("企业银行", new String[] { "企业银行", "596110000013" });
		bankChannelNoMap.put("韩亚银行", new String[] { "韩亚银行", "597100000014" });
	}

	public static String[] getDuTongBankChannelNo(String bankName) {
		String[] duTongBankChannelNoArr = null;

		if (bankName == null) {
			return duTongBankChannelNoArr;
		}
		Set<String> keys = bankChannelNoMap.keySet();
		for (@SuppressWarnings("rawtypes")
		Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (bankName.startsWith(key)) {
				duTongBankChannelNoArr = bankChannelNoMap.get(key);
				break;
			}
		}
		return duTongBankChannelNoArr;
	}

}
