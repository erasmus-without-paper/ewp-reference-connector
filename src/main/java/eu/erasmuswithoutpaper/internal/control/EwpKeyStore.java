package eu.erasmuswithoutpaper.internal.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class EwpKeyStore {
    @Inject
    GlobalProperties properties;
    
    private String fomattedCertificate;
    private KeyStore truststore;
    private KeyStore keystore;
    
    @PostConstruct
    private void loadKeystore() {
        try {
            truststore = KeyStore.getInstance("JKS");
            truststore.load(new FileInputStream(properties.getTruststoreLocation()), properties.getTruststorePassword().toCharArray());

            keystore = KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(properties.getKeystoreLocation()), properties.getKeystorePassword().toCharArray());
            
            fomattedCertificate = getCertificate(keystore, properties.getKeystoreCertificateAlias());
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException ex) {
            Logger.getLogger(EwpKeyStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getCertificate(KeyStore keystore, String alias) throws KeyStoreException, CertificateEncodingException {
        Certificate certificate = keystore.getCertificate(alias);
        
        byte[] cert = Base64.getEncoder().encode(certificate.getEncoded());
        return new String(cert).replaceAll("(.{1,64})", "$1\n");
    }
    
    public String getCertificate() {
        return this.fomattedCertificate;
    }
    
    public KeyStore getKeystore() {
        return this.keystore;
    }
    
    public KeyStore getTruststore() {
        return this.truststore;
    }
}
