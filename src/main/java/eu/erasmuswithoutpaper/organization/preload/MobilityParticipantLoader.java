
package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.MobilityParticipant;
import eu.erasmuswithoutpaper.organization.entity.Person;
import java.io.IOException;
import java.util.List;
import javax.persistence.Query;

public class MobilityParticipantLoader extends AbstractStartupLoader {
    public static final String IKEA_MOBILITY_PARTICIPANT1_ID = "8965F285-E763-IKEA-STUD-C52C8B654030";

    @Override
    public void createDemoDataIkea() throws IOException {
        
        //IKEA
        persistMobilityParticipant("{'id':'" + IKEA_MOBILITY_PARTICIPANT1_ID + "','contactDetails':{streetAddress:{addressLine:['Student street 53']},phoneNumber:{e164:'+4679123456'},email:['kate@mymail.com']}}", getPerson("9011046365"));

        //Pomodoro
    }

    @Override
    public void createDemoDataPomodoro() throws IOException {
    }
    
    private void persistMobilityParticipant(String mobilityParticipantJson, Person person) throws IOException {
        MobilityParticipant mobilityParticipant = JsonHelper.mapToObject(MobilityParticipant.class, mobilityParticipantJson);
        mobilityParticipant.setPerson(person);
        em.persist(mobilityParticipant);
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
