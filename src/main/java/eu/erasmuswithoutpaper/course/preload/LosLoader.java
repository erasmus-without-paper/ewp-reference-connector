
package eu.erasmuswithoutpaper.course.preload;

import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class LosLoader {
    public static final String IKEA_LOS1_ID = "8965F285-E763-IKEA-8163-C52C8B654033";
    
@PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        
        // Course
        String namesCourse1Ikea = "[{'text':'Analytisk kemi','lang':'sv'},{'text':'Analytical Chemistry','lang':'en'}]";
        String urlCourse1Ikea = "[{'text':'www.iu.se/analytiskkemi','lang':'sv'},{'text':'www.iu.se/analyticalchemistry','lang':'en'}]";
        persistLearningOppSpecFromJson("{'id':" + IKEA_LOS1_ID + ",'institutionId':'ikea.university.se','losCode':'IU001','type':'COURSE','name':" + namesCourse1Ikea + ",'url':" + urlCourse1Ikea + "}");
        
        // Course
        String namesCourse2Ikea = "[{'text':'Kärnfysik','lang':'sv'},{'text':'Nuclear physics','lang':'en'}]";
        String urlCourse2Ikea = "[{'text':'www.iu.se/karnfysik','lang':'sv'},{'text':'www.iu.se/nuclearphysics','lang':'en'}]";
        persistLearningOppSpecFromJson("{'institutionId':'ikea.university.se','losCode':'IU002','type':'COURSE','name':" + namesCourse2Ikea + ",'url':" + urlCourse2Ikea + "}");
        
        // Course
        String namesCourse3Ikea = "[{'text':'Bildbehandling','lang':'sv'},{'text':'Image Processing','lang':'en'}]";
        String urlCourse3Ikea = "[{'text':'www.iu.se/bildbehandling','lang':'sv'},{'text':'www.iu.se/imageprocessing','lang':'en'}]";
        persistLearningOppSpecFromJson("{'institutionId':'ikea.university.se','losCode':'IU003','type':'COURSE','name':" + namesCourse3Ikea + ",'url':" + urlCourse3Ikea + "}");
        
        // Course, part of a module
        String namesCourse4Ikea = "[{'text':'Java introduktion','lang':'sv'},{'text':'Java Introduction','lang':'en'}]";
        String urlCourse4Ikea = "[{'text':'www.iu.se/javaintroduktion','lang':'sv'},{'text':'www.iu.se/javaintroduction','lang':'en'}]";
        LearningOpportunitySpecification course4 = createLearningOppSpecFromJson("{'institutionId':'ikea.university.se','losCode':'IUJ04','type':'COURSE','name':" + namesCourse4Ikea + ",'url':" + urlCourse4Ikea + "}");
        
        // Class, part of a course
        String namesClass1Ikea = "[{'text':'Java intro del 1','lang':'sv'},{'text':'Java Intro part 1','lang':'en'}]";
        String urlClass1Ikea = "[{'text':'www.iu.se/javaintrodel1','lang':'sv'},{'text':'www.iu.se/javaintropart1','lang':'en'}]";
        LearningOpportunitySpecification class1 = createLearningOppSpecFromJson("{'institutionId':'ikea.university.se','losCode':'IUCL1','type':'CLASS','name':" + namesClass1Ikea + ",'url':" + urlClass1Ikea + "}");
        
        // Class, part of a course
        String namesClass2Ikea = "[{'text':'Java intro del 2','lang':'sv'},{'text':'Java Intro part 2','lang':'en'}]";
        String urlClass2Ikea = "[{'text':'www.iu.se/javaintrodel2','lang':'sv'},{'text':'www.iu.se/javaintropart2','lang':'en'}]";
        LearningOpportunitySpecification class2 = createLearningOppSpecFromJson("{'institutionId':'ikea.university.se','losCode':'IUCL2','type':'CLASS','name':" + namesClass2Ikea + ",'url':" + urlClass2Ikea + "}");
        
        // Course, part of a module
        String namesCourse5Ikea = "[{'text':'Java steg 2','lang':'sv'},{'text':'Java part 2','lang':'en'}]";
        String urlCourse5Ikea = "[{'text':'www.iu.se/javasteg2','lang':'sv'},{'text':'www.iu.se/javapart2','lang':'en'}]";
        LearningOpportunitySpecification course5 = createLearningOppSpecFromJson("{'institutionId':'ikea.university.se','losCode':'IUJ05','type':'COURSE','name':" + namesCourse5Ikea + ",'url':" + urlCourse5Ikea + "}");
        List<LearningOpportunitySpecification> courseClasses = new ArrayList<>();
        courseClasses.add(class1);
        courseClasses.add(class2);
        course5.setLearningOpportunitySpecifications(courseClasses);
        
        // Module
        String namesModuleIkea = "[{'text':'Java Programmering','lang':'sv'},{'text':'Java Programing','lang':'en'}]";
        String urlModuleIkea = "[{'text':'www.iu.se/java/se','lang':'sv'},{'text':'www.iu.se/java/en','lang':'en'}]";
        LearningOpportunitySpecification module = createLearningOppSpecFromJson("{'institutionId':'ikea.university.se','losCode':'MOD01','type':'MODULE','name':" + namesModuleIkea + ",'url':" + urlModuleIkea + "}");
        List<LearningOpportunitySpecification> moduleCourses = new ArrayList<>();
        moduleCourses.add(course4);
        moduleCourses.add(course5);
        module.setLearningOpportunitySpecifications(moduleCourses);
        persistLearningOppSpecFromObject(module);
    }
    
    public void createDemoDataPomodoro() throws IOException {
        //TODO create more data for Pomodoro
        
        // Course
        String namesCourse1Pomodoro = "[{'text':'Datainsamling och analys','lang':'sv'},{'text':'Data Collection and Analysis','lang':'en'}]";
        String urlCourse1Pomodoro = "[{'text':'www.pu.it/datainsamlinganalys','lang':'sv'},{'text':'www.pu.it/datacollectionanalysis','lang':'en'}]";
        persistLearningOppSpecFromJson("{'institutionId':'pomodoro.university.it','losCode':'PU001','type':'COURSE','name':" + namesCourse1Pomodoro + ",'url':" + urlCourse1Pomodoro + "}");
        
        // Course
        String namesCourse2Pomodoro = "[{'text':'Datormoln','lang':'sv'},{'text':'Cloud computing','lang':'en'}]";
        String urlCourse2Pomodoro = "[{'text':'www.pu.it/datormoln','lang':'sv'},{'text':'www.pu.it/cloudcomputing','lang':'en'}]";
        persistLearningOppSpecFromJson("{'institutionId':'pomodoro.university.it','losCode':'PU002','type':'COURSE','name':" + namesCourse2Pomodoro + ",'url':" + urlCourse2Pomodoro + "}");
    }
    
    private void persistLearningOppSpecFromJson(String losJson) throws IOException {
        LearningOpportunitySpecification learningOppSpec = JsonHelper.mapToObject(LearningOpportunitySpecification.class, losJson);
        learningOppSpec.setTopLevelParent(true);
        em.persist(learningOppSpec);
    }
    
    private void persistLearningOppSpecFromObject(LearningOpportunitySpecification los) throws IOException {
        los.setTopLevelParent(true);
        em.persist(los);
    }
    
    private LearningOpportunitySpecification createLearningOppSpecFromJson(String losJson) throws IOException {
        LearningOpportunitySpecification learningOppSpec = JsonHelper.mapToObject(LearningOpportunitySpecification.class, losJson);
        
        return learningOppSpec;
    }
}
