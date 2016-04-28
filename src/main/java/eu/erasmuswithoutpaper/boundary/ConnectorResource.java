package eu.erasmuswithoutpaper.boundary;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
@Path("")
public class ConnectorResource {

  @GET
  @Path("status")
  public String status() {
    return "I'm working!";
  }
}
