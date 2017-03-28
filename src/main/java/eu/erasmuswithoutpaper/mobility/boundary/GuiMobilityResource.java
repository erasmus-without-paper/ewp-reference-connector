
package eu.erasmuswithoutpaper.mobility.boundary;

import eu.erasmuswithoutpaper.mobility.entity.LearningAgreementComponentStatus;
import eu.erasmuswithoutpaper.mobility.entity.Mobility;
import eu.erasmuswithoutpaper.mobility.entity.MobilityStatus;
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
@Path("mobility")
public class GuiMobilityResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Mobility mobility) {
        em.persist(mobility);
    }

    @GET
    @Path("get_all")
    public Response getAll() {
        List<Mobility> mobiltyList = em.createNamedQuery(Mobility.findAll).getResultList();
        GenericEntity<List<Mobility>> entity = new GenericEntity<List<Mobility>>(mobiltyList) {};
        
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("mobility_statuses")
    public Response getMobilityStatuses() {
        String[] statuses = MobilityStatus.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("lacomponent_statuses")
    public Response getLaComponentStatuses() {
        String[] statuses = LearningAgreementComponentStatus.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }
}
