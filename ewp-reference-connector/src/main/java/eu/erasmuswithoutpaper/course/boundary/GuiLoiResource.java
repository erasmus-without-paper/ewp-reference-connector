
package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.course.entity.GradingScheme;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Stateless
@Path("loi")
public class GuiLoiResource {
@PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @GET
    @Path("grading_schemes")
    public Response getGradingSchemes() {
        List<GradingScheme> gradingSchemeList = em.createNamedQuery(GradingScheme.findAll).getResultList();
        GenericEntity<List<GradingScheme>> entity = new GenericEntity<List<GradingScheme>>(gradingSchemeList) {};
        
        return Response.ok(entity).build();
    }
}
