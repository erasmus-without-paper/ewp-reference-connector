package eu.erasmuswithoutpaper.internal.boundary;

import eu.erasmuswithoutpaper.internal.control.GlobalPropertiesController;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("home")
public class GuiHomeResource {
    @Inject
    private GlobalPropertiesController properties;

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
        return Response.ok(properties.getHostname()).build();
    }
}
