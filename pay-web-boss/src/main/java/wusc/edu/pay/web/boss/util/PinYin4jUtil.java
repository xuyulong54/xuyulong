package wusc.edu.pay.web.boss.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 该工具类用来根据中文字符生成拼音
 * @author Administrator
 *
 */
public class PinYin4jUtil {

	/**
	 * 获取省市区大写缩写
	 * 例如：中国 ----》 ZG
	 * @throws Exception 
	 */
	public static String getFirstPinyin(String ZH) throws Exception{
		StringBuffer returnStr = new StringBuffer("");
		String tempStr = "";
		//现将特殊地区代码判断出来返回，通用的在后面返回
		if(ZH.endsWith("内蒙古自治区")){
			return "NM";
		}else if(ZH.endsWith("重庆市")){
			return "CQ";
		}else if(ZH.endsWith("海南省")){
			return "HI";
		}else if(ZH.endsWith("河北省")){
			return "HE";
		}else if(ZH.endsWith("黑龙江省")){
			return "HL";
		}else if(ZH.endsWith("河南省")){
			return "HA";
		}else if(ZH.endsWith("陕西省")){
			return "SN";
		}else if(ZH.endsWith("香港特别行政区")){
			return "HK";
		}else if(ZH.endsWith("澳门特别行政区")){
			return "MO";
		}else if(ZH.endsWith("自治州")){
			tempStr = ZH.substring(0, ZH.length()-3);
		}else if(ZH.endsWith("地区")){
			tempStr = ZH.substring(0, ZH.length()-2);
		}else if(ZH.endsWith("市")){
			tempStr = ZH.substring(0, ZH.length()-1);
		}else if(ZH.endsWith("壮族自治区")){
			tempStr = ZH.substring(0, ZH.length()-5);
		}else if(ZH.endsWith("回族自治区")){
			tempStr = ZH.substring(0, ZH.length()-5);
		}else if(ZH.endsWith("维吾尔自治区")){
			tempStr = ZH.substring(0, ZH.length()-6);
		}else if(ZH.endsWith("自治区")){
			tempStr = ZH.substring(0, ZH.length()-3);
		}else if(ZH.endsWith("省")){
			tempStr = ZH.substring(0, ZH.length()-1);
		}else{
			tempStr = ZH;
		}
		String[] tempStrArr = tempStr.split("");
		int tempStrLength = tempStrArr.length;
		for(int i = 1 ; i < tempStrLength ; i++){
			returnStr.append(getPinyin(tempStrArr[i], null).substring(0, 1));
		}
		return returnStr.toString();
	}

	/**
	 * 设置一个常用的输出格式
	 * 
	 * @return
	 */

	public static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
		format.setVCharType(HanyuPinyinVCharType.WITH_V);// u显示
		return format;
	}

	/**
	 * 
	 * 获得拼音
	 * 
	 * @param zhongwen
	 * 
	 * @param format
	 *            为空时，使用默认格式
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public static String getPinyin(String zhongwen,
			HanyuPinyinOutputFormat format) throws Exception {
		if (format == null) {
			format = getDefaultOutputFormat();
		}
		StringBuffer pinyin = new StringBuffer("");
		char[] chars = zhongwen.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i],
					format);
			// 当转换不是中文字符时,返回null
			if (pinYin != null) {
				pinyin.append(pinYin[0]);
			} else {
				pinyin.append(chars[i]);
			}
		}
		return pinyin.toString();
	}

}
