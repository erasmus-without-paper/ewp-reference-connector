
package eu.erasmuswithoutpaper.iia.preload;

import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
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
        Query query = em.createNamedQuery(CooperationCondition.findByIiaId).setParameter("iiaId", iiaId);
        List<CooperationCondition> cooperationConditions = query.getResultList();

        return cooperationConditions;
    }
}
