package eu.erasmuswithoutpaper.iias.boundary;

import eu.erasmuswithoutpaper.api.aiis.endpoints.Response;
import eu.erasmuswithoutpaper.api.aiis.endpoints.Response.Iia;
import eu.erasmuswithoutpaper.api.aiis.endpoints.Response.Iia.Partner;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("iia")
public class IIASResource {

    @GET
    @Path("list")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listGet(@QueryParam("hei_id") List<String> heiIdList) {
        return null;
    }

    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_XML)
    public Response listPost(@FormParam("hei_id") List<String> heiIdList) {
        return null;
    }
    
    @GET
    @Path("get")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getGet(@QueryParam("hei_id") String heiId, @QueryParam("iia_id") List<String> iiaIdList) {
        Response response = new Response();
        iiaIdList.stream().forEach(iiaId -> response.getIia().add(getIia(heiId, iiaId)));
        return response;
    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public Response getPost(@FormParam("hei_id") String heiId, @FormParam("iia_id") List<String> iiaIdList) {
        Response response = new Response();
        iiaIdList.stream().forEach(iiaId -> response.getIia().add(getIia(heiId, iiaId)));
        return response;
    }

    private Iia getIia(String heiId, String iiaId) {
        Iia iia = new Iia();
        iia.setId(iiaId);
        Partner p = new Partner();
        p.setInstitutionId(heiId);
        p.setOrganizationUnitId("dummy");
        iia.setPartner(p);
        return iia;
    }

}
