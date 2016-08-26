package wusc.edu.pay.webservice.merchant.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @描述: DES加密工具类.
 * @作者: WuShuicheng.
 * @创建: 2015-1-12,下午2:38:56
 * @版本: V1.0
 * 
 */
public class DES {

	public static String encryptDES(String encryptString, String encryptKey) throws Exception {
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
		return Base64.encodeBase64String(encryptedData);
	}

	public static String decryptDES(String decryptString, String decryptKey) throws Exception {
		byte[] byteMi = Base64.decodeBase64(decryptString);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte decryptedData[] = cipher.doFinal(byteMi);
		return new String(decryptedData);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(DES.encryptDES("张文斯", "01020304"));
		System.out.println(DES.decryptDES("2OKkF/l94fmLacrJrcSvBg==", "01020304"));
	}
	
}