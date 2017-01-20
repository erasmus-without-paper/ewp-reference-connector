
package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.organization.entity.Institution;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("institution")
public class GuiInstitutionResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Institution institution) {
        if (institution.getId() == null || institution.getId().isEmpty()) {
            em.persist(institution);
        } else {
            em.merge(institution);
        }
    }
    
    @GET
    @Path("get_all")
    public Response getAll() {
        List<Institution> institutionList = em.createNamedQuery(Institution.findAll).getResultList();

        GenericEntity<List<Institution>> entity = new GenericEntity<List<Institution>>(institutionList) {};
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response institutionHeis() {
        List<HeiEntry> institutionsHeis = registryClient.getEwpInstanceHeisWithUrl();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(institutionsHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response institutions(InstitutionRequest institutionRequest) {

        InstitutionResponse institutionResponse = new InstitutionResponse();
        try {
            WebTarget target = restClient.client().target(institutionRequest.getUrl());
            Response response;
            Instant start = Instant.now();
            switch (institutionRequest.getMethod()) {
                case POST:
                    Form form = new Form();
                    institutionRequest.getHeiIds().stream().forEach(e -> form.param("hei_id", e));
                    response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    break;
                case PUT:
                    response = target.request().put(null);
                    break;
                default:
                    for (String heiId : institutionRequest.getHeiIds()) {
                        target = target.queryParam("hei_id", heiId);
                    }
                    response = target.request().get();
                    break;
            }
            
            institutionResponse.setDuration(ChronoUnit.MILLIS.between(start,Instant.now()));
            
            institutionResponse.setStatusCode(response.getStatus());
            institutionResponse.setMediaType(response.getMediaType().toString());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                response.bufferEntity();
                
                String raw = response.readEntity(String.class);
                institutionResponse.setRawResponse(raw);
                //eu.erasmuswithoutpaper.api.echo.Response echo = response.readEntity(eu.erasmuswithoutpaper.api.echo.Response.class);

                //echoResponse.setEcho(new ArrayList<>(echo.getEcho()));
                //echoResponse.setHeiId(new ArrayList<>(echo.getHeiId()));
            } else {
                if (response.hasEntity()) {
                    response.bufferEntity();
                    String raw = response.readEntity(String.class);
                    institutionResponse.setRawResponse(raw);
                    try {
                        eu.erasmuswithoutpaper.api.architecture.ErrorResponse error = response.readEntity(eu.erasmuswithoutpaper.api.architecture.ErrorResponse.class);
                        institutionResponse.setErrorMessage(error.getDeveloperMessage().getValue());
                    } catch (Exception e) {
                        institutionResponse.setErrorMessage(raw);
                    }
                }
            }
        } catch (Exception e) {
            institutionResponse.setErrorMessage(e.getMessage());
        }
 
        return javax.ws.rs.core.Response.ok(institutionResponse).build();
    }

}
