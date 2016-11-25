package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.iia.entity.Iia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("iia")
public class GuiIiaResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void listGet(@QueryParam("iia") Iia iia) {
        // TODO: Create ID and add iia to DB
    }

    @GET
    @Path("get_all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Iia> getAll() {
        Query query = em.createQuery("select a from Iia a", eu.erasmuswithoutpaper.iia.entity.Iia.class);
        List<Iia> iiaList = query.getResultList();

        return iiaList;
    }

}
