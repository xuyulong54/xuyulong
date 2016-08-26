package wusc.edu.pay.common.utils.rsa;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @描述: AES加解密
 *      (加密/解密算法：AES，工作模式：ECB，填充方式：NoPadding，其中若加密字符串长度不是16的倍数，则在其后补足0x00).
 * @作者: WuShuicheng.
 * @创建: 2015-3-17,下午4:30:34
 * @版本: V1.0
 * 
 */
public class AESCodec {
	
	private static final String KEY_ALGORITHM = "AES";

	// 加密、解密算法/工作模式/填充方式
	private static final String CIPHER_ALGORITHM = "AES/ECB/NoPadding";

	private static final int KEY_SIZE = 128;

	private static final String CHARSET = "UTF-8";

	// 解密
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return trim(cipher.doFinal(data));
	}

	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return decrypt(data, HexCodec.hexDecode(key));
	}

	public static String decrypt(String data, String key) throws Exception {
		return new String(decrypt(HexCodec.hexDecode(data), key), CHARSET);
	}

	// 加密
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(padding(data));
	}

	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return encrypt(data, HexCodec.hexDecode(key));
	}

	public static String encrypt(String data, String key) throws Exception {
		return HexCodec.hexEncode(encrypt(data.getBytes(CHARSET), key));
	}

	/**
	 * 采用NoPadding模式时，若加密字符串长度不是16的倍数，则须在其后补足0x00
	 * 
	 * @param data
	 * @return
	 */
	private static byte[] padding(byte[] data) {
		return padding(data, 16);
	}
	
	private static byte[] padding(byte[] data, int len) {
		int length = data.length;
		int remainder = length % len;

		if (remainder == 0) {
			return data;
		} else {
			byte[] newData = new byte[length + (len - remainder)];
			System.arraycopy(data, 0, newData, 0, length);
			return newData;
		}
	}

	private static byte[] trim(byte[] data) {
		int length = data.length;

		int counter = 0;
		for (int i = 1; i < 17; i++) {
			if (data[length - i] == (byte) 0x00) {
				counter++;
			}
		}

		return Arrays.copyOfRange(data, 0, (length - counter));
	}

	// 生成一个AES密钥
	public static byte[] genKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(KEY_SIZE);
		SecretKey secretKey = kg.generateKey();
		byte[] key = secretKey.getEncoded();
		return key;
	}

	private static byte[] encryptMD5(String data) {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException gse) {
			//ignore, must not be here
		} catch (UnsupportedEncodingException uee) {
			//ignore, must not be here
		}
		return bytes;
	}

}
