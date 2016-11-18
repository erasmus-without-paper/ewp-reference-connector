
package eu.erasmuswithoutpaper.course.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

@Stateless
@Path("academicterm")
public class GuiAcademicTermResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    

}
