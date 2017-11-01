package eu.erasmuswithoutpaper.common.control;

import eu.erasmuswithoutpaper.registryclient.ApiSearchConditions;
import eu.erasmuswithoutpaper.registryclient.ClientImpl;
import eu.erasmuswithoutpaper.registryclient.ClientImplOptions;
import eu.erasmuswithoutpaper.registryclient.DefaultCatalogueFetcher;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Singleton
public class RegistryClient {
    private static final Logger logger = LoggerFactory.getLogger(RegistryClient.class);
    private eu.erasmuswithoutpaper.registryclient.RegistryClient client;
    
    @Inject
    private GlobalProperties properties;
    
    @PostConstruct
    private void loadRegistryClient() {
        try {
            ClientImplOptions options = new ClientImplOptions();
            options.setCatalogueFetcher(new DefaultCatalogueFetcher(properties.getRegistryUrl()));
            options.setAutoRefreshing(properties.isRegistryAutoRefreshing());
            options.setTimeBetweenRetries(properties.getRegistryTimeBetweenRetries());
            client = new ClientImpl(options);
            
            client.refresh();
        } catch (eu.erasmuswithoutpaper.registryclient.RegistryClient.RefreshFailureException ex) {
            logger.error("Can't refresh registry client", ex);
        }
    }
    
    public X509Certificate getCertificateKnownInEwpNetwork(X509Certificate[] certificates) {
        if(certificates == null) {
            return null;
        }
        
        for (X509Certificate certificate : certificates) {
            if (client.isCertificateKnown(certificate)) {
                return certificate;
            }
        }
        return null;
    }

    public Collection<String> getHeisCoveredByCertificate(X509Certificate certificate) {
        if (certificate != null && client.isCertificateKnown(certificate)) {
            Collection<String> heiIds = client.getHeisCoveredByCertificate(certificate);
            return heiIds;
        }
        
        return new ArrayList<>();
    }
    
    public List<HeiEntry> getEwpInstanceHeisWithUrls() {
        List<HeiEntry> heis = getHeis(EwpConstants.INSTITUTION_NAMESPACE, "institutions", EwpConstants.INSTITUTION_CLIENT_VERSION);
        heis.stream().forEach(hei -> hei.setUrls(getEwpInstanceHeiUrls(hei.getId())));
        return heis;
    }
    
    public Map<String, String> getEwpInstanceHeiUrls(String heiId) {
        return getHeiUrls(heiId, EwpConstants.INSTITUTION_NAMESPACE, "institutions", EwpConstants.INSTITUTION_CLIENT_VERSION);
    }
    
    public List<HeiEntry> getEwpOrganizationUnitHeisWithUrls() {
        List<HeiEntry> heis = getHeis(EwpConstants.ORGANIZATION_UNIT_NAMESPACE, "organizational-units", EwpConstants.ORGANIZATION_UNIT_CLIENT_VERSION);
        heis.stream().forEach(hei -> hei.setUrls(getEwpOrganizationUnitHeiUrls(hei.getId())));
        return heis;
    }
    
    public Map<String, String> getEwpOrganizationUnitHeiUrls(String heiId) {
        return getHeiUrls(heiId, EwpConstants.ORGANIZATION_UNIT_NAMESPACE, "organizational-units", EwpConstants.ORGANIZATION_UNIT_CLIENT_VERSION);
    }

    public List<HeiEntry> getCoursesReplicationHeisWithUrls() {
        List<HeiEntry> heis = getHeis(EwpConstants.COURSE_REPLICATION_NAMESPACE, "simple-course-replication", EwpConstants.COURSE_REPLICATION_CLIENT_VERSION);
        heis.stream().forEach(hei -> hei.setUrls(getCoursesReplicationHeiUrls(hei.getId())));
        return heis;
    }

    public Map<String, String> getCoursesReplicationHeiUrls(String heiId) {
        return getHeiUrls(heiId, EwpConstants.COURSE_REPLICATION_NAMESPACE, "simple-course-replication", EwpConstants.COURSE_REPLICATION_CLIENT_VERSION);
    }

    public List<HeiEntry> getCoursesHeisWithUrls() {
        List<HeiEntry> heis = getHeis(EwpConstants.COURSES_NAMESPACE, "courses", EwpConstants.COURSES_CLIENT_VERSION);
        heis.stream().forEach(hei -> hei.setUrls(getCoursesHeiUrls(hei.getId())));
        return heis;
    }

    public Map<String, String> getCoursesHeiUrls(String heiId) {
        return getHeiUrls(heiId, EwpConstants.COURSES_NAMESPACE, "courses", EwpConstants.COURSES_CLIENT_VERSION);
    }

    public List<HeiEntry> getIiaHeisWithUrls() {
        List<HeiEntry> heis = getHeis(EwpConstants.IIAS_NAMESPACE, "iias", EwpConstants.IIAS_CLIENT_VERSION);
        heis.stream().forEach(hei -> hei.setUrls(getIiaHeiUrls(hei.getId())));
        return heis;
    }

    public Map<String, String> getIiaHeiUrls(String heiId) {
        return getHeiUrls(heiId, EwpConstants.IIAS_NAMESPACE, "iias", EwpConstants.IIAS_CLIENT_VERSION);
    }

    public List<HeiEntry> getOmobilitiesHeisWithUrls() {
        List<HeiEntry> heis = getHeis(EwpConstants.OUTGOING_MOBILITIES_NAMESPACE, "omobilities", EwpConstants.OUTGOING_MOBILITIES_CLIENT_VERSION);
        heis.stream().forEach(hei -> hei.setUrls(getOmobilitiesHeiUrls(hei.getId())));
        return heis;
    }

    public Map<String, String> getOmobilitiesHeiUrls(String heiId) {
        return getHeiUrls(heiId, EwpConstants.OUTGOING_MOBILITIES_NAMESPACE, "omobilities", EwpConstants.OUTGOING_MOBILITIES_CLIENT_VERSION);
    }
    
    public List<HeiEntry> getEchoHeis() {
        return getHeis(EwpConstants.ECHO_NAMESPACE, "echo", EwpConstants.ECHO_CLIENT_VERSION);
    }
    
    public Map<String, String> getEchoHeiUrls(String heiId) {
        return getHeiUrls(heiId, EwpConstants.ECHO_NAMESPACE, "echo", EwpConstants.ECHO_CLIENT_VERSION);
    }
    
    private Map<String, String> getHeiUrls(String heiId, String namespace, String name, String version) {
        ApiSearchConditions myConditions = new ApiSearchConditions();
        myConditions.setApiClassRequired(namespace, name, version);
        myConditions.setRequiredHei(heiId);
        Element manifest = client.findApi(myConditions);

        return getUrlsFromManifestElement(manifest);
    }
    
    private List<HeiEntry> getHeis(String namespace, String name, String version) {
        ApiSearchConditions myConditions = new ApiSearchConditions();
        myConditions.setApiClassRequired(namespace, name, version);
        
        Collection<eu.erasmuswithoutpaper.registryclient.HeiEntry> list = client.findHeis(myConditions);
        List<HeiEntry> heis = list.stream().map(e -> new HeiEntry(e.getId(), e.getName())).collect(Collectors.toList());
        
        return heis;
    }
     
   private Map<String, String> getUrlsFromManifestElement(Element manifestElement) {
        Map<String, String> urlMap = new HashMap<>();
        NodeList childNodeList = manifestElement.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            if ("url".equalsIgnoreCase(childNode.getLocalName())) {
                urlMap.put("url", childNode.getFirstChild().getNodeValue());
            } else if ("index-url".equalsIgnoreCase(childNode.getLocalName())) {
                urlMap.put("index-url", childNode.getFirstChild().getNodeValue());
            } else if ("get-url".equalsIgnoreCase(childNode.getLocalName())) {
                urlMap.put("get-url", childNode.getFirstChild().getNodeValue());
            }
        }
        
        return urlMap;
    }

    public RSAPublicKey findRsaPublicKey(String fingerprint) {
        return client.findRsaPublicKey(fingerprint);
    }

    public RSAPublicKey findClientRsaPublicKey(String fingerprint) {
        RSAPublicKey rsaPublicKey = client.findRsaPublicKey(fingerprint);
        return rsaPublicKey != null && client.isClientKeyKnown(rsaPublicKey) ? rsaPublicKey : null;
    }
    
    public Collection<String> getHeisCoveredByClientKey(RSAPublicKey rsapk) {
        return client.getHeisCoveredByClientKey(rsapk);
    }
}
