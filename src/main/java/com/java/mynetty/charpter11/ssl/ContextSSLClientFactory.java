package com.java.mynetty.charpter11.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class ContextSSLClientFactory {
	private static final SSLContext SSL_CONTEXT_S;
	static {
		SSLContext sslContextServer = null;
		try {
			sslContextServer = SSLContext.getInstance("SSLv3");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (getKeyManagerServer() != null && getTrustManagersServer() != null) {
				sslContextServer.init(getKeyManagerServer(), getTrustManagersServer(), null);
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sslContextServer.createSSLEngine().getSupportedCipherSuites();
		SSL_CONTEXT_S = sslContextServer;
	}
	private static TrustManager[] getTrustManagersServer() {
		// TODO Auto-generated method stub
		FileInputStream is = null;
		TrustManager[] trustManager = null;
		TrustManagerFactory trustManagerFactory = null;
		KeyStore ks = null;
		
		try {
			trustManagerFactory = TrustManagerFactory.getInstance("SunX509") ;
			is = new FileInputStream(new File("C:\\Users\\michael.qi\\clientCerts.jks"));
			ks = KeyStore.getInstance("JKS");
			String keyStorePass = "123456";
			ks.load(is, keyStorePass.toCharArray());
			trustManagerFactory.init(ks);
			trustManager = trustManagerFactory.getTrustManagers();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
            if(is != null ){
                try {
                    is.close() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return trustManager;
	}
	
    public static SSLContext getSslContext(){
        return SSL_CONTEXT_S ;
    }
	
	private static KeyManager[] getKeyManagerServer() {
		// TODO Auto-generated method stub
		FileInputStream is  = null;
		KeyStore ks = null;
		KeyManagerFactory keyFac = null;
		
		KeyManager[] kms = null;
		try {
			keyFac = KeyManagerFactory.getInstance("SunX509");
			
			is = new FileInputStream(new File("C:\\Users\\michael.qi\\clientCerts.jks"));
			ks = KeyStore.getInstance("JKS");
			String keyStorePass = "123456";
			ks.load(is,keyStorePass.toCharArray());
			keyFac.init(ks,keyStorePass.toCharArray());
			kms = keyFac.getKeyManagers();
		} catch (Exception e) {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return kms;
 	}
}
