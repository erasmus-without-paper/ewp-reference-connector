package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.api.echo.Response;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.internal.control.GlobalProperties;
import eu.erasmuswithoutpaper.internal.control.RegistryClient;
import java.security.cert.X509Certificate;
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
    public javax.ws.rs.core.Response echoGet(@QueryParam("echo") List<String> echo) {
        return echo(echo);
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response echoPost(@FormParam("echo") List<String> echo) {
        return echo(echo);
    }
    
    private javax.ws.rs.core.Response echo(List<String> echo) {
                
        X509Certificate[] certificates = (X509Certificate[]) httpRequest.getAttribute("javax.servlet.request.X509Certificate");
        if (certificates == null && !properties.isAllowMissingClientCertificate()) {
            throw new EwpWebApplicationException("No client certificates found in the request", javax.ws.rs.core.Response.Status.FORBIDDEN);
        }
        
        X509Certificate certificate = registryClient.getCertificateKnownInEwpNetwork(certificates);
        if (certificate == null && !properties.isAllowMissingClientCertificate()) {
            throw new EwpWebApplicationException("None of the client certificates is valid in the EWP network", javax.ws.rs.core.Response.Status.FORBIDDEN);
        }
        
        Response response = new Response();
        echo.stream().forEach(e -> response.getEcho().add(e));
        registryClient.getHeisCoveredByCertificate(certificate).stream().forEach(i -> response.getHeiId().add(i));
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
}
