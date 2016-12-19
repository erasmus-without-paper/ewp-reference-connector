package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
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
@Path("iia")
public class GuiIiaResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @GET
    @Path("get_all")
    public Response getAll() {
        List<Iia> iiaList = em.createNamedQuery(Iia.findAll).getResultList();
        GenericEntity<List<Iia>> entity = new GenericEntity<List<Iia>>(iiaList) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("get_mobility_types")
    public Response getMobilityTypes() {
        List<MobilityType> mobilityTypeList = em.createNamedQuery(MobilityType.findAll).getResultList();
        GenericEntity<List<MobilityType>> entity = new GenericEntity<List<MobilityType>>(mobilityTypeList) {};
        
        return Response.ok(entity).build();
    }
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Iia iia) {
        em.persist(iia);
    }

}
