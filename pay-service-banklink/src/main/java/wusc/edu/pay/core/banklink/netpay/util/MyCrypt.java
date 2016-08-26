package wusc.edu.pay.core.banklink.netpay.util;

import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyCrypt {
	// nNzYOIdKN2Q=
	public static void main(String args[]) {
		String value = "nNzYOIdKN2Q=";
		String key = "a1b2c3d4e5f6g7h8i9j0klmn";
		String CHAR_ENCODING = "UTF-8";
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] valueByte = Base64.decode(value.getBytes(CHAR_ENCODING));

			final String Algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyByte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			byte[] dataByte = c1.doFinal(valueByte);
			String data = new String(dataByte, CHAR_ENCODING);
			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}
}
