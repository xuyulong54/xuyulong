package wusc.edu.pay.core.banklink.netpay.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Administrator
 * 
 */
public class AuthSSLX509TrustManager implements X509TrustManager {

	private static final Log logger = LogFactory.getLog(BankConfUtil.class);

	private X509TrustManager defaultTrustManager = null;

	public AuthSSLX509TrustManager(final X509TrustManager defaultTrustManager) {
		super();
		if (defaultTrustManager == null) {
			throw new IllegalArgumentException("Trust manager may not be null");
		}
		this.defaultTrustManager = defaultTrustManager;
	}

	@Override
	public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
		logger.debug("paramString:" + paramString);
		for (X509Certificate x509Certificate : paramArrayOfX509Certificate) {
			logger.debug("Client certificate :");
			logger.debug("Subject DN: " + x509Certificate.getSubjectDN());
			logger.debug("Signature Algorithm: " + x509Certificate.getSigAlgName());
			logger.debug("Valid from: " + x509Certificate.getNotBefore());
			logger.debug("Valid until: " + x509Certificate.getNotAfter());
			logger.debug("Issuer: " + x509Certificate.getIssuerDN());
		}
		this.defaultTrustManager.checkClientTrusted(paramArrayOfX509Certificate, paramString);
	}

	@Override
	public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
		logger.debug("paramString:{}" + paramString);
		for (X509Certificate x509Certificate : paramArrayOfX509Certificate) {
			logger.debug("Server certificate :");
			logger.debug("Subject DN: " + x509Certificate.getSubjectDN());
			logger.debug("Signature Algorithm: " + x509Certificate.getSigAlgName());
			logger.debug("Valid from: " + x509Certificate.getNotBefore());
			logger.debug("Valid until: " + x509Certificate.getNotAfter());
			logger.debug("Issuer: " + x509Certificate.getIssuerDN());
		}
		this.defaultTrustManager.checkServerTrusted(paramArrayOfX509Certificate, paramString);
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return this.defaultTrustManager.getAcceptedIssuers();
	}

}
