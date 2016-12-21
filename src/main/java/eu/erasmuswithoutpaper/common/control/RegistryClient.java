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
    
    private final String ECHO_NAMESPACE = "https://github.com/erasmus-without-paper/ewp-specs-api-echo/blob/stable-v1/manifest-entry.xsd";
    
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
    
    public List<HeiEntry> getEchoHeis() {
        ApiSearchConditions myEchoConditions = new ApiSearchConditions();
        myEchoConditions.setApiClassRequired(ECHO_NAMESPACE, "echo", "1.0.1");
        
        List<HeiEntry> heis = client.findHeis(myEchoConditions).stream().map(e -> new HeiEntry(e.getId(), e.getName())).collect(Collectors.toList());
        
        return heis;
    }

    public String getEchoHeiUrl(String heiId) {
        ApiSearchConditions myEchoConditions = new ApiSearchConditions();
        myEchoConditions.setApiClassRequired(ECHO_NAMESPACE, "echo", "1.0.1");
        myEchoConditions.setRequiredHei(heiId);
        Element manifest = client.findApi(myEchoConditions);

        return parseEchoManifest(manifest);
    }
    
    private String parseEchoManifest(Element echoManifestElement) {
        String echoUrl = null;
        NodeList echoChildNodeList = echoManifestElement.getChildNodes();
        for (int i = 0; i < echoChildNodeList.getLength(); i++) {
            Node echoChildNode = echoChildNodeList.item(i);
            if ("url".equalsIgnoreCase(echoChildNode.getLocalName())) {
                echoUrl = echoChildNode.getFirstChild().getNodeValue();
            }
        }
        
        return echoUrl;
    }
}
