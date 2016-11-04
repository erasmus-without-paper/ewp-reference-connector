package eu.erasmuswithoutpaper.internal.control;

import eu.erasmuswithoutpaper.registryclient.ApiSearchConditions;
import eu.erasmuswithoutpaper.registryclient.ClientImpl;
import eu.erasmuswithoutpaper.registryclient.ClientImplOptions;
import eu.erasmuswithoutpaper.registryclient.HeiEntry;
import eu.erasmuswithoutpaper.registryclient.RegistryClient;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class ClientRegistryController {
    
    private RegistryClient client;
    
    @PostConstruct
    private void loadClientRegistry() {
        ClientImplOptions options = new ClientImplOptions();
        client = new ClientImpl(options);
    }
    
    public void getEchoHeis() {
        ApiSearchConditions myEchoConditions = new ApiSearchConditions();
        String ns = "https://github.com/erasmus-without-paper/ewp-specs-api-echo/blob/stable-v1/manifest-entry.xsd";
        myEchoConditions.setApiClassRequired(ns, "echo", "1.0.1");
        Collection<HeiEntry> heis = client.findHeis(myEchoConditions);        
    }
}
