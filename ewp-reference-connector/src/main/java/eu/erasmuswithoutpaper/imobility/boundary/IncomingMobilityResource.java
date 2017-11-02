package eu.erasmuswithoutpaper.imobility.boundary;

import eu.erasmuswithoutpaper.api.imobilities.endpoints.ImobilitiesGetResponse;
import eu.erasmuswithoutpaper.api.imobilities.endpoints.StudentMobilityForStudies;
import eu.erasmuswithoutpaper.api.imobilities.tors.endpoints.ImobilityTorsGetResponse;
import eu.erasmuswithoutpaper.api.imobilities.tors.endpoints.ImobilityTorsIndexResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;
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
@Path("imobilities")
public class IncomingMobilityResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    IncomingMobilityConverter mobilityConverter;
    
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityGetGet(@QueryParam("receiving_hei_id") String receivingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(receivingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityGetPost(@FormParam("receiving_hei_id") String receivingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(receivingHeiId, mobilityIdList);
    }
    
    @GET
    @Path("tors/get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityTorsGetGet(@QueryParam("receiving_hei_id") String receivingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityTorsGet(receivingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("tors/get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityTorsGetPost(@FormParam("receiving_hei_id") String receivingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityTorsGet(receivingHeiId, mobilityIdList);
    }

    @GET
    @Path("tors/index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityTorsIndexGet(@QueryParam("receiving_hei_id") String receivingHeiId, @QueryParam("sending_hei_id") List<String> sendingHeiIdList) {
        return mobilityTorsIndex(receivingHeiId, sendingHeiIdList);
    }
    
    @POST
    @Path("tors/index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityTorsIndexPost(@FormParam("receiving_hei_id") String receivingHeiId, @FormParam("sending_hei_id") List<String> sendingHeiIdList) {
        return mobilityTorsIndex(receivingHeiId, sendingHeiIdList);
    }
    
    private javax.ws.rs.core.Response mobilityTorsGet(String receivingHeiId, List<String> mobilityIdList) {
        if (mobilityIdList.size() > properties.getMaxMobilityIds()) {
            throw new EwpWebApplicationException("Max number of mobility id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        ImobilityTorsGetResponse response = new ImobilityTorsGetResponse();
        List<Mobility> mobilityList =  em.createNamedQuery(Mobility.findByReceivingInstitutionId).setParameter("receivingInstitutionId", receivingHeiId).getResultList();
        mobilityList.stream().forEachOrdered((m) -> {
            if (mobilityIdList.contains(m.getId())) {
                response.getTor().add(mobilityConverter.convertToTor(m));
            }
        });
        
        return javax.ws.rs.core.Response.ok(response).build();
    }    

    private Response mobilityTorsIndex(String receivingHeiId, List<String> sendingHeiIdList) {
        List<Mobility> mobilityList =  em.createNamedQuery(Mobility.findByReceivingInstitutionId).setParameter("receivingInstitutionId", receivingHeiId).getResultList();
        
        ImobilityTorsIndexResponse response = new ImobilityTorsIndexResponse();
        mobilityList.stream().forEachOrdered((m) -> {
            if (sendingHeiIdList.isEmpty() || sendingHeiIdList.contains(m.getSendingInstitutionId())) {
                response.getOmobilityId().add(m.getId());
            }
        });
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private javax.ws.rs.core.Response mobilityGet(String receivingHeiId, List<String> mobilityIdList) {
        if (mobilityIdList.size() > properties.getMaxMobilityIds()) {
            throw new EwpWebApplicationException("Max number of mobility id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        ImobilitiesGetResponse response = new ImobilitiesGetResponse();
        List<Mobility> mobilityList =  em.createNamedQuery(Mobility.findByReceivingInstitutionId).setParameter("receivingInstitutionId", receivingHeiId).getResultList();
        if (!mobilityList.isEmpty()) {
            response.getSingleIncomingMobilityObject().addAll(mobilities(mobilityList, mobilityIdList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private List<StudentMobilityForStudies> mobilities(List<Mobility> mobilityList, List<String> mobilityIdList) {
        List<StudentMobilityForStudies> mobilities = new ArrayList<>();
        mobilityList.stream().forEachOrdered((m) -> {
            if (mobilityIdList.contains(m.getId())) {
                mobilities.add(mobilityConverter.convertToStudentMobilityForStudies(m));
            }
        });
        
        return mobilities;
    }
}
