package wusc.edu.pay.common.utils.rsa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 类描述：
 * 
 * @author: huangbin
 * @date： 日期：2013-12-31 时间：下午3:17:25
 * @version 1.0
 */
public class RSACoder {
	public static final String KEY_ALGORITHM = "RSA"; // 加密方式
	// 数字签名 签名/验证算法
	private static final String SIGNATURE_ALGORRITHM = "SHA1withRSA";
	// 公钥变量名
	private static final String PUBLIC_KEY = "RSAPublicKey";
	// 私钥变量名
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	// RSA密钥长度,默认为1024,密钥长度必须是64的倍数,范围在512~65526位之间
	private static final int KEY_SIZE = 1024;
	// RSA最大加密明文大小
	private static final int MAX_ENCRYPT_BLOCK = 117;
	// RSA最大解密密文大小
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 生成密钥
	 * 
	 * @return 密钥Map
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<String, String> generateKey() throws NoSuchAlgorithmException {
		// 实例化实钥对生成器
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥对生成器
		keyPairGen.initialize(KEY_SIZE, new SecureRandom());
		// 生成密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 封装密钥
		Map<String, String> keyMap = new HashMap<String, String>(2);
		keyMap.put(PUBLIC_KEY, Base64.encode(publicKey.getEncoded()));
		keyMap.put(PRIVATE_KEY, Base64.encode(privateKey.getEncoded()));
		return keyMap;
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            私钥
	 * @return String 加密数据
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 */
	public static String encryptByPrivateKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		byte[] keybyte = Base64.decode(key);
		// 取得私钥 PKCS8EncodedKeySpec封装私钥的类
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keybyte);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] databyte = data.getBytes();
		int inputLen = databyte.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(databyte, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(databyte, offSet, inputLen - offSet);
			}
			out.write(cache);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encbyte = out.toByteArray();
		String enc = Base64.encode(encbyte);
		out.close();
		return enc;
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            公钥
	 * @return String 加密数据
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 */
	public static String encryptByPublicKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		byte[] keybyte = Base64.decode(key);
		// 取得公钥 X509EncodedKeySpec封装公钥的类
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keybyte);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] databyte = data.getBytes();
		int inputLen = databyte.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(databyte, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(databyte, offSet, inputLen - offSet);
			}
			out.write(cache);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encbyte = out.toByteArray();
		String enc = Base64.encode(encbyte);
		out.close();
		return enc;
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            私钥
	 * @return String 解密数据
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 */
	public static String decryptByPrivateKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		byte[] keybyte = Base64.decode(key);
		// 取得私钥 PKCS8EncodedKeySpec封装私钥的类
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keybyte);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] databyte = Base64.decode(data);
		int inputLen = databyte.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(databyte, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(databyte, offSet, inputLen - offSet);
			}
			out.write(cache);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decbyte = out.toByteArray();
		String dec = new String(decbyte);
		out.close();
		return dec;
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            公钥
	 * @return String 解密数据
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 */
	public static String decryptByPublicKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		byte[] keybyte = Base64.decode(key);
		// 取得公钥 X509EncodedKeySpec封装公钥的类
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keybyte);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] databyte = Base64.decode(data);
		int inputLen = databyte.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(databyte, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(databyte, offSet, inputLen - offSet);
			}
			out.write(cache);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decbyte = out.toByteArray();
		String dec = new String(decbyte);
		out.close();
		return dec;
	}

	/**
	 * 私钥签名
	 * 
	 * @param data
	 *            待签名的加密数据
	 * @param privateKey
	 *            私钥
	 * @return String 数字签名
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static String sign(String data, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		byte[] privateKeyByte = Base64.decode(privateKey);
		// 转接私钥材料
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取私钥对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORRITHM);
		// 初始化Signature
		signature.initSign(priKey);
		// 更新
		signature.update(Base64.decode(data));
		// 签名
		byte[] signbyte = signature.sign();
		String signstr = Base64.encode(signbyte);
		return signstr;
	}

	/**
	 * 公钥校验
	 * 
	 * @param data
	 *            待校验数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static boolean verify(String data, String publicKey, String sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		byte[] publicKeyByte = Base64.decode(publicKey);
		// 转接公钥材料
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyByte);
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORRITHM);
		// 初始化Signature
		signature.initVerify(pubKey);
		// 更新
		signature.update(Base64.decode(data));
		// 验证
		boolean isVerify = signature.verify(Base64.decode(sign));
		return isVerify;
	}

}
