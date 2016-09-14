package eu.erasmuswithoutpaper.iias.boundary;

import eu.erasmuswithoutpaper.api.aiis.endpoints.Response;
import eu.erasmuswithoutpaper.iias.control.IiaController;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
    
    @Inject
    IiaController iiaController;

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
        return iiaController.getIiasFor(heiId, iiaIdList);
    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public Response getPost(@FormParam("hei_id") String heiId, @FormParam("iia_id") List<String> iiaIdList) {
        return iiaController.getIiasFor(heiId, iiaIdList);
    }
}
