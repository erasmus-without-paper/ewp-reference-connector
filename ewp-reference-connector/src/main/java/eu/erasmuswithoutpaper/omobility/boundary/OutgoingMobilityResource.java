package eu.erasmuswithoutpaper.omobility.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.omobilities.cnr.ObjectFactory;
import eu.erasmuswithoutpaper.api.omobilities.endpoints.ApprovingParty;
import eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesGetResponse;
import eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesIndexResponse;
import eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesUpdateRequest;
import eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesUpdateResponse;
import eu.erasmuswithoutpaper.api.omobilities.endpoints.StudentMobilityForStudies;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import eu.erasmuswithoutpaper.notification.entity.NotificationTypes;
import eu.erasmuswithoutpaper.omobility.control.OutgoingMobilityConverter;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;
import eu.erasmuswithoutpaper.omobility.entity.MobilityUpdateRequest;
import eu.erasmuswithoutpaper.omobility.entity.MobilityUpdateRequestType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("omobilities")
public class OutgoingMobilityResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    OutgoingMobilityConverter mobilityConverter;
    
    @GET
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityIndexGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("receiving_hei_id") List<String> receivingHeiIdList, 
            @QueryParam("planned_arrival_after") String plannedArrivalAfterDate) {
        return mobilityIndex(sendingHeiId, receivingHeiIdList, plannedArrivalAfterDate);
    }
    
    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityIndexPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("receiving_hei_id") List<String> receivingHeiIdList, 
            @FormParam("planned_arrival_after") String plannedArrivalAfterDate) {
        return mobilityIndex(sendingHeiId, receivingHeiIdList, plannedArrivalAfterDate);
    }
    
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityGetGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityGetPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }

    @POST
    @Path("update")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityUpdatePost(OmobilitiesUpdateRequest request) {
        if (request == null) {
            throw new EwpWebApplicationException("No update data was sent", Response.Status.BAD_REQUEST);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            MobilityUpdateRequestType type = MobilityUpdateRequestType.APPROVE_COMPONENTS_STUDIED_DRAFT_V1;
            String updateInformation = null;
            if (request.getApproveComponentsStudiedDraftV1() != null) {
                type = MobilityUpdateRequestType.APPROVE_COMPONENTS_STUDIED_DRAFT_V1;
                updateInformation = mapper.writeValueAsString(request.getApproveComponentsStudiedDraftV1());
            } else if (request.getUpdateComponentsStudiedV1()!= null) {
                type = MobilityUpdateRequestType.UPDATE_COMPONENTS_STUDIED_V1;
                updateInformation = mapper.writeValueAsString(request.getUpdateComponentsStudiedV1());
            } else if (request.getUpdateStatusesV1() != null) {
                type = MobilityUpdateRequestType.UPDATE_STATUSES_V1;
                updateInformation = mapper.writeValueAsString(request.getUpdateStatusesV1());
            }
            
            MobilityUpdateRequest mobilityUpdateRequest = new MobilityUpdateRequest();
            mobilityUpdateRequest.setType(type);
            mobilityUpdateRequest.setSendingHeiId(request.getSendingHeiId());
            mobilityUpdateRequest.setUpdateRequestDate(new Date());
            mobilityUpdateRequest.setUpdateInformation(updateInformation);
            em.persist(mobilityUpdateRequest);
        } catch (JsonProcessingException ex) {
            throw new EwpWebApplicationException("Unexpected error", Response.Status.INTERNAL_SERVER_ERROR);
        }

        OmobilitiesUpdateResponse response = new OmobilitiesUpdateResponse();
        MultilineStringWithOptionalLang message = new MultilineStringWithOptionalLang();
        message.setLang("en");
        message.setValue("Thank you! We will review your suggestion");
        response.getSuccessUserMessage().add(message);
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    @POST
    @Path("cnr")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response cnrPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> omobilityId) {
        if (sendingHeiId == null || sendingHeiId.isEmpty() || omobilityId.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for notification.", Response.Status.BAD_REQUEST);
        }
        Notification notification = new Notification();
        notification.setType(NotificationTypes.OMOBILITY);
        notification.setHeiId(sendingHeiId);
        notification.setChangedElementIds(String.join(", ", omobilityId));
        notification.setNotificationDate(new Date());
        em.persist(notification);
         
        return javax.ws.rs.core.Response.ok(new ObjectFactory().createOmobilityCnrResponse(new Empty())).build();
    }
   
    private javax.ws.rs.core.Response mobilityGet(String sendingHeiId, List<String> mobilityIdList) {
        if (mobilityIdList.size() > properties.getMaxMobilityIds()) {
            throw new EwpWebApplicationException("Max number of mobility id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        OmobilitiesGetResponse response = new OmobilitiesGetResponse();
        List<Mobility> mobilityList =  em.createNamedQuery(Mobility.findBySendingInstitutionId).setParameter("sendingInstitutionId", sendingHeiId).getResultList();
        if (!mobilityList.isEmpty()) {
            response.getSingleMobilityObject().addAll(mobilities(mobilityList, mobilityIdList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private javax.ws.rs.core.Response mobilityIndex(String sendingHeiId, List<String> receivingHeiIdList, String plannedArrivalAfterDate) {
        OmobilitiesIndexResponse response = new OmobilitiesIndexResponse();
        List<Mobility> mobilityList =  em.createNamedQuery(Mobility.findBySendingInstitutionId).setParameter("sendingInstitutionId", sendingHeiId).getResultList();
        if (!mobilityList.isEmpty()) {
            response.getOmobilityId().addAll(mobilityIds(mobilityList, receivingHeiIdList, plannedArrivalAfterDate));
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
    
    private List<String> mobilityIds(List<Mobility> mobilityList, List<String> receivingHeiIdList, String plannedArrivalAfterDate) {
        List<String> mobilityIds = new ArrayList<>();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        mobilityList.stream().filter((m) -> {
            if (plannedArrivalAfterDate == null || plannedArrivalAfterDate.isEmpty()) {
                return true;
            }
            String plannedArrivalDate = sdf.format(m.getPlannedArrivalDate());
            return plannedArrivalDate.compareTo(plannedArrivalAfterDate) > 0;
        }).forEachOrdered((m) -> {
            if (receivingHeiIdList.isEmpty() || receivingHeiIdList.contains(m.getReceivingInstitutionId())) {
                mobilityIds.add(m.getId());
            }
        });
        
        return mobilityIds;
    }
}
