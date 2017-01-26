
package eu.erasmuswithoutpaper.iia.preload;

import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Contact;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import eu.erasmuswithoutpaper.organization.preload.InstitutionLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class IiaLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        persistIia("{'iiaCode':'IK-POM-01','startDate':'2016-01-01','endDate':'2017-01-01'}", getCooperationConditions());
    }
    
    public void createDemoDataPomodoro() throws IOException {
        createDemoDataIkea(); // Same data for both HEIs since they are partners in this IIA.
    }
    
    private void persistIia(String iiaJson, List<CooperationCondition> cooperationConditions) throws IOException {
        Iia iia = JsonHelper.mapToObject(Iia.class, iiaJson);
        iia.setCooperationConditions(cooperationConditions);
        em.persist(iia);
    }
    
    private List<CooperationCondition> getCooperationConditions() throws IOException {
        String durationIkeaToPomodoro = "{'unit':'Weeks','number':'20'}";
        String durationPomodoroToIkea = "{'unit':'Days','number':'11'}";
        String mobilityNumber_average_3 = "{'variant':'Average','number':'3'}";
        String mobilityNumber_total_8 = "{'variant':'Total','number':'8'}";
        List<CooperationCondition> conditions = new ArrayList<>();
        conditions.add(getCooperationCondition("{'startDate':'2016-01-01','endDate':'2016-06-29','mobilityNumber':" + mobilityNumber_average_3 + ",'duration':" + durationIkeaToPomodoro + ",'eqfLevel':'8'}", getMobilityType("Student", "Studies"), getIkeaIiaPartner(), getPomodoroIiaPartner()));
        conditions.add(getCooperationCondition("{'startDate':'2016-03-15','endDate':'2016-04-02','mobilityNumber':" + mobilityNumber_total_8 + ",'duration':" + durationPomodoroToIkea + ",'eqfLevel':'5'}", getMobilityType("Staff", "Training"), getPomodoroIiaPartner(), getIkeaIiaPartner()));
        return conditions;
    }
    
    private CooperationCondition getCooperationCondition(String cooperationConditionJson, MobilityType mobilityType, IiaPartner sendingPartner, IiaPartner receivingPartner) throws IOException {
        CooperationCondition cooperationCondition = JsonHelper.mapToObject(CooperationCondition.class, cooperationConditionJson);
        cooperationCondition.setSendingPartner(sendingPartner);
        cooperationCondition.setReceivingPartner(receivingPartner);
        cooperationCondition.setMobilityType(mobilityType);
        return cooperationCondition;
    }
    
    private MobilityType getMobilityType(String group, String category) throws IOException {
        Query query = em.createNamedQuery(MobilityType.findByGroupAndCategory).setParameter("mobilityGroup", group).setParameter("mobilityCategory", category);
        List<MobilityType> mobilityTypes = query.getResultList();
        if (mobilityTypes.size() != 1) {
           throw new IllegalArgumentException("Group " + group + " and category " + category + "doesn't return an unique mobilityType.");
        }
        
        return mobilityTypes.get(0);
    }

    private IiaPartner getIkeaIiaPartner() throws IOException {
        String ouId = InstitutionLoader.IKEA_OU1_ID;
        return getIiaPartner("{'institutionId':'ikea.university.se','organizationUnitId':'" + ouId + "'}", getContacts("ikea.university.se", ouId));
    }
    
    private IiaPartner getPomodoroIiaPartner() throws IOException {
        String ouId = InstitutionLoader.POMODORO_OU1_ID;
        return getIiaPartner("{'institutionId':'pomodoro.university.it','organizationUnitId':'" + ouId + "'}", getContacts("'pomodoro.university.it", ouId));
    }

    private IiaPartner getIiaPartner(String iiaPartnerJson, List<Contact> contacts) throws IOException {
        IiaPartner iiaPartner = JsonHelper.mapToObject(IiaPartner.class, iiaPartnerJson);
        iiaPartner.setContacts(contacts);
        return iiaPartner;
    }
    
    private List<Contact> getContacts(String institutionId, String orgUnitId) throws IOException {
        Query query = em.createNamedQuery(Contact.findByInstAndOrgUnit).setParameter("institutionId", institutionId).setParameter("organizationUnitId", orgUnitId);
        List<Contact> contacts = query.getResultList();

        return contacts;
    }
    
    private String getOrganizationUnitId(String organizationUnitCode) throws IOException {
        Query query = em.createNamedQuery(OrganizationUnit.findByOrganizationUnitCode).setParameter("organizationUnitCode", organizationUnitCode);
        List<OrganizationUnit> organizationUnitList = query.getResultList();
        if (organizationUnitList.size() != 1) {
           throw new IllegalArgumentException("Organization unit code " + organizationUnitCode + " doesn't return an unique organization unit.");
        }
        
        return organizationUnitList.get(0).getId();
    }
}
