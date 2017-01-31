
package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
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
        List<HeiEntry> heis = registryClient.getCoursesReplicationHeisWithUrl();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("course-replication")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response courseReplication(LosRequest losReplicationRequest) {

        LosResponse losReplicationResponse = new LosResponse();
        try {
            WebTarget target = restClient.client().target(losReplicationRequest.getUrl());
            Response response;
            Instant start = Instant.now();
            switch (losReplicationRequest.getMethod()) {
                case POST:
                    Form form = new Form();
                    form.param("hei_id", losReplicationRequest.getHeiId());
                    response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    break;
                case PUT:
                    response = target.request().put(null);
                    break;
                default:
                    target = target.queryParam("hei_id", losReplicationRequest.getHeiId());
                    response = target.request().get();
                    break;
            }
            
            losReplicationResponse.setDuration(ChronoUnit.MILLIS.between(start,Instant.now()));
            
            losReplicationResponse.setStatusCode(response.getStatus());
            losReplicationResponse.setMediaType(response.getMediaType().toString());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                response.bufferEntity();
                
                String raw = response.readEntity(String.class);
                losReplicationResponse.setRawResponse(raw);
                eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse losResponse = response.readEntity(eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse.class);

                losReplicationResponse.setResultList(losResponse.getLosId());
            } else {
                if (response.hasEntity()) {
                    response.bufferEntity();
                    String raw = response.readEntity(String.class);
                    losReplicationResponse.setRawResponse(raw);
                    try {
                        eu.erasmuswithoutpaper.api.architecture.ErrorResponse error = response.readEntity(eu.erasmuswithoutpaper.api.architecture.ErrorResponse.class);
                        losReplicationResponse.setErrorMessage(error.getDeveloperMessage().getValue());
                    } catch (Exception e) {
                        losReplicationResponse.setErrorMessage(raw);
                    }
                }
            }
        } catch (Exception e) {
            losReplicationResponse.setErrorMessage(e.getMessage());
        }
 
        return javax.ws.rs.core.Response.ok(losReplicationResponse).build();
    }

    @GET
    @Path("courses")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response coursesHeis() {
        List<HeiEntry> heis = registryClient.getCoursesHeisWithUrl();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("courses")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response courses(LosRequest losRequest) {

        LosResponse losResponse = new LosResponse();
        try {
            WebTarget target = restClient.client().target(losRequest.getUrl());
            Response response;
            Instant start = Instant.now();
            switch (losRequest.getMethod()) {
                case POST:
                    Form form = new Form();
                    form.param("hei_id", losRequest.getHeiId());
                    losRequest.getLosIds().stream().forEach(e -> form.param("los_id", e));
                    response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    break;
                case PUT:
                    response = target.request().put(null);
                    break;
                default:
                    target = target.queryParam("hei_id", losRequest.getHeiId());
                    for (String heiId : losRequest.getLosIds()) {
                        target = target.queryParam("los_id", heiId);
                    }
                    response = target.request().get();
                    break;
            }
            
            losResponse.setDuration(ChronoUnit.MILLIS.between(start,Instant.now()));
            
            losResponse.setStatusCode(response.getStatus());
            losResponse.setMediaType(response.getMediaType().toString());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                response.bufferEntity();
                
                String raw = response.readEntity(String.class);
                losResponse.setRawResponse(raw);
                eu.erasmuswithoutpaper.api.courses.CoursesResponse coursesResponse = response.readEntity(eu.erasmuswithoutpaper.api.courses.CoursesResponse.class);

                losResponse.setResultList(coursesResponse.getLearningOpportunitySpecification());
            } else {
                if (response.hasEntity()) {
                    response.bufferEntity();
                    String raw = response.readEntity(String.class);
                    losResponse.setRawResponse(raw);
                    try {
                        eu.erasmuswithoutpaper.api.architecture.ErrorResponse error = response.readEntity(eu.erasmuswithoutpaper.api.architecture.ErrorResponse.class);
                        losResponse.setErrorMessage(error.getDeveloperMessage().getValue());
                    } catch (Exception e) {
                        losResponse.setErrorMessage(raw);
                    }
                }
            }
        } catch (Exception e) {
            losResponse.setErrorMessage(e.getMessage());
        }
 
        return javax.ws.rs.core.Response.ok(losResponse).build();
    }
}
