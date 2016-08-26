
package wusc.edu.pay.webservice.merchant.unit;

/**
 * 常量类
 * 
 */
public class UmsConstant {
    // 客户端是否生产版本
    public static final boolean isProdVersion = false;

    public static final int TIME_OUT = 30 * 1000;

    // 签名字段缓存的key
    public static final String SIGN_KEY_KEY = "sign_key";

    // 加密字段缓存的key
    public static final String ENCRYPT_PUBLIC_KEY = "encrypt_key";

    // 解密字段的key
    public static final String DECRYPT_PRIVATE_KEY = "decrypt_key";

    // work secrity key
    public static final String CRYPT_KEY = "work_key";
    
}
