/**
 * wusc.edu.pay.web.portal.action.PinyinAction.java
 */
package wusc.edu.pay.web.portal.action;

import java.util.HashMap;
import java.util.Map;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * <ul>
 * <li>Title: 拼音转换类</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-6-6
 */
public class PinyinAction extends Struts2ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8356839715439129074L;

	/**
	 * 获取汉字的简拼全拼
	 * 
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public void getPinyin() throws BadHanyuPinyinOutputFormatCombination {
		String value = getString("value");
		Map<String, String> result = new HashMap<String, String>();
		if (!StringUtil.isEmpty(value)) {
			result.put("simple", PinyinAction.getSpell(value, true));
			result.put("full", PinyinAction.getSpell(value, false));
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(result));
	}
	
	public static String getSpell(String target, boolean isSimpleSpell) {
		char[] chars = target.toCharArray();
		StringBuffer spell = new StringBuffer();
		String[] pinyinStr;
		int endIndex;
		for (char ch : chars) {
			// 如果不是汉字
			if (ch <= 128) {
				spell.append(ch);
				continue;
			}
			pinyinStr = PinyinHelper.toHanyuPinyinStringArray(ch);
			// 过滤中文符号
			if (pinyinStr == null) {
				spell.append(ch);
				continue;
			}
			endIndex = isSimpleSpell ? 1 : pinyinStr[0].length() - 1;
			spell.append(pinyinStr[0].substring(0, endIndex));
		}
		return spell.toString();
	}
}
