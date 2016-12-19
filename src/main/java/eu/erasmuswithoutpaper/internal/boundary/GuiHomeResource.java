package eu.erasmuswithoutpaper.internal.boundary;

import eu.erasmuswithoutpaper.internal.control.GlobalProperties;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless
@Path("home")
public class GuiHomeResource {
    @Inject
    GlobalProperties properties;
    
    @Context
    UriInfo uriInfo;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("name")
    public Response name() {
        return Response.ok(properties.getUniversityName()).build();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hostname")
    public Response hostname() {
        Optional<String> hostnameProperty = properties.getHostname();
        String hostname = hostnameProperty.isPresent() ? hostnameProperty.get() : uriInfo.getBaseUri().getHost();
        return Response.ok(hostname).build();
    }
}

