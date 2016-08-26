package wusc.edu.pay.api.merchant.utils;

import javax.net.ssl.KeyManagerFactory;

public class ClientKeyStore {
	private KeyManagerFactory keyManagerFactory;
	
	ClientKeyStore(KeyManagerFactory keyManagerFactory){
		this.keyManagerFactory = keyManagerFactory;
	}
	
	KeyManagerFactory getKeyManagerFactory(){
		return keyManagerFactory;
	}
}
