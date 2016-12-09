package eu.erasmuswithoutpaper.internal.control;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class RestClient {
    @Inject
    GlobalProperties properties;
    
    @Inject
    EwpKeyStore keystoreController;

    private Client client;
    
    @PostConstruct
    private void createClient() {
        try {
            SSLContext context = initSecurityContext(keystoreController.getKeystore(), keystoreController.getTruststore(), properties.getKeystorePassword());

            ClientBuilder clientBuilder = ClientBuilder.newBuilder().sslContext(context);
            clientBuilder.hostnameVerifier((String string, SSLSession ssls) -> true);
            client = clientBuilder.build();
        } catch (NoSuchAlgorithmException | KeyStoreException | NoSuchProviderException | UnrecoverableKeyException | KeyManagementException ex) {
            Logger.getLogger(RestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Client client() {
        return client;
    }
    
    public <T> T get(String url, Class<T> clazz) {
        Response response = client().target(url).request().get();
        return response.readEntity(clazz);
    }
    
    private static SSLContext initSecurityContext(KeyStore keyStore, KeyStore trustStore, String pwd) throws NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509", "SunJSSE");
        kmf.init(keyStore, pwd.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext context = SSLContext.getInstance("TLS", "SunJSSE");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        return context;
    }
    
    public String getCertificate(KeyStore keystore, String alias) throws KeyStoreException, CertificateEncodingException {
        Certificate certificate = keystore.getCertificate(alias);
        
        byte[] cert = Base64.getEncoder().encode(certificate.getEncoded());
        return new String(cert).replaceAll("(.{1,64})", "$1\n");
    }
}
