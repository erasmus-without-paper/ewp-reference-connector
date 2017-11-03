
package eu.erasmuswithoutpaper.notification.boundary;

import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Stateless
@Path("notification")
public class GuiNotificationResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @GET
    @Path("get_all")
    public Response getAll() {
        List<Notification> notificationList = em.createNamedQuery(Notification.findAll).getResultList();
        GenericEntity<List<Notification>> entity = new GenericEntity<List<Notification>>(notificationList) {};
        
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("count")
    public Response getCount() {
        List<Notification> notificationList = em.createNamedQuery(Notification.findAll).getResultList();
        
        return Response.ok("{\"count\":"+notificationList.size()+"}").build();
    }
    
    @GET
    @Path("iia-cnr-heis")
    public javax.ws.rs.core.Response iiaCnrHeis() {
        List<HeiEntry> iiaCnrHeis = registryClient.getIiaCnrHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(iiaCnrHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @GET
    @Path("omobility-cnr-heis")
    public javax.ws.rs.core.Response omobilityCnrHeis() {
        List<HeiEntry> cnrHeis = registryClient.getOmobilitiesCnrHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(cnrHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @GET
    @Path("imobility-cnr-heis")
    public javax.ws.rs.core.Response imobilityCnrHeis() {
        List<HeiEntry> cnrHeis = registryClient.getImobilitiesCnrHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(cnrHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @GET
    @Path("tors-cnr-heis")
    public javax.ws.rs.core.Response torsCnrHeis() {
        List<HeiEntry> cnrHeis = registryClient.getImobilityTorsCnrHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(cnrHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("notify")
    public javax.ws.rs.core.Response notify(ClientRequest request) {
        ClientResponse response = restClient.sendRequest(request, String.class);
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
}
