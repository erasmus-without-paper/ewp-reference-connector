package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
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
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response echoHeis() {
        List<HeiEntry> echoHeis = registryClient.getEchoHeis();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(echoHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response echo(ClientRequest request) {
        request.setUrl(registryClient.getEchoHeiUrls(request.getHeiId()).get("url"));
        ClientResponse response = restClient.sendRequest(request, eu.erasmuswithoutpaper.api.echo.Response.class);
        return javax.ws.rs.core.Response.ok(response).build();
    }
}
