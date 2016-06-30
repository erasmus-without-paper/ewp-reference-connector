package eu.erasmuswithoutpaper.boundary;

import eu.erasmuswithoutpaper.api.echo.Response;
import eu.erasmuswithoutpaper.entity.MyEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    EntityManager em;

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

    @POST
    @Path("persist")
    @Produces(MediaType.APPLICATION_XML)
    public Response persistPost(@FormParam("text") List<String> text) {

        Response response = new Response();
        text.stream().forEach(e -> em.persist(new MyEntity(e)));
        text.stream().forEach(e -> response.getEcho().add(e));
        return response;
    }

    @GET
    @Path("persist")
    @Produces(MediaType.APPLICATION_XML)
    public Response persistGet() {
        Response response = new Response();

        List<MyEntity> list = em.createQuery("select a from MyEntity a", MyEntity.class).getResultList();
        list.stream().forEach(e -> response.getEcho().add(e.getText()));
        return response;
    }
}
