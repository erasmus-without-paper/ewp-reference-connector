
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
                eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse institutionsResponse = response.readEntity(eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse.class);

                institutionResponse.setHeis(institutionsResponse.getHei());
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

    @GET
    @Path("ounits-heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response organizationUnitsHeis() {
        List<HeiEntry> organizationUnitHeis = registryClient.getEwpOrganizationUnitHeisWithUrl();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(organizationUnitHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("ounits-heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response organizationUnits(OrganizationUnitRequest organizationUnitRequest) {

        OrganizationUnitResponse organizationUnitResponse = new OrganizationUnitResponse();
        try {
            WebTarget target = restClient.client().target(organizationUnitRequest.getUrl());
            Response response;
            Instant start = Instant.now();
            switch (organizationUnitRequest.getMethod()) {
                case POST:
                    Form form = new Form();
                    form.param("hei_id", organizationUnitRequest.getHeiId());
                    organizationUnitRequest.getOrganizationUnitIds().stream().forEach(e -> form.param("ounit_id", e));
                    response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    break;
                case PUT:
                    response = target.request().put(null);
                    break;
                default:
                    target = target.queryParam("hei_id", organizationUnitRequest.getHeiId());
                    for (String heiId : organizationUnitRequest.getOrganizationUnitIds()) {
                        target = target.queryParam("ounit_id", heiId);
                    }
                    response = target.request().get();
                    break;
            }
            
            organizationUnitResponse.setDuration(ChronoUnit.MILLIS.between(start,Instant.now()));
            
            organizationUnitResponse.setStatusCode(response.getStatus());
            organizationUnitResponse.setMediaType(response.getMediaType().toString());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                response.bufferEntity();
                
                String raw = response.readEntity(String.class);
                organizationUnitResponse.setRawResponse(raw);
                eu.erasmuswithoutpaper.api.ounits.OunitsResponse ounitResponse = response.readEntity(eu.erasmuswithoutpaper.api.ounits.OunitsResponse.class);
                organizationUnitResponse.setOrganizationUnits(ounitResponse.getOunit());
            } else {
                if (response.hasEntity()) {
                    response.bufferEntity();
                    String raw = response.readEntity(String.class);
                    organizationUnitResponse.setRawResponse(raw);
                    try {
                        eu.erasmuswithoutpaper.api.architecture.ErrorResponse error = response.readEntity(eu.erasmuswithoutpaper.api.architecture.ErrorResponse.class);
                        organizationUnitResponse.setErrorMessage(error.getDeveloperMessage().getValue());
                    } catch (Exception e) {
                        organizationUnitResponse.setErrorMessage(raw);
                    }
                }
            }
        } catch (Exception e) {
            organizationUnitResponse.setErrorMessage(e.getMessage());
        }
 
        return javax.ws.rs.core.Response.ok(organizationUnitResponse).build();
    }
}
