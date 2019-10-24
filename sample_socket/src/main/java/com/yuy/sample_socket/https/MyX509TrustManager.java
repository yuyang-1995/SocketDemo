package com.yuy.sample_socket.https;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Author: yuyang
 * Date:2019/10/24 22:34
 * Description:
 * Version:
 */
public class MyX509TrustManager implements X509TrustManager {
    private X509Certificate mServerCert;

    public MyX509TrustManager(X509Certificate certificate) {
        mServerCert = certificate;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        for (X509Certificate certificate : chain) {

            // 证书是否过期以及合法性的校验
            certificate.checkValidity();
            try {
                certificate.verify(mServerCert.getPublicKey());
            } catch (Exception e) {
                throw new CertificateException(e);
            }

        }

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}
