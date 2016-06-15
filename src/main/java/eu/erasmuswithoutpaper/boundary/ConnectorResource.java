package eu.erasmuswithoutpaper.boundary;


import eu.erasmuswithoutpaper.api.echo.Response;
import eu.erasmuswithoutpaper.api.echo.Vars;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("")
public class ConnectorResource {

  @GET
  @Path("status")
  public String status() {
    return "I'm working!";
  }

  @GET
  @Path("manifest")
  @Produces(MediaType.APPLICATION_XML)
  public String manifest() throws URISyntaxException, IOException  {
          String content = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                        .getResource("manifest.xml")
                        .toURI())));
          return content;
  }
  
  @POST
  @Path("echo")
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Response echo(Vars vars) {
      Response response = new Response();
      response.setVars(vars);
      return response;
  }

  @GET
  @Path("echo")
  @Produces(MediaType.APPLICATION_XML)
  public Response echoGet(@QueryParam("vars") Vars vars) {
      Response response = new Response();
      response.setVars(vars);
      return response;
  }
}
