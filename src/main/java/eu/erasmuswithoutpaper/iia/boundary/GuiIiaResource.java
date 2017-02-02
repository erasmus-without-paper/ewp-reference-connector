package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.entity.DurationUnitVariants;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumberVariants;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("iia")
public class GuiIiaResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;

    @GET
    @Path("get_all")
    public Response getAll() {
        List<Iia> iiaList = em.createNamedQuery(Iia.findAll).getResultList();
        GenericEntity<List<Iia>> entity = new GenericEntity<List<Iia>>(iiaList) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("mobility_types")
    public Response getMobilityTypes() {
        List<MobilityType> mobilityTypeList = em.createNamedQuery(MobilityType.findAll).getResultList();
        GenericEntity<List<MobilityType>> entity = new GenericEntity<List<MobilityType>>(mobilityTypeList) {};
        
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("mobility_unit_variants")
    public Response getMobilityNumberVariants() {
        String[] statuses = MobilityNumberVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("duration_unit_variants")
    public Response getDurationUnitVariants() {
        String[] statuses = DurationUnitVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Iia iia) {
        em.persist(iia);
    }

    @GET
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiaHeis() {
        List<HeiEntry> heis = registryClient.getIiaHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("iias-index")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasIndex(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iia.endpoints.IiasIndexResponse.class);
        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }
    
    @POST
    @Path("iias")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iias(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iia.endpoints.IiasGetResponse.class);
        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }
}
