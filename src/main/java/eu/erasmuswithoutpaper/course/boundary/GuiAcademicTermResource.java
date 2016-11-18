
package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.course.entity.AcademicTerm;
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
@Path("academicterm")
public class GuiAcademicTermResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(AcademicTerm academicTerm) {
        em.persist(academicTerm);
    }
    
    @GET
    @Path("list")
    public Response listPost() {
        List<AcademicTerm> academicTermList = em.createNamedQuery(AcademicTerm.findAll).getResultList();
        GenericEntity<List<AcademicTerm>> entity = new GenericEntity<List<AcademicTerm>>(academicTermList) {};
        
        return Response.ok(entity).build();
    }
}
