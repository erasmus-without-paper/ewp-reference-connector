
package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("los")
public class GuiLosResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(LearningOpportunitySpecification los) {
        if (los.getId() == null || los.getId().isEmpty()) {
            em.persist(los);
        } else {
            em.merge(los);
        }
    }
    
    @GET
    @Path("get_all")
    public Response getAll() {
        List<LearningOpportunitySpecification> losList = em.createNamedQuery(LearningOpportunitySpecification.findAll).getResultList();
        GenericEntity<List<LearningOpportunitySpecification>> entity = new GenericEntity<List<LearningOpportunitySpecification>>(losList) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("get_top_level_parents")
    public Response getAllTopLevelParents() {
        List<LearningOpportunitySpecification> losList = em.createNamedQuery(LearningOpportunitySpecification.findAllTopLevelParents).getResultList();
        GenericEntity<List<LearningOpportunitySpecification>> entity = new GenericEntity<List<LearningOpportunitySpecification>>(losList) {};
        
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("get_by_institution_id")
    public Response getByInstitutionId(@QueryParam("institutionId") String institutionId) {
        List<LearningOpportunitySpecification> losList = em.createNamedQuery(LearningOpportunitySpecification.findByInstitutionId).setParameter("institutionId", institutionId).getResultList();
        GenericEntity<List<LearningOpportunitySpecification>> entity = new GenericEntity<List<LearningOpportunitySpecification>>(losList) {};
        
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("course-replication")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response courseReplicationHeis() {
        List<HeiEntry> heis = registryClient.getCoursesReplicationHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("course-replication")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response courseReplication(ClientRequest request) {
        ClientResponse response = restClient.sendRequest(request, eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse.class);
        return javax.ws.rs.core.Response.ok(response).build();
    }

    @GET
    @Path("courses")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response coursesHeis() {
        List<HeiEntry> heis = registryClient.getCoursesHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("courses")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response courses(ClientRequest request) {
        ClientResponse response = restClient.sendRequest(request, eu.erasmuswithoutpaper.api.courses.CoursesResponse.class);
        return javax.ws.rs.core.Response.ok(response).build();
    }
}
