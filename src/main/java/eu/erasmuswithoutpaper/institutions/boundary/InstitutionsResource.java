package eu.erasmuswithoutpaper.institutions.boundary;

import eu.erasmuswithoutpaper.api.institutions.Response;
import eu.erasmuswithoutpaper.institutions.control.InstitutionController;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("institution")
public class InstitutionsResource {
    
    @Inject
    private InstitutionController institutionController;

    @GET
    @Path("get")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getGet(@QueryParam("hei_id") List<String> heiIdList, @DefaultValue("false") @QueryParam("include_iro_sections") boolean includeIroSections) {
        return institutionController.getAllInstitutions();
    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public Response getPost(@FormParam("hei_id") List<String> heiIdList, @DefaultValue("false") @FormParam("include_iro_sections") boolean includeIroSections) {
        return institutionController.getAllInstitutions();
    }

}
