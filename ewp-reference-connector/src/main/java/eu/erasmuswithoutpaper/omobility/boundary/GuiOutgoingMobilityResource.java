
package eu.erasmuswithoutpaper.omobility.boundary;

import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.omobility.entity.LearningAgreementComponentStatus;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;
import eu.erasmuswithoutpaper.omobility.entity.MobilityStatus;
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
@Path("omobility")
public class GuiOutgoingMobilityResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;

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
    
    @GET
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response omobilitiesHeis() {
        List<HeiEntry> heis = registryClient.getOmobilitiesHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("omobilities-index")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response omobilitiesIndex(ClientRequest clientRequest) {
        ClientResponse omobilitiesResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesIndexResponse.class);
        return javax.ws.rs.core.Response.ok(omobilitiesResponse).build();
    }
    
    @POST
    @Path("omobilities-get")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response omobilitiesGet(ClientRequest clientRequest) {
        ClientResponse omobilitiesResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesGetResponse.class);
        return javax.ws.rs.core.Response.ok(omobilitiesResponse).build();
    }
    
    @POST
    @Path("omobilities-update")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response omobilitiesUpdate(ClientRequest clientRequest) {
        ClientResponse omobilitiesResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesUpdateResponse.class);
        return javax.ws.rs.core.Response.ok(omobilitiesResponse).build();
    }
}
