
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
                persistPerson("{'personId':'9001013344','firstNames':'Billy','lastName':'Thomas','birthDate':'1990-01-01'}");
                persistPerson("{'personId':'9011046365','firstNames':'Alice','lastName':'Moneypenny','birthDate':'1990-11-04'}");
                persistPerson("{'personId':'8704122398','firstNames':'Carl','lastName':'Simson','birthDate':'1987-04-12'}");
                persistPerson("{'personId':'8906093845','firstNames':'Mary','lastName':'Carter','birthDate':'1989-06-09'}");
                break;
            case POMODORO_U:
                persistPerson("{'personId':'8810126789','firstNames':'Ann','lastName':'White','birthDate':'1988-10-12'}");
                persistPerson("{'personId':'8712146574','firstNames':'Gregory','lastName':'Willis','birthDate':'1987-12-14'}");
                persistPerson("{'personId':'9003228402','firstNames':'Arnold','lastName':'Jones','birthDate':'1990-03-22'}");
                persistPerson("{'personId':'8602181287','firstNames':'Sharon','lastName':'Lopez','birthDate':'1986-02-18'}");
                break;
        }
    }

    private void persistPerson(String personJson) throws IOException {
        Person person = JsonHelper.mapToObject(Person.class, personJson);
        em.persist(person);
    }
}
