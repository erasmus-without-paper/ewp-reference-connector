package eu.erasmuswithoutpaper.internal;

import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.course.preload.AcademicTermLoader;
import eu.erasmuswithoutpaper.course.preload.AcademicYearLoader;
import eu.erasmuswithoutpaper.course.preload.LoiLoader;
import eu.erasmuswithoutpaper.course.preload.LosLoader;
import eu.erasmuswithoutpaper.iia.preload.IiaLoader;
import eu.erasmuswithoutpaper.iia.preload.MobilityTypeLoader;
import eu.erasmuswithoutpaper.mobility.preload.MobilityLoader;
import eu.erasmuswithoutpaper.organization.preload.ContactLoader;
import eu.erasmuswithoutpaper.organization.preload.InstitutionLoader;
import eu.erasmuswithoutpaper.organization.preload.MobilityParticipantLoader;
import eu.erasmuswithoutpaper.organization.preload.PersonLoader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartupLoader {

    @Inject
    private GlobalProperties properties;
    
    @Inject
    private InstitutionLoader institutionLoader;
    
    @Inject
    private PersonLoader personLoader;
    
    @Inject
    private ContactLoader contactLoader;

    @Inject
    MobilityParticipantLoader mobilityParticipantLoader;

    @Inject
    private AcademicTermLoader academicTermLoader;
    
    @Inject
    private AcademicYearLoader academicYearLoader;
    
    @Inject
    private MobilityTypeLoader mobilityTypeLoader;
   
    @Inject
    private MobilityLoader mobilityLoader;
    
    @Inject
    private LosLoader learningOppSpecLoader;

    @Inject
    private LoiLoader learningOppInstLoader;

    @Inject
    private IiaLoader iiaLoader;
    
    @PostConstruct
    public void loadDemoData() {
        try {
            if (institutionLoader.dataAlreadyExist()) {
                Logger.getLogger(StartupLoader.class.getName()).log(Level.INFO, "Database already exist, no default data will be loaded. Remove DB to restore database content.");
                return;
            } else {
                Logger.getLogger(StartupLoader.class.getName()).log(Level.INFO, "Database is created, default data will be loaded.");
            } 

            switch (properties.getUniversity()) {
                case IKEA_U:
                    institutionLoader.createDemoDataIkea();
                    personLoader.createDemoDataIkea();
                    contactLoader.createDemoDataIkea();
                    mobilityParticipantLoader.createDemoDataIkea();
                    academicYearLoader.createDemoDataIkea();
                    academicTermLoader.createDemoDataIkea();
                    mobilityTypeLoader.createDemoDataIkea();
                    learningOppSpecLoader.createDemoDataIkea();
                    learningOppInstLoader.createDemoDataIkea();
                    iiaLoader.createDemoDataIkea();
                    mobilityLoader.createDemoDataIkea();
                    break;
                case POMODORO_U:
                    institutionLoader.createDemoDataPomodoro();
                    personLoader.createDemoDataPomodoro();
                    contactLoader.createDemoDataPomodoro();
                    mobilityParticipantLoader.createDemoDataPomodoro();
                    academicYearLoader.createDemoDataPomodoro();
                    academicTermLoader.createDemoDataPomodoro();
                    mobilityTypeLoader.createDemoDataPomodoro();
                    learningOppSpecLoader.createDemoDataPomodoro();
                    learningOppInstLoader.createDemoDataPomodoro();
                    iiaLoader.createDemoDataPomodoro();
                    mobilityLoader.createDemoDataPomodoro();
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(StartupLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
