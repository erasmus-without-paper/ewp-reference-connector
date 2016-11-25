
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.organization.entity.Institution;
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
@Path("institution")
public class GuiInstitutionResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Institution institution) {
        if (institution.getId() == 0) {
            em.persist(institution);
        } else {
            em.merge(institution);
        }
    }
    
    @GET
    @Path("get_all")
    public Response getAll() {
        List<Institution> institutionList = em.createNamedQuery(Institution.findAll).getResultList();

        GenericEntity<List<Institution>> entity = new GenericEntity<List<Institution>>(institutionList) {};
        return Response.ok(entity).build();
    }
}
