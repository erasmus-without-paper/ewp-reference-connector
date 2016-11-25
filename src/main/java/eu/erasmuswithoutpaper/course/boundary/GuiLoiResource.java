
package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance;
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
@Path("loi")
public class GuiLoiResource {
@PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(LearningOpportunityInstance loi) {
        em.persist(loi);
    }
    
    @GET
    @Path("get_all")
    public Response getAll() {
        List<LearningOpportunityInstance> loiList = em.createNamedQuery(LearningOpportunityInstance.findAll).getResultList();
        GenericEntity<List<LearningOpportunityInstance>> entity = new GenericEntity<List<LearningOpportunityInstance>>(loiList) {};
        
        return Response.ok(entity).build();
    }
}
