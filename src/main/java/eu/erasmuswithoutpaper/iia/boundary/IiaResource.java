package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("iias")
public class IiaResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    IiaConverter iiaConverter;
    
    @Context
    HttpServletRequest httpRequest;
    
    @GET
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexGet(@QueryParam("hei_id") String heiId) {
        return iiaIndex(heiId);
    }
    
    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexPost(@FormParam("hei_id") String heiId) {
        return iiaIndex(heiId);
    }
    
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response getGet(@QueryParam("hei_id") String heiId, @QueryParam("iia_id") List<String> iiaIdList) {
        return iiaGet(heiId, iiaIdList);
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response getPost(@FormParam("hei_id") String heiId, @FormParam("iia_id") List<String> iiaIdList) {
        return iiaGet(heiId, iiaIdList);
    }
    
    private javax.ws.rs.core.Response iiaGet(String heiId, List<String> iiaIdList) {
        if (iiaIdList.size() > properties.getMaxIiaIds()) {
            throw new EwpWebApplicationException("Max number of IIA id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        IiasGetResponse response = new IiasGetResponse();
        
        // TODO: Should IIA hold hei/institution id (if not hei_id will not be used, only use iia id)
        List<Iia> iiaList = iiaIdList.stream().map(id -> em.find(Iia.class, id)).filter(iia -> iia != null).collect(Collectors.toList());
        if (!iiaList.isEmpty()) {
            response.getIia().addAll(iiaConverter.convertToIias(iiaList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private boolean isInstitutionInEwp(String institutionId) {
        List<Institution> institutionList =  em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", institutionId).getResultList();
        return !institutionList.isEmpty();
    }

    private javax.ws.rs.core.Response iiaIndex(String heiId) {
        if (!isInstitutionInEwp(heiId)) {
            throw new EwpWebApplicationException("Not a valid hei_id.", Response.Status.BAD_REQUEST);
        }
        
        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }
        
        IiasIndexResponse response = new IiasIndexResponse();
        List<Iia> iiaList =  em.createNamedQuery(Iia.findAll).getResultList();
        if (!iiaList.isEmpty()) {
            response.getIiaId().addAll(iiaIds(iiaList, heisCoveredByCertificate));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private List<String> iiaIds(List<Iia> iiaList, Collection<String> heisCoveredByCertificate) {
        return iiaList.stream().filter((iia) -> {
            return iia.getCooperationConditions().stream().anyMatch(
                    cond -> heisCoveredByCertificate.contains(cond.getReceivingPartner().getInstitutionId()) || 
                            heisCoveredByCertificate.contains(cond.getSendingPartner().getInstitutionId()));
        }).map(iia -> iia.getId()).collect(Collectors.toList());
    }
}
