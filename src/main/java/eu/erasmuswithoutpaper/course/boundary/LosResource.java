package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.api.courses.CoursesResponse;
import eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.course.control.LearningOpportunitySpecificationConverter;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecificationType;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    public javax.ws.rs.core.Response losGet(@QueryParam("hei_id") String heiId, @QueryParam("los_id") List<String> losIdList, 
            @QueryParam("lois_before") String loisBefore, @QueryParam("lois_after") String loisAfter, @QueryParam("los_at_date") String losAtDate) {
        return los(heiId, losIdList, loisBefore, loisAfter, losAtDate);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response losPost(@FormParam("hei_id") String heiId, @FormParam("los_id") List<String> losIdList, 
            @FormParam("lois_before") String loisBefore, @FormParam("lois_after") String loisAfter, @FormParam("los_at_date") String losAtDate) {
        return los(heiId, losIdList, loisBefore, loisAfter, losAtDate);
    }

    @GET
    @Path("replication")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response losReplicationGet(@QueryParam("hei_id") String heiId, @QueryParam("modified_since") String modifiedSince) {
        return losReplication(heiId, modifiedSince);
    }
    
    @POST
    @Path("replication")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response losReplicationPost(@FormParam("hei_id") String heiId, @FormParam("modified_since") String modifiedSince) {
        return losReplication(heiId, modifiedSince);
    }
    
    private javax.ws.rs.core.Response los(String heiId, List<String> losIdList, String loisBefore, String loisAfter, String losAtDate) {
        if (losIdList.size() > properties.getMaxLosIds()) {
            throw new EwpWebApplicationException("Max number of los id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        if (!isInstitutionInEwp(heiId)) {
            throw new EwpWebApplicationException("Not a valid hei_id.", Response.Status.BAD_REQUEST);
        }
        
        CoursesResponse response = new CoursesResponse();
        List<LearningOpportunitySpecification> losList =  em.createNamedQuery(LearningOpportunitySpecification.findByInstitutionId).setParameter("institutionId", heiId).getResultList();

        response.getLearningOpportunitySpecification().addAll(los(losIdList, losList, loisBefore, loisAfter, losAtDate));
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private boolean isInstitutionInEwp(String institutionId) {
        List<Institution> institutionList =  em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", institutionId).getResultList();
        return !institutionList.isEmpty();
    }
    
    private List<CoursesResponse.LearningOpportunitySpecification> los(List<String> losIdList, List<LearningOpportunitySpecification> losList, String loisBefore, String loisAfter, String losAtDate) {
        List<CoursesResponse.LearningOpportunitySpecification> courses = new ArrayList<>();
        losList.stream().forEachOrdered((los) -> {
            String id = LearningOpportunitySpecificationType.abbreviation(los.getType()) + "/" + los.getId();
            if (losIdList.contains(id)) {
                courses.add(losConverter.convertToLos(los, loisBefore, loisAfter, losAtDate));
            }
        });
        
        return courses;
    }

    private Response losReplication(String heiId, String modifiedSince) {
        if (!isInstitutionInEwp(heiId)) {
            throw new EwpWebApplicationException("Not a valid hei_id.", Response.Status.BAD_REQUEST);
        }
        
        List<LearningOpportunitySpecification> losList =  em.createNamedQuery(LearningOpportunitySpecification.findByInstitutionId).setParameter("institutionId", heiId).getResultList();
        
        CourseReplicationResponse response = new CourseReplicationResponse();
        List<String> losIds = losList.stream().map(los -> LearningOpportunitySpecificationType.abbreviation(los.getType()) + "/" + los.getId()).collect(Collectors.toList());
        response.getLosId().addAll(losIds);
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
}
