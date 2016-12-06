package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.internal.control.ClientRegistryController;
import eu.erasmuswithoutpaper.internal.control.HeiEntry;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            String heiUrl = clientRegistryController.getEchoHeiUrl(echoReuest.getHeiId());
            String getUrl = heiUrl + "?echo=" + String.join("&echo=", echoReuest.getEcho());
            URL url = new URL(getUrl);
            return javax.ws.rs.core.Response.ok(url.getContent()).build();
        } catch (IOException ex) {
            Logger.getLogger(GuiEchoResource.class.getName()).log(Level.SEVERE, null, ex);
            return javax.ws.rs.core.Response.serverError().build();
        }
    }
}
