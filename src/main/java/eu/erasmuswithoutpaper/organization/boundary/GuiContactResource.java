
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.organization.entity.Contact;
import eu.erasmuswithoutpaper.organization.entity.ContactRoles;
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
@Path("contact")
public class GuiContactResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Contact contact) {
        em.persist(contact);
    }
    
    @GET
    @Path("get_all")
    public Response getAll() {
        List<Contact> contactList = em.createNamedQuery(Contact.findAll).getResultList();
            
        GenericEntity<List<Contact>> entity = new GenericEntity<List<Contact>>(contactList) {};
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("contact_roles")
    public Response getContactRoles() {
        String[] statuses = ContactRoles.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }
}
