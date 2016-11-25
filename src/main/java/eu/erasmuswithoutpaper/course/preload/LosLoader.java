
package eu.erasmuswithoutpaper.course.preload;

import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class LosLoader {
@PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        String namesLosIkea1 = "[{'text':'Analytisk kemi','lang':'sv'},{'text':'Analytical Chemistry','lang':'en'}]";
        String descLosIkea1 = "[{'text':'Kursens mål är att ge den studerande ett solitt fundament i analytisk kemi.','lang':'sv'},{'text':'The aim of the course is to give the student a solid fundament in analytical chemistry.','lang':'en'}]";
        String namesLosIkea2 = "[{'text':'Kärnfysik','lang':'sv'},{'text':'Nuclear physics','lang':'en'}]";
        String descLosIkea2 = "[{'text':'Kursens ger grundliga teoretiska och experimentella kunskaper om atomkärnans egenskaper.','lang':'sv'},{'text':'The course gives thorough theoretical and experimental knowledge on atomic nucleus.','lang':'en'}]";
        String namesLosIkea3 = "[{'text':'Bildbehandling','lang':'sv'},{'text':'Image Processing','lang':'en'}]";
        String descLosIkea3 = "[{'text':'I kursen ges en systematisk framställning av metoder och verktyg för digital bildbehandling.','lang':'sv'},{'text':'The aim of the course is that students achieve knowledge and skills in digital image processing.','lang':'en'}]";
        persistLearningOppSpec("{'institutionId':'ikea.university.se','losCode':'IU001','type':'COURSE','name':" + namesLosIkea1 + ",'description':" + descLosIkea1 + "}");
        persistLearningOppSpec("{'institutionId':'ikea.university.se','losCode':'IU002','type':'COURSE','name':" + namesLosIkea2 + ",'description':" + descLosIkea2 + "}");
        persistLearningOppSpec("{'institutionId':'ikea.university.se','losCode':'IU003','type':'COURSE','name':" + namesLosIkea3 + ",'description':" + descLosIkea3 + "}");
    }
    
    public void createDemoDataPomodoro() throws IOException {
        String namesLosPomodoro1 = "[{'text':'Datainsamling och analys','lang':'sv'},{'text':'Data Collection and Analysis','lang':'en'}]";
        String descLosPomodoro1 = "[{'text':'Av forskningsprocessens fyra övergripande steg behandlar denna kurs de två sista stegen, nämligen att samla in forskningsdata och analysera data.','lang':'sv'},{'text':'Among the four overbridging steps in the research process, the last two will be taken in this course, namely, collecting data and analyzing the data.','lang':'en'}]";
        String namesLosPomodoro2 = "[{'text':'Datormoln','lang':'sv'},{'text':'Cloud computing','lang':'en'}]";
        String descLosPomodoro2 = "[{'text':'Grunderna inom virtualiseringsteknik och hur denna används till att bygga moln.','lang':'sv'},{'text':'Fundamentals of infrastructure virtualization and its use in building clouds.','lang':'en'}]";
        persistLearningOppSpec("{'institutionId':'pomodoro.university.it','losCode':'PU001','type':'COURSE','name':" + namesLosPomodoro1 + ",'description':" + descLosPomodoro1 + "}");
        persistLearningOppSpec("{'institutionId':'pomodoro.university.it','losCode':'PU002','type':'COURSE','name':" + namesLosPomodoro2 + ",'description':" + descLosPomodoro2 + "}");
    }
    
    private void persistLearningOppSpec(String losJson) throws IOException {
        LearningOpportunitySpecification learningOppSpec = JsonHelper.mapToObject(LearningOpportunitySpecification.class, losJson);
        em.persist(learningOppSpec);
    }
}
