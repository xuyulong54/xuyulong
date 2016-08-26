package wusc.edu.pay.common.utils.token;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.lang3.StringUtils;

import wusc.edu.pay.common.exceptions.BizException;


/**
 * ClassName: TokenToolEncrypter <br/>
 * Function: 一个Token加密与解密工具 ，不限于Token范围  只能解本JDK加密串<br/>
 * date: 2014-8-5 下午8:49:52 <br/>
 * <per>
 * 使用时，结合 TokenProductFactory 生成 平台Token
 * 通过解密，识别Token来源。
 * </per>
 * @author laich
 */
public class TokenToolEncrypter  implements TokenBaseInter{

	/** BASE64 加密处理工具 */
	private static Cipher ecipher;
	/** BASE64 解密处理工具 */
	private static Cipher dcipher;
    
	/**初始化值**/
	public TokenToolEncrypter(){
		init();
	}
	public static void init() {
		try {
			SecretKey key = KeyGenerator.getInstance("DES").generateKey();
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("restriction")
	public  String encrypt(String str) {
		try {
			if(ecipher==null){
				init();
			}if(StringUtils.isEmpty(str)){
				throw  BizException.TOKEN_IS_ILLICIT;
			}
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return new sun.misc.BASE64Encoder().encode(enc).replace("+", "_");
		} catch (javax.crypto.BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("restriction")
	public   String decrypt(String str) {
		try {
			if(ecipher==null){
				init();
			}if(StringUtils.isEmpty(str)){
				throw  BizException.TOKEN_IS_ILLICIT;
			}
			str = str.replace("_", "+");
			// Decode base64 to get bytes
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see wusc.edu.pay.common.utils.token.TokenBaseInter#productToken(java.lang.String[])
	 */
	@Override
	public String productToken(String[] pramaters) {
		if(pramaters==null || pramaters.length==0){
			return null;
		}else{
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < pramaters.length; i++) {
				sb.append(pramaters[i]+"-");
			}
			sb.append(key);//最后加上Key值
			return this.encrypt(sb.toString());
		}
	}
	/* (non-Javadoc)
	 * @see wusc.edu.pay.common.utils.token.TokenBaseInter#productToken(java.lang.String, java.lang.String)
	 */
	@Override
	public String productToken(String pix, String userNo) {
		return this.encrypt(pix+"-"+userNo+"-"+System.currentTimeMillis()+"-"+key);
	}

}
