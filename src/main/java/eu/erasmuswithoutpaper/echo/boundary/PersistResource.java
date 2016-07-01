package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.api.echo.Response;
import eu.erasmuswithoutpaper.echo.entity.MyEntity;
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
public class PersistResource {

    @PersistenceContext
    EntityManager em;

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
