package eu.erasmuswithoutpaper.iias.control;

import eu.erasmuswithoutpaper.api.aiis.endpoints.Response;
import eu.erasmuswithoutpaper.api.aiis.endpoints.StringWithOptionalLang;
import eu.erasmuswithoutpaper.iias.entity.Condition;
import eu.erasmuswithoutpaper.iias.entity.Iia;
import eu.erasmuswithoutpaper.iias.entity.Term;
import eu.erasmuswithoutpaper.iias.entity.Title;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class IiaController {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public Response getIiasForHeisAndIds() {
        return new Response();
    }
    
    public Response getAllIias() {
        Query query = em.createQuery("select a from Iia a", eu.erasmuswithoutpaper.iias.entity.Iia.class);
        return createAndPopulateResponse(query);
    }
    
    public Response getIiasFor(String heiId, List<String> iiaIdList) {
        Query query = em.createQuery("select a from Iia a where a.partner.institutionId=:heiId and a.id in :iiaIdList", eu.erasmuswithoutpaper.iias.entity.Iia.class);
        query.setParameter("heiId", heiId);
        query.setParameter("iiaIdList", iiaIdList);
        return createAndPopulateResponse(query);
    }
    
    public void saveIia(Iia iia) {
        if (iia.getId() == null || iia.getId().isEmpty()) {
            UUID uniqueKey = UUID.randomUUID();
            iia.setId(uniqueKey.toString());
        }
        em.persist(iia);
    }
    
    private Response createAndPopulateResponse(Query query) {
        Response response = new Response();
        List<Iia> iiaEntityList = query.getResultList();
        for (Iia iiaEntity : iiaEntityList) {
            Response.Iia iia = new Response.Iia();
            iia.setId(iiaEntity.getId());
            
            Response.Iia.Partner partner = new Response.Iia.Partner();
            partner.setInstitutionId(iiaEntity.getPartner().getInstitutionId());
            partner.setOrganizationUnitId(iiaEntity.getPartner().getOrganizationUnitId());
            createAndPopulateConditions(partner, iiaEntity.getPartner().getCondition());
            
            iia.setPartner(partner);
            response.getIia().add(iia);
        }
        
        return response;
    }

    private void createAndPopulateConditions(Response.Iia.Partner partner, List<Condition> conditionEntities) {
        for (Condition conditionEntity : conditionEntities) {
            Response.Iia.Partner.Condition condition = new Response.Iia.Partner.Condition();
            condition.setSubjectArea(conditionEntity.getSubjectArea());
            for (String moblilityType : conditionEntity.getMobilityType()) {
                condition.getMobilityType().add(moblilityType);
            }
            createAndPopulateTerm(condition, conditionEntity.getTerm());
            
            partner.getCondition().add(condition);
        }
    }

    private void createAndPopulateTerm(Response.Iia.Partner.Condition condition, Term termEntity) {
        if (termEntity != null) {
            Response.Iia.Partner.Condition.Term term = new Response.Iia.Partner.Condition.Term();

            term.setEndDate(convertDateToXMLGregorianCalendar(termEntity.getEndDate()));
            term.setStartDate(convertDateToXMLGregorianCalendar(termEntity.getStartDate()));
            for (Title titleEntity : termEntity.getTitle()) {
                StringWithOptionalLang title = new StringWithOptionalLang();
                title.setLang(titleEntity.getLang());
                title.setValue(titleEntity.getTitle());
                term.getTitle().add(title);
            }
            condition.setTerm(term);
        }
    }
    
    private XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) {
        try {
            if (date != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(date);
                XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                return xmlGregorianCalendar;
            }
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(IiaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
