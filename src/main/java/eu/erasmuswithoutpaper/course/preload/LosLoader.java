
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
        String urlLosIkea1 = "[{'text':'www.iu.se/analytiskkemi','lang':'sv'},{'text':'www.iu.se/analyticalchemistry','lang':'en'}]";
        String namesLosIkea2 = "[{'text':'Kärnfysik','lang':'sv'},{'text':'Nuclear physics','lang':'en'}]";
        String urlLosIkea2 = "[{'text':'www.iu.se/karnfysik','lang':'sv'},{'text':'www.iu.se/nuclearphysics','lang':'en'}]";
        String namesLosIkea3 = "[{'text':'Bildbehandling','lang':'sv'},{'text':'Image Processing','lang':'en'}]";
        String urlLosIkea3 = "[{'text':'www.iu.se/bildbehandling','lang':'sv'},{'text':'www.iu.se/imageprocessing','lang':'en'}]";
        persistLearningOppSpec("{'institutionId':'ikea.university.se','losCode':'IU001','type':'COURSE','name':" + namesLosIkea1 + ",'description':" + urlLosIkea1 + "}");
        persistLearningOppSpec("{'institutionId':'ikea.university.se','losCode':'IU002','type':'COURSE','name':" + namesLosIkea2 + ",'description':" + urlLosIkea2 + "}");
        persistLearningOppSpec("{'institutionId':'ikea.university.se','losCode':'IU003','type':'COURSE','name':" + namesLosIkea3 + ",'description':" + urlLosIkea3 + "}");
    }
    
    public void createDemoDataPomodoro() throws IOException {
        String namesLosPomodoro1 = "[{'text':'Datainsamling och analys','lang':'sv'},{'text':'Data Collection and Analysis','lang':'en'}]";
        String urlLosPomodoro1 = "[{'text':'www.pu.it/datainsamlinganalys','lang':'sv'},{'text':'www.pu.it/datacollectionanalysis','lang':'en'}]";
        String namesLosPomodoro2 = "[{'text':'Datormoln','lang':'sv'},{'text':'Cloud computing','lang':'en'}]";
        String urlLosPomodoro2 = "[{'text':'www.pu.it/datormoln','lang':'sv'},{'text':'www.pu.it/cloudcomputing','lang':'en'}]";
        persistLearningOppSpec("{'institutionId':'pomodoro.university.it','losCode':'PU001','type':'COURSE','name':" + namesLosPomodoro1 + ",'description':" + urlLosPomodoro1 + "}");
        persistLearningOppSpec("{'institutionId':'pomodoro.university.it','losCode':'PU002','type':'COURSE','name':" + namesLosPomodoro2 + ",'description':" + urlLosPomodoro2 + "}");
    }
    
    private void persistLearningOppSpec(String losJson) throws IOException {
        LearningOpportunitySpecification learningOppSpec = JsonHelper.mapToObject(LearningOpportunitySpecification.class, losJson);
        em.persist(learningOppSpec);
    }
}
