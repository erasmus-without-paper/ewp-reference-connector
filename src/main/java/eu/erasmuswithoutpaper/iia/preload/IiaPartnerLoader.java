
package eu.erasmuswithoutpaper.iia.preload;

import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Coordinator;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class IiaPartnerLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        persistIiaPartner("{'iiaId':'iiaId001','institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se'}", getCoordinators("ikea.university.se", "ikea.ou1.se"));
        persistIiaPartner("{'iiaId':'iiaId001','institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it'}", getCoordinators("'pomodoro.university.it", "pomodoro.ou1.it"));
    }
    public void createDemoDataPomodoro() throws IOException {
        persistIiaPartner("{'iiaId':'iiaId001','institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se'}", getCoordinators("ikea.university.se", "ikea.ou1.se"));
        persistIiaPartner("{'iiaId':'iiaId001','institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it'}", getCoordinators("'pomodoro.university.it", "pomodoro.ou1.it"));
    }

    private void persistIiaPartner(String iiaPartnerJson, List<Coordinator> coordinators) throws IOException {
        IiaPartner iiaPartner = JsonHelper.mapToObject(IiaPartner.class, iiaPartnerJson);
        iiaPartner.setCoordinators(coordinators);
        em.persist(iiaPartner);
    }
    
    private List<Coordinator> getCoordinators(String institutionId, String orgUnitId) throws IOException {
        Query query = em.createNamedQuery(Coordinator.findByInstAndOrgUnit).setParameter("institutionId", institutionId).setParameter("organizationUnitId", orgUnitId);
        List<Coordinator> coordinators = query.getResultList();

        return coordinators;
    }
}
