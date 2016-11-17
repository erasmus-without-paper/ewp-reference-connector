
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Person;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("person")
public class GuiPersonResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Person person) {
        em.persist(person);
    }

    @GET
    @Path("list")
    public Response listPost() {
        List<Person> personList = em.createNamedQuery(Person.findAll).getResultList();
        GenericEntity<List<Person>> entity = new GenericEntity<List<Person>>(personList) {};
        
        String test;
        try {
            test = JsonHelper.objectToJson(personList);
        } catch (IOException ex) {
            Logger.getLogger(GuiPersonResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.ok(entity).build();
    }

}
