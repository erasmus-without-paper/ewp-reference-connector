package eu.erasmuswithoutpaper.iias.control;

import eu.erasmuswithoutpaper.api.aiis.endpoints.Response;
import eu.erasmuswithoutpaper.iias.entity.Condition;
import eu.erasmuswithoutpaper.iias.entity.Partner;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class IiaController {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public Response getAllIias() {
        Response response = new Response();
        List<eu.erasmuswithoutpaper.iias.entity.Iia> list = em.createQuery("select a from Iia a", eu.erasmuswithoutpaper.iias.entity.Iia.class).getResultList();
        list.stream().map((i) -> {
            Response.Iia iia = new Response.Iia();
            iia.setId(i.getIid());
            iia.setPartner(convertPartner(i.getPartner()));
            return iia;
        }).forEach((iia) -> {
            response.getIia().add(iia);
        });
        
        return response;
    }
    
    public Response getIiasForHeisAndIds() {
        return new Response();
    }
    
    private Response.Iia.Partner convertPartner(Partner partner) {
        Response.Iia.Partner p = new Response.Iia.Partner();
        p.setInstitutionId(partner.getInstitutionId());
        p.setOrganizationUnitId(partner.getOrganizationUnitId());
        
        for (Condition c : partner.getCondition()) {
            Response.Iia.Partner.Condition condition = new Response.Iia.Partner.Condition();
            condition.setSubjectArea(c.getSubjectArea());
            p.getCondition().add(condition);
        }
        /**
        partner.getCondition().stream().map((c) -> {
            Response.Iia.Partner.Condition condition = new Response.Iia.Partner.Condition();
            condition.setSubjectArea(c.getSubjectArea());
            return condition;
        }).forEach((condition) -> {
            p.getCondition().add(condition);
        });
        */
        return p;
    }
}
