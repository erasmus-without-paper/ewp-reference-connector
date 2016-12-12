
package eu.erasmuswithoutpaper.iia.preload;

import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CooperationConditionLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
//        String durationIkeaToPomodoro = "{'unit':'WEEKS','number':'20'}";
//        String durationPomodoroToIkea = "{'unit':'DAYS','number':'11'}";
//        persistCooperationCondition("{'iiaId':'iiaId001','mobilityType':'STUDENT_STUDIES','startDate':'2016-01-01','endDate':'2016-06-29','mobilityNumberVariant':'TOTAL','duration':" + durationIkeaToPomodoro + ",'eqfLevel':'8'}", getIiaPartner("iiaId001", "ikea.university.se", "ikea.ou1.se"), getIiaPartner("iiaId001", "pomodoro.university.it", "pomodoro.ou1.it"));
//        persistCooperationCondition("{'iiaId':'iiaId001','mobilityType':'STAFF_TRAINING','startDate':'2016-03-15','endDate':'2016-04-02','mobilityNumberVariant':'AVERAGE','duration':" + durationPomodoroToIkea + ",'eqfLevel':'5'}", getIiaPartner("iiaId001", "pomodoro.university.it", "pomodoro.ou1.it"), getIiaPartner("iiaId001", "ikea.university.se", "ikea.ou1.se"));
        persistCooperationCondition("{'iiaId':'iiaId001','mobilityType':'STUDENT_STUDIES','startDate':'2016-01-01','endDate':'2016-06-29','mobilityNumberVariant':'TOTAL','duration':'3','eqfLevel':'8'}", getIiaPartner("iiaId001", "ikea.university.se", "ikea.ou1.se"), getIiaPartner("iiaId001", "pomodoro.university.it", "pomodoro.ou1.it"));
        persistCooperationCondition("{'iiaId':'iiaId001','mobilityType':'STAFF_TRAINING','startDate':'2016-03-15','endDate':'2016-04-02','mobilityNumberVariant':'AVERAGE','duration':'4','eqfLevel':'5'}", getIiaPartner("iiaId001", "pomodoro.university.it", "pomodoro.ou1.it"), getIiaPartner("iiaId001", "ikea.university.se", "ikea.ou1.se"));
    }
    
    public void createDemoDataPomodoro() throws IOException {
        //TODO create data for Pomodoro
        createDemoDataIkea();
    }
    
    private void persistCooperationCondition(String cooperationConditionJson, IiaPartner sendingPartner, IiaPartner receivingPartner) throws IOException {
        CooperationCondition cooperationCondition = JsonHelper.mapToObject(CooperationCondition.class, cooperationConditionJson);
        cooperationCondition.setSendingPartner(sendingPartner);
        cooperationCondition.setReceivingPartner(receivingPartner);
        em.persist(cooperationCondition);
    }
    
    private IiaPartner getIiaPartner(String iiaId, String institutionId, String orgUnitId) throws IOException {
        Query query = em.createNamedQuery(IiaPartner.findByIiaIdAndInstAndOrgUnit).setParameter("iiaId", iiaId).setParameter("institutionId", institutionId).setParameter("organizationUnitId", orgUnitId);
        List<IiaPartner> iiaPartners = query.getResultList();
        if (iiaPartners.size() != 1) {
           throw new IllegalArgumentException("IiaPartner " + iiaId + " " + institutionId + " " + orgUnitId + "doesn't return an unique IiaPartner.");
        }
        
        return iiaPartners.get(0);
    }
}