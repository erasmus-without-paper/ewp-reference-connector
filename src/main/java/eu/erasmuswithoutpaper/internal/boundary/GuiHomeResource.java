package eu.erasmuswithoutpaper.internal.boundary;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("home")
public class GuiHomeResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response name() {
        String name;
        String ewpHost = System.getProperty("ewp.host");
        if (ewpHost == null || ewpHost.equals("IKEA")) {
            name = "IKEA University";
        } else {
            name = "Pomodoro University";
        }
        return Response.ok(name).build();
    }
}
