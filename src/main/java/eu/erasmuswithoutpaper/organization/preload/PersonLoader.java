
package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.internal.StartupLoader;
import eu.erasmuswithoutpaper.organization.entity.Person;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PersonLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoData(StartupLoader.University university) throws IOException {
        switch (university) {
            case IKEA_U:
                persistPerson("{'personId':'9001013344','firstNames':'Billy','lastName':'Boy','birthDate':'1990-01-01'}");
                persistPerson("{'personId':'9011046365','firstNames':'Alice','lastName':'Moneypenny','birthDate':'1990-11-04'}");
                break;
            case POMODORO_U:
                persistPerson("{'personId':'8810126789','firstNames':'Ann','lastName':'White','birthDate':'1988-10-12'}");
                persistPerson("{'personId':'8712146574','firstNames':'Gregory','lastName':'Willis','birthDate':'1987-12-14'}");
                break;
        }
    }

    private void persistPerson(String personJson) throws IOException {
        Person person = JsonHelper.mapToObject(Person.class, personJson);
        em.persist(person);
    }
}
