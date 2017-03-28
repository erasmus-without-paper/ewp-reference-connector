
package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Contact;
import eu.erasmuswithoutpaper.organization.entity.Person;
import java.io.IOException;
import java.util.List;
import javax.persistence.Query;

public class ContactLoader extends AbstractStartupLoader {
    
    @Override
    public void createDemoDataIkea() throws IOException {
        String ouIdIkea = InstitutionLoader.IKEA_OU1_ID ;
        persistContact("{'institutionId':'ikea.university.se','organizationUnitId':'" + ouIdIkea + "','role':'INSURANCE','contactDetails':{'email':['ikeauniversity@hei.ewp']}}", getPerson("9001013344"));
        persistContact("{'institutionId':'ikea.university.se','organizationUnitId':'" + ouIdIkea + "','role':'ADMISSION'}", getPerson("9011046365"));
        persistContact("{'institutionId':'ikea.university.se','role':'ADMISSION'}", getPerson("9107146991"));
        persistContact("{'institutionId':'ikea.university.se','organizationUnitId':'" + ouIdIkea + "','role':'COURSE'}", getPerson("8906093845"));
    }

    @Override
    public void createDemoDataPomodoro() throws IOException {
        String ouIdPomodoro = InstitutionLoader.POMODORO_OU1_ID;
        persistContact("{'institutionId':'pomodoro.university.it','organizationUnitId':'" + ouIdPomodoro + "','role':'COURSE'}", getPerson("8810126789"));
        persistContact("{'institutionId':'pomodoro.university.it','role':'ADMISSION'}", getPerson("8602181287"));
        persistContact("{'institutionId':'pomodoro.university.it','organizationUnitId':'" + ouIdPomodoro + "','role':'INSURANCE'}", getPerson("9104125620"));
    }

    private void persistContact(String contactJson, Person person) throws IOException {
        Contact contact = JsonHelper.mapToObject(Contact.class, contactJson);
        contact.setPerson(person);
        em.persist(contact);
    }
    
    private Person getPerson(String personId) throws IOException {
        Query query = em.createNamedQuery(Person.findByPersonId).setParameter("personId", personId);
        List<Person> personList = query.getResultList();
        if (personList.size() != 1) {
           throw new IllegalArgumentException("Person id " + personId + " doesn't return an unique person.");
        }
        
        return personList.get(0);
    }
}
