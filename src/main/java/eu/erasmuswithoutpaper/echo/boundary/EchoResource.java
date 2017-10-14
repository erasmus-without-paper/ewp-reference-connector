package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.api.echo.Response;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("echo")
public class EchoResource {
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    GlobalProperties properties;
    
    @Context
    HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response echoGet(@QueryParam("echo") List<String> echo) {
        return echo(echo);
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response echoPost(@FormParam("echo") List<String> echo) {
        return echo(echo);
    }
    
    private javax.ws.rs.core.Response echo(List<String> echo) {
        Collection<String> heisCovered;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCovered = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCovered = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }
        
        Response response = new Response();
        echo.stream().forEach(e -> response.getEcho().add(e));
        heisCovered.stream().forEach(i -> response.getHeiId().add(i));
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
}
