package eu.erasmuswithoutpaper.internal.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class GlobalProperties {
    public static enum University {
        IKEA_U,
        POMODORO_U
    }
    
    Properties properties;
    
    @PostConstruct
    private void loadProperties() {
        properties = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("ewp.properties")) {
            properties.load(in);
            Logger.getLogger(GlobalProperties.class.getName()).log(Level.INFO, "Loaded properties from resource.");
        } catch (IOException ex) {
            Logger.getLogger(GlobalProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String overrideProperties = System.getProperty("ewp.override.properties");
        if (overrideProperties != null) {
            try {
                properties.load(new FileInputStream(overrideProperties));
                Logger.getLogger(GlobalProperties.class.getName()).log(Level.INFO, "Override properties from file '{0}'.", overrideProperties);
            } catch (IOException ex) {
                Logger.getLogger(GlobalProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public University getUniversity() {
        University university;
        switch(properties.getProperty("ewp.instance")) {
            case "POMODORO":
                university = University.POMODORO_U;
                break;
            default:
                university = University.IKEA_U;
                break;
        }
        return university;
    }
    
    public String getUniversityName() {
        return getProperty("ewp.name");
    }

    public String getHostname() {
        return getProperty("ewp.host.name");
    }
    
    public String getBaseUri() {
        return getProperty("ewp.base.uri");
    }
    
    public String getTruststoreLocation() {
        return getProperty("ewp.truststore.location");
    }
    public String getTruststorePassword() {
        return getProperty("ewp.truststore.password");
    }
    
    
    public String getKeystoreLocation() {
        return getProperty("ewp.keystore.location");
    }
    public String getKeystorePassword() {
        return getProperty("ewp.keystore.password");
    }
    public String getKeystoreCertificateAlias() {
        return getProperty("ewp.keystore.certificate.alias");
    }
    
    public boolean isAllowMissingClientCertificate() {
        return "true".equalsIgnoreCase(getProperty("ewp.allow.missing.client.certificate"));
    }

    private String getProperty(String key) {
        String property = properties.getProperty(key);
        return property;
    }
}
