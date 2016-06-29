package eu.erasmuswithoutpaper.boundary;


import eu.erasmuswithoutpaper.api.echo.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("")
public class EchoResource {

  @GET
  @Path("echo")
  @Produces(MediaType.APPLICATION_XML)
  public Response echoGet(@QueryParam("echo") List<String> echo) {
      Response response = new Response();
      echo.stream().forEach(e -> response.getEcho().add(e));
      return response;
  }

  @POST
  @Path("echo")
  @Produces(MediaType.APPLICATION_XML)
  public Response echoPost(@FormParam("echo") List<String> echo) {
      Response response = new Response();
      echo.stream().forEach(e -> response.getEcho().add(e));
      return response;
  }

}
