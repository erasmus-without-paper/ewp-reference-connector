package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Stateless
@Path("iiapartner")
public class GuiIiaPartnerResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @GET
    @Path("get_all")
    public Response getAll() {
        List<IiaPartner> iiaPartnerList = em.createNamedQuery(IiaPartner.findAll).getResultList();
        GenericEntity<List<IiaPartner>> entity = new GenericEntity<List<IiaPartner>>(iiaPartnerList) {};
        
        return Response.ok(entity).build();
    }
    
}
