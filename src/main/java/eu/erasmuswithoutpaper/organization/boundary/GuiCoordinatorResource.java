
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.organization.entity.Coordinator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("coordinator")
public class GuiCoordinatorResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Coordinator coordinator) {
        em.persist(coordinator);
    }
    
    @GET
    @Path("list")
    public Response listPost() {
        List<Coordinator> coordinatorList = em.createNamedQuery(Coordinator.findAll).getResultList();
            
        GenericEntity<List<Coordinator>> entity = new GenericEntity<List<Coordinator>>(coordinatorList) {};
        return Response.ok(entity).build();
    }
}
