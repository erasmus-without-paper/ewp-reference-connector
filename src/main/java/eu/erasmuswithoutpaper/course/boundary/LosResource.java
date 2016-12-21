package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.api.courses.CoursesResponse;
import eu.erasmuswithoutpaper.course.control.LearningOpportunitySpecificationConverter;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
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
import javax.ws.rs.core.Response;

@Stateless
@Path("courses")
public class LosResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    LearningOpportunitySpecificationConverter losConverter;
    
    @Inject
    GlobalProperties properties;
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response losGet(@QueryParam("hei_id") String heiId, @QueryParam("los_id") List<String> losIdList) {
        return los(heiId, losIdList);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response losPost(@FormParam("hei_id") String heiId, @FormParam("los_id") List<String> losIdList) {
        return los(heiId, losIdList);
    }
    
    private javax.ws.rs.core.Response los(String heiId, List<String> losIdList) {
        CoursesResponse response = new CoursesResponse();
        if (losIdList.size() > properties.getMaxOunitsIds()) {
            throw new EwpWebApplicationException("Max number of los id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        List<LearningOpportunitySpecification> losList =  em.createNamedQuery(LearningOpportunitySpecification.findByInstitutionId).setParameter("institutionId", heiId).getResultList();
        response.getLearningOpportunitySpecification().addAll(los(losIdList, losList));
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private List<CoursesResponse.LearningOpportunitySpecification> los(List<String> losIdList, List<LearningOpportunitySpecification> losList) {
        List<CoursesResponse.LearningOpportunitySpecification> courses = new ArrayList<>();
        losList.stream().forEachOrdered((los) -> {
            if (losIdList.contains(los.getLosCode())) {
                courses.add(losConverter.convertToLos(los));
            }
        });
        
        return courses;
    }
}
