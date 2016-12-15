
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse;
import eu.erasmuswithoutpaper.organization.control.InstitutionConverter;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("institutions")
public class InstitutionResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    InstitutionConverter institutionConverter;
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response save(@QueryParam("hei_id") List<String> heiIdList) {
        return institution(heiIdList);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response getAll(@FormParam("hei_id") List<String> heiIdList) {
        return institution(heiIdList);
    }
    
    private javax.ws.rs.core.Response institution(List<String> heiIdList) {
        InstitutionsResponse response = new InstitutionsResponse();
        
        heiIdList.stream()
                .map((String heiId) -> em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", heiId).getResultList())
                .forEachOrdered((institutionList) -> {
                    response.getHei().addAll(institutionConverter.convertToHei(institutionList));
                });
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
}
