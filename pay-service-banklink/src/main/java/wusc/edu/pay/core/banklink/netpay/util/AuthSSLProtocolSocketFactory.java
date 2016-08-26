package wusc.edu.pay.core.banklink.netpay.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthSSLProtocolSocketFactory implements SecureProtocolSocketFactory {

	private static final Log LOG = LogFactory.getLog(AuthSSLProtocolSocketFactory.class);

	private String keystoreUrl = null;
	private String keystorePassword = null;
	private String truststoreUrl = null;
	private String truststorePassword = null;
	private SSLContext sslcontext = null;

	public AuthSSLProtocolSocketFactory(final String keyStoreFile, String keystorePassword, String truststoreFile,
			final String truststorePassword) {
		super();
		this.keystoreUrl = keyStoreFile;
		this.keystorePassword = keystorePassword;
		this.truststoreUrl = truststoreFile;
		this.truststorePassword = truststorePassword;
	}

	private static KeyStore createKeyStore(final String file, final String password) throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {

		if (file == null) {
			throw new IllegalArgumentException("Keystore url may not be null");
		}
		LOG.debug("Initializing key store");
		KeyStore keystore = null;
		InputStream is = null;
		try {
			// 尝试 使用PKCS12的格式读取私钥证书
			keystore = KeyStore.getInstance("PKCS12");
			is = new FileInputStream(new File(file));
			keystore.load(is, password != null ? password.toCharArray() : null);
		} catch (Exception ce) {
			// 再次 尝试使用JKS的格式读取私钥证书
			keystore = KeyStore.getInstance("jks");
			is = new FileInputStream(new File(file));
			keystore.load(is, password != null ? password.toCharArray() : null);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return keystore;
	}

	private static KeyManager[] createKeyManagers(final KeyStore keystore, final String password) throws KeyStoreException,
			NoSuchAlgorithmException, UnrecoverableKeyException {
		if (keystore == null) {
			throw new IllegalArgumentException("Keystore may not be null");
		}
		LOG.debug("Initializing key manager");
		KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmfactory.init(keystore, password != null ? password.toCharArray() : null);
		return kmfactory.getKeyManagers();
	}

	private static TrustManager[] createTrustManagers(final KeyStore keystore) throws KeyStoreException, NoSuchAlgorithmException {
		if (keystore == null) {
			throw new IllegalArgumentException("Keystore may not be null");
		}
		LOG.debug("Initializing trust manager");
		TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmfactory.init(keystore);
		TrustManager[] trustmanagers = tmfactory.getTrustManagers();
		for (int i = 0; i < trustmanagers.length; i++) {
			if (trustmanagers[i] instanceof X509TrustManager) {
				trustmanagers[i] = new AuthSSLX509TrustManager((X509TrustManager) trustmanagers[i]);
			}
		}
		return trustmanagers;
	}

	private SSLContext createSSLContext() {
		try {
			KeyManager[] keymanagers = null;
			TrustManager[] trustmanagers = null;
			if (this.keystoreUrl != null) {
				// 证明自己身份的keyStore
				KeyStore keystore = createKeyStore(this.keystoreUrl, this.keystorePassword);
				if (LOG.isDebugEnabled()) {
					Enumeration<String> aliases = keystore.aliases();
					while (aliases.hasMoreElements()) {
						String alias = (String) aliases.nextElement();
						Certificate[] certs = keystore.getCertificateChain(alias);
						if (certs != null) {
							LOG.debug("Certificate chain '" + alias + "':");
							for (int c = 0; c < certs.length; c++) {
								if (certs[c] instanceof X509Certificate) {
									X509Certificate cert = (X509Certificate) certs[c];
									LOG.debug(" Certificate " + (c + 1) + ":");
									LOG.debug("  Subject DN: " + cert.getSubjectDN());
									LOG.debug("  Signature Algorithm: " + cert.getSigAlgName());
									LOG.debug("  Valid from: " + cert.getNotBefore());
									LOG.debug("  Valid until: " + cert.getNotAfter());
									LOG.debug("  Issuer: " + cert.getIssuerDN());
								}
							}
						}
					}
				}
				keymanagers = createKeyManagers(keystore, this.keystorePassword);
			}
			if (this.truststoreUrl != null) {
				// 信任其他的store
				KeyStore keystore = createKeyStore(this.truststoreUrl, this.truststorePassword);
				if (LOG.isDebugEnabled()) {
					Enumeration<String> aliases = keystore.aliases();
					while (aliases.hasMoreElements()) {
						String alias = (String) aliases.nextElement();
						LOG.debug("Trusted certificate '" + alias + "':");
						Certificate trustedcert = keystore.getCertificate(alias);
						if (trustedcert != null && trustedcert instanceof X509Certificate) {
							X509Certificate cert = (X509Certificate) trustedcert;
							LOG.debug("  Subject DN: " + cert.getSubjectDN());
							LOG.debug("  Signature Algorithm: " + cert.getSigAlgName());
							LOG.debug("  Valid from: " + cert.getNotBefore());
							LOG.debug("  Valid until: " + cert.getNotAfter());
							LOG.debug("  Issuer: " + cert.getIssuerDN());
						}
					}
				}
				trustmanagers = createTrustManagers(keystore);
			}
			SSLContext sslcontext = SSLContext.getInstance("SSLv3");
			sslcontext.init(keymanagers, trustmanagers, null);
			return sslcontext;
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage(), e);
			throw new Error("Unsupported algorithm exception: " + e.getMessage());
		} catch (KeyStoreException e) {
			LOG.error(e.getMessage(), e);
			throw new Error("Keystore exception: " + e.getMessage());
		} catch (GeneralSecurityException e) {
			LOG.error(e.getMessage(), e);
			throw new Error("Key management exception: " + e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			throw new Error("I/O error reading keystore/truststore file: " + e.getMessage());
		}
	}

	private SSLContext getSSLContext() {
		if (this.sslcontext == null) {
			this.sslcontext = createSSLContext();
		}
		return this.sslcontext;
	}

	public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort,
			final HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
		if (params == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		}
		int timeout = params.getConnectionTimeout();
		SocketFactory socketfactory = getSSLContext().getSocketFactory();
		if (timeout == 0) {
			return socketfactory.createSocket(host, port, localAddress, localPort);
		} else {
			Socket socket = socketfactory.createSocket();
			SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
			SocketAddress remoteaddr = new InetSocketAddress(host, port);
			socket.bind(localaddr);
			socket.connect(remoteaddr, timeout);
			return socket;
		}
	}

	/**
	 * @see SecureProtocolSocketFactory#createSocket(java.lang.String,int,java.net.InetAddress,int)
	 */
	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
	}

	/**
	 * @see SecureProtocolSocketFactory#createSocket(java.lang.String,int)
	 */
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port);
	}

	/**
	 * @see SecureProtocolSocketFactory#createSocket(java.net.Socket,java.lang.String,int,boolean)
	 */
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
	}
}