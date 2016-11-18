
package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.internal.StartupLoader;
import eu.erasmuswithoutpaper.organization.entity.Coordinator;
import eu.erasmuswithoutpaper.organization.entity.Person;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CoordinatorLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoData(StartupLoader.University university) throws IOException {
        switch (university) {
            case IKEA_U:
                persistCoordinator("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','header':'INSURANCE'}", getPerson("9001013344"));
                persistCoordinator("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou2.se','header':'COURSE'}", getPerson("8906093845"));
                break;
            case POMODORO_U:
                persistCoordinator("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','header':'COURSE'}", getPerson("8810126789"));
                persistCoordinator("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','header':'ADMISSION'}", getPerson("8602181287"));
                break;
        }
    }

    private void persistCoordinator(String coordinatorJson, Person person) throws IOException {
        Coordinator coordinator = JsonHelper.mapToObject(Coordinator.class, coordinatorJson);
        coordinator.setPerson(person);
        em.persist(coordinator);
    }
    
    private Person getPerson(String personId) throws IOException {
        Query query = em.createQuery("select a from Person a where a.personId=:personId", Person.class).setParameter("personId", personId);
        List<Person> personList = query.getResultList();
        if (personList.size() != 1) {
           throw new IllegalArgumentException("Person id " + personId + " doesn't return an unique person.");
        }
        
        return personList.get(0);
    }
}
