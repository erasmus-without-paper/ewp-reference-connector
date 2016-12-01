package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.iia.entity.Iia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
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
    
//    @POST
//    @Path("add")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void listGet(@QueryParam("iia") Iia iia) {
//        // TODO: Create ID and add iia to DB
//    }
//
//    @GET
//    @Path("get_all")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Iia> getAll() {
//        Query query = em.createQuery("select a from Iia a", eu.erasmuswithoutpaper.iia.entity.Iia.class);
//        List<Iia> iiaList = query.getResultList();
//
//        return iiaList;
//    }

}
