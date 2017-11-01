package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.organization.entity.Institution;
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
        List<HeiEntry> institutionsHeis = registryClient.getEwpInstanceHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(institutionsHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response institutions(ClientRequest request) {
        ClientResponse response = restClient.sendRequest(request, eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse.class);
        return javax.ws.rs.core.Response.ok(response).build();
    }

    @GET
    @Path("ounits-heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response organizationUnitsHeis() {
        List<HeiEntry> organizationUnitHeis = registryClient.getEwpOrganizationUnitHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(organizationUnitHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("ounits-heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response organizationUnits(ClientRequest request) {
        ClientResponse response = restClient.sendRequest(request, eu.erasmuswithoutpaper.api.ounits.OunitsResponse.class);
        return javax.ws.rs.core.Response.ok(response).build();
    }
}
