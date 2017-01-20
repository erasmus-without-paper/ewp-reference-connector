package eu.erasmuswithoutpaper.common.control;

import eu.erasmuswithoutpaper.registryclient.ApiSearchConditions;
import eu.erasmuswithoutpaper.registryclient.ClientImpl;
import eu.erasmuswithoutpaper.registryclient.ClientImplOptions;
import eu.erasmuswithoutpaper.registryclient.DefaultCatalogueFetcher;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Singleton
public class RegistryClient {
    
    
    private eu.erasmuswithoutpaper.registryclient.RegistryClient client;
    
    @PostConstruct
    private void loadRegistryClient() {
        try {
            ClientImplOptions options = new ClientImplOptions();
            options.setCatalogueFetcher(new DefaultCatalogueFetcher("dev-registry.erasmuswithoutpaper.eu"));
            client = new ClientImpl(options);
            
            client.refresh();
        } catch (eu.erasmuswithoutpaper.registryclient.RegistryClient.RefreshFailureException ex) {
            Logger.getLogger(RegistryClient.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<HeiEntry> getEwpInstanceHeisWithUrl() {
        List<HeiEntry> heis = getHeis(EwpConstants.INSTITUTION_NAMESPACE, "institutions", "0.4.0");
        heis.stream().forEach(hei -> hei.setUrl(getEwpInstanceHeiUrl(hei.getId())));
        return heis;
    }
    
    public String getEwpInstanceHeiUrl(String heiId) {
        return getHeiUrl(heiId, EwpConstants.INSTITUTION_NAMESPACE, "institutions", "0.4.0");
    }
    
    public List<HeiEntry> getEchoHeis() {
        return getHeis(EwpConstants.ECHO_NAMESPACE, "echo", EwpConstants.ECHO_VERSION);
    }

    public String getEchoHeiUrl(String heiId) {
        return getHeiUrl(heiId, EwpConstants.ECHO_NAMESPACE, "echo", EwpConstants.ECHO_VERSION);
    }
    
    private String getHeiUrl(String heiId, String namespace, String name, String version) {
        ApiSearchConditions myConditions = new ApiSearchConditions();
        myConditions.setApiClassRequired(namespace, name, version);
        myConditions.setRequiredHei(heiId);
        Element manifest = client.findApi(myConditions);

        return getUrlFromManifestElement(manifest);
    }
    
    private List<HeiEntry> getHeis(String namespace, String name, String version) {
        ApiSearchConditions myConditions = new ApiSearchConditions();
        myConditions.setApiClassRequired(namespace, name, version);
        
        Collection<eu.erasmuswithoutpaper.registryclient.HeiEntry> list = client.findHeis(myConditions);
        List<HeiEntry> heis = list.stream().map(e -> new HeiEntry(e.getId(), e.getName())).collect(Collectors.toList());
        
        return heis;
    }
     
   private String getUrlFromManifestElement(Element manifestElement) {
        String url = null;
        NodeList childNodeList = manifestElement.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            if ("url".equalsIgnoreCase(childNode.getLocalName())) {
                url = childNode.getFirstChild().getNodeValue();
            }
        }
        
        return url;
    }
}
