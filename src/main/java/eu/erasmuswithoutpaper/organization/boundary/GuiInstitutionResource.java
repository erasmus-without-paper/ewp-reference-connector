
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Stateless
@Path("institution")
public class GuiInstitutionResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Institution institution) {
        institution.setInstitutionId(UUID.randomUUID().toString());
        em.persist(institution);
    }

    @GET
    @Path("list")
    public Response listPost() {
        try {
            Query query = em.createQuery("select a from Institution a", Institution.class);
            List<Institution> institutionList = query.getResultList();
            
            ResponseBuilder builder = Response.status(Response.Status.ACCEPTED).entity(JsonHelper.objectToJson(institutionList));
            return builder.build();
        } catch (IOException ex) {
            Logger.getLogger(GuiInstitutionResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
