
package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.course.entity.AcademicTerm;
import eu.erasmuswithoutpaper.course.entity.AcademicYear;
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
@Path("academic_term")
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
    @Path("get_all")
    public Response getAll() {
        List<AcademicTerm> academicTermList = em.createNamedQuery(AcademicTerm.findAll).getResultList();
        GenericEntity<List<AcademicTerm>> entity = new GenericEntity<List<AcademicTerm>>(academicTermList) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("list_academic_years")
    public Response listAcademicYears() {
        List<AcademicYear> academicYearList = em.createNamedQuery(AcademicYear.findAll).getResultList();
        GenericEntity<List<AcademicYear>> entity = new GenericEntity<List<AcademicYear>>(academicYearList) {};
        
        return Response.ok(entity).build();
    }
}
