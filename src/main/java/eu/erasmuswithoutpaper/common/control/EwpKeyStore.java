package eu.erasmuswithoutpaper.common.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EwpKeyStore {
    private static final Logger logger = LoggerFactory.getLogger(EwpKeyStore.class);
    @Inject
    GlobalProperties properties;
    
    private String fomattedCertificate;
    private KeyStore truststore;
    private KeyStore keystore;
    private boolean successfullyInitiated;
    
    private Key ownPrivateKey;
    private String fomattedRsaPublicKey;
    private String ownPublicKeyFingerprint;
    
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
            logger.error("Missing keystore/truststore propeties");
            return;
        }
        
        try {
            truststore = KeyStore.getInstance("JKS");
            truststore.load(new FileInputStream(truststoreLocation.get()), truststorePassword.get().toCharArray());

            keystore = KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(keystoreLocation.get()), keystorePassword.get().toCharArray());
            
            retrieveCertificate(keystore, keystoreCertificateAlias.get());

            retrievePrivateKey(keystore, keystoreCertificateAlias.get(), keystorePassword.get());
            
            successfullyInitiated = true;
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException | UnrecoverableKeyException ex) {
            logger.error("Load keystore error", ex);
        }
    }
    
    private void retrievePrivateKey(KeyStore keystore, String alias, String pwd) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        ownPrivateKey = (PrivateKey) keystore.getKey(alias, pwd.toCharArray());
    }
    
    private void retrieveCertificate(KeyStore keystore, String alias) throws KeyStoreException, CertificateEncodingException {
        Certificate certificate = keystore.getCertificate(alias);
        byte[] publicKey = Base64.getEncoder().encode(certificate.getPublicKey().getEncoded());
        this.fomattedRsaPublicKey = new String(publicKey).replaceAll("(.{1,64})", "$1\n");

        this.ownPublicKeyFingerprint = DigestUtils.sha256Hex(certificate.getPublicKey().getEncoded());
        
        byte[] cert = Base64.getEncoder().encode(certificate.getEncoded());
        this.fomattedCertificate = new String(cert).replaceAll("(.{1,64})", "$1\n");
    }
    
    public String getCertificate() {
        return this.fomattedCertificate;
    }
    
    public Key getOwnPrivateKey() {
        return this.ownPrivateKey;
    }
    
    public String getRsaPublicKey() {
        return this.fomattedRsaPublicKey;
    }
    
    public String getOwnPublicKeyFingerprint() {
        return ownPublicKeyFingerprint;
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
