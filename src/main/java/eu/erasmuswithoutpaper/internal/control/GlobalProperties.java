package eu.erasmuswithoutpaper.internal.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
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
    University university;
    String defaultUniversityName;
    
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
        
        switch(properties.getProperty("ewp.instance")) {
            case "POMODORO":
                university = University.POMODORO_U;
                defaultUniversityName = "Pomodoro University";
                break;
            default:
                university = University.IKEA_U;
                defaultUniversityName = "IKEA University";
                break;
        }
        
    }
    
    public University getUniversity() {
        return university;
    }
    
    public String getUniversityName() {
        return getProperty("ewp.name", defaultUniversityName);
    }

    public Optional<String> getHostname() {
        return Optional.ofNullable(getProperty("ewp.host.name"));
    }
    
    public Optional<String> getBaseUri() {
        return Optional.ofNullable(getProperty("ewp.base.uri"));
    }
    
    public Optional<String> getTruststoreLocation() {
        return Optional.ofNullable(getProperty("ewp.truststore.location"));
    }
    public Optional<String> getTruststorePassword() {
        return Optional.ofNullable(getProperty("ewp.truststore.password"));
    }
    
    public Optional<String> getKeystoreLocation() {
        return Optional.ofNullable(getProperty("ewp.keystore.location"));
    }
    public Optional<String> getKeystorePassword() {
        return Optional.ofNullable(getProperty("ewp.keystore.password"));
    }
    public Optional<String> getKeystoreCertificateAlias() {
        return Optional.ofNullable(getProperty("ewp.keystore.certificate.alias"));
    }
    
    public boolean isAllowMissingClientCertificate() {
        return "true".equalsIgnoreCase(getProperty("ewp.allow.missing.client.certificate"));
    }
    
    public int getMaxInstitutionsIds() {
        return getIntProperty("ewp.api.institutions.max.ids", 1);
    }

    public int getMaxOunitsIds() {
        return getIntProperty("ewp.api.ounits.max.ids", 1);
    }
    
    private int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                Logger.getLogger(GlobalProperties.class.getName()).log(Level.SEVERE, "Not a number " + key, e);
            }
        }
        return defaultValue;
    }

    private String getProperty(String key) {
        return getProperty(key, null);
    }
    
    private String getProperty(String key, String defaultValue) {
        String property = properties.getProperty(key, defaultValue);
        return property;
    }
}
