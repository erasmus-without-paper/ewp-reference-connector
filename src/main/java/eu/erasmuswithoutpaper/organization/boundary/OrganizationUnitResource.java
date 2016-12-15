
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.api.ounits.OunitsResponse;
import eu.erasmuswithoutpaper.organization.control.OrganizationUnitConverter;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import java.util.ArrayList;
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
@Path("ounits")
public class OrganizationUnitResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    OrganizationUnitConverter organizationUnitConverter;
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response save(@QueryParam("hei_id") String heiId, @QueryParam("ounit_id") List<String> organizationUnitIdList) {
        return organizationUnits(heiId, organizationUnitIdList);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response getAll(@FormParam("hei_id") String heiId, @FormParam("ounit_id") List<String> organizationUnitIdList) {
        return organizationUnits(heiId, organizationUnitIdList);
    }
    
    private javax.ws.rs.core.Response organizationUnits(String heiId, List<String> organizationUnitIdList) {
        OunitsResponse response = new OunitsResponse();
        
        List<Institution> institutionList =  em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", heiId).getResultList();
        
        if (!institutionList.isEmpty()) {
            response.getOunit().addAll(ounits(organizationUnitIdList, institutionList.get(0).getOrganizationUnits(), null));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private List<OunitsResponse.Ounit> ounits(List<String> organizationUnitIdList, List<OrganizationUnit> organizationUnits, String parentOrganizationUnitId) {
        List<OunitsResponse.Ounit> ounits = new ArrayList<>();
        organizationUnits.stream().map((ou) -> {
            if (organizationUnitIdList.contains(ou.getOrganizationUnitId())) {
                ounits.add(organizationUnitConverter.convertToOunit(ou, parentOrganizationUnitId));
            }
            return ou;
        }).forEachOrdered((ou) -> {
            ounits.addAll(ounits(organizationUnitIdList, ou.getOrganizationUnits(), ou.getOrganizationUnitId()));
        });
        
        return ounits;
    }
}
