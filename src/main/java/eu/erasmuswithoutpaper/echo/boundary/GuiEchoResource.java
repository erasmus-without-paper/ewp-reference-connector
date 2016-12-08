package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.internal.control.ClientRegistryController;
import eu.erasmuswithoutpaper.internal.control.HeiEntry;
import eu.erasmuswithoutpaper.internal.control.RestClient;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("echo")
public class GuiEchoResource {

    @Inject
    ClientRegistryController clientRegistryController;
    
    @Inject
    RestClient restClient;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response echoHeis() {
        List<HeiEntry> echoHeis = clientRegistryController.getEchoHeis();
            
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(echoHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response echo(EchoRequest echoReuest) {
            String heiUrl = clientRegistryController.getEchoHeiUrl(echoReuest.getHeiId());
            String getUrl = heiUrl + "?echo=" + String.join("&echo=", echoReuest.getEcho());
            
            String answer = restClient.get(getUrl, String.class);
            return javax.ws.rs.core.Response.ok(answer).build();
    }
}
