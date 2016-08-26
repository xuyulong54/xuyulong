package wusc.edu.pay.common.utils.rsa;

import java.nio.ByteBuffer;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESede {

	/**
	 * 对称加密DESede密钥算法 JAVA 6 支持长度为112和168位 Bouncy Castle支持密钥长度为128位和192位
	 */
	public static final String KEY_ALGORITHM = "DESede";

	/**
	 * 加解密算法/工作模式/填充方式NoPadding JAVA 6 支持DESede/ECB/PKCS5Padding填充方式 Bouncy
	 * Castle支持PKCS7Padding填充方式
	 */
	public static final String CIPHER_ALGORITHM = "DESede/ECB/NoPadding";

	// public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

	// Bouncy Castle算法提供者
	// public static final String PROVIDER_BOUNCY_CASTOLE = "BC";

	// 将C#的字节数组转换成JAVA字节数组
	public static byte[] convertByte(int[] keyInt) {
		byte[] keyByte = new byte[keyInt.length];
		for (int i = 0; i < keyByte.length; i++) {
			if (keyInt[i] > 127) {
				keyByte[i] = (byte) (keyInt[i] - 256);
			} else {
				keyByte[i] = (byte) keyInt[i];
			}
		}
		return keyByte;
	}

	// 生成密钥
	public static byte[] initKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(192);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	// 转换密钥
	private static final Key toKey(byte[] key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		return keyFactory.generateSecret(dks);
	}

	// 加密数据
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		if (key.length < 24) {
			key = convertKey(key);
		}
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		// Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	// 解密数据
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		if (key.length < 24) {
			key = convertKey(key);
		}
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	public static byte[] decryptPck(byte[] data, byte[] key) throws Exception {
		if (key.length < 24) {
			key = convertKey(key);
		}
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		//IvParameterSpec ips = new IvParameterSpec(data);
		// Cipher cipher = Cipher.getInstance(KEY_ALGORITHM); // DESede/ECB/PKCS5Padding
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	// 将16字节的密钥转换成24字节
	public static byte[] convertKey(byte[] srcKey) {
		byte[] destKey = null;

		byte[] keyFirst = new byte[8];
		ByteBuffer buffer = ByteBuffer.wrap(srcKey);
		buffer.get(keyFirst);
		buffer.clear();
		buffer = ByteBuffer.allocate(24);
		buffer.put(srcKey);
		buffer.put(keyFirst);
		buffer.flip();
		destKey = buffer.array();
		buffer.clear();
		return destKey;
	}
}
