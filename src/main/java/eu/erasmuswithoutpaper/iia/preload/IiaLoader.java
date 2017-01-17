
package eu.erasmuswithoutpaper.iia.preload;

import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Contact;
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
        persistIia("{'iiaId':'iiaId001','startDate':'2016-01-01','endDate':'2017-01-01'}", getCooperationConditions("iiaId001"));
    }
    
    public void createDemoDataPomodoro() throws IOException {
        //TODO Create data for Pomodoro
        createDemoDataIkea();
    }
    
    private void persistIia(String iiaJson, List<CooperationCondition> cooperationConditions) throws IOException {
        Iia iia = JsonHelper.mapToObject(Iia.class, iiaJson);
        iia.setCooperationConditions(cooperationConditions);
        em.persist(iia);
    }
    
    private List<CooperationCondition> getCooperationConditions(String iiaId) throws IOException {
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
        return getIiaPartner("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se'}", getContacts("ikea.university.se", "ikea.ou1.se"));
    }
    private IiaPartner getPomodoroIiaPartner() throws IOException {
        return getIiaPartner("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it'}", getContacts("'pomodoro.university.it", "pomodoro.ou1.it"));
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
    
}
