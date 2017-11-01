
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.organization.entity.MobilityParticipant;
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
@Path("participant")
public class GuiMobilityParticipantResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(MobilityParticipant mobilityParticipant) {
        em.persist(mobilityParticipant);
    }
    
    @GET
    @Path("get_all")
    public Response getAll() {
        List<MobilityParticipant> mobilityParticipantList = em.createNamedQuery(MobilityParticipant.findAll).getResultList();
            
        GenericEntity<List<MobilityParticipant>> entity = new GenericEntity<List<MobilityParticipant>>(mobilityParticipantList) {};
        return Response.ok(entity).build();
    }
}
