
package eu.erasmuswithoutpaper.notification.boundary;

import eu.erasmuswithoutpaper.notification.entity.Notification;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Stateless
@Path("notification")
public class GuiNotificationResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
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
}
