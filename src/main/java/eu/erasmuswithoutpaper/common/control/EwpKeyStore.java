package eu.erasmuswithoutpaper.common.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Optional;
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
    private boolean successfullyInitiated;
    
    @PostConstruct
    private void loadKeystore() {
        Optional<String> truststoreLocation = properties.getTruststoreLocation();
        Optional<String> truststorePassword = properties.getTruststorePassword();
        Optional<String> keystoreLocation = properties.getKeystoreLocation();
        Optional<String> keystorePassword = properties.getKeystorePassword();
        Optional<String> keystoreCertificateAlias = properties.getKeystoreCertificateAlias();
        if (!truststoreLocation.isPresent() || !truststorePassword.isPresent() 
                || !keystoreLocation.isPresent() || !keystorePassword.isPresent()
                || !keystoreCertificateAlias.isPresent()) {
            Logger.getLogger(EwpKeyStore.class.getName()).log(Level.SEVERE, "Missing keystore/truststore propeties");
            return;
        }
        
        try {
            truststore = KeyStore.getInstance("JKS");
            truststore.load(new FileInputStream(truststoreLocation.get()), truststorePassword.get().toCharArray());

            keystore = KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(keystoreLocation.get()), keystorePassword.get().toCharArray());
            
            fomattedCertificate = getCertificate(keystore, keystoreCertificateAlias.get());
            
            successfullyInitiated = true;
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

    public boolean isSuccessfullyInitiated() {
        return successfullyInitiated;
    }
}
