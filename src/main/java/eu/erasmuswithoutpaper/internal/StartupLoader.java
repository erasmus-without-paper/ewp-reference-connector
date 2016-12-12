package eu.erasmuswithoutpaper.internal;

import eu.erasmuswithoutpaper.course.preload.AcademicTermLoader;
import eu.erasmuswithoutpaper.course.preload.AcademicYearLoader;
import eu.erasmuswithoutpaper.course.preload.LoiLoader;
import eu.erasmuswithoutpaper.course.preload.LosLoader;
import eu.erasmuswithoutpaper.iia.preload.CooperationConditionLoader;
import eu.erasmuswithoutpaper.iia.preload.IiaLoader;
import eu.erasmuswithoutpaper.iia.preload.IiaPartnerLoader;
import eu.erasmuswithoutpaper.internal.control.GlobalProperties;
import eu.erasmuswithoutpaper.organization.preload.CoordinatorLoader;
import eu.erasmuswithoutpaper.organization.preload.InstitutionLoader;
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
    private CoordinatorLoader coordinatorLoader;

    @Inject
    private AcademicTermLoader academicTermLoader;
    
    @Inject
    private AcademicYearLoader academicYearLoader;
    
    @Inject
    private LosLoader learningOppSpecLoader;

    @Inject
    private LoiLoader learningOppInstLoader;

    @Inject
    private IiaPartnerLoader iiaPartnerLoader;
    
    @Inject
    private CooperationConditionLoader cooperationConditionLoader;
    
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
                    coordinatorLoader.createDemoDataIkea();
                    academicYearLoader.createDemoDataIkea();
                    academicTermLoader.createDemoDataIkea();
                    learningOppSpecLoader.createDemoDataIkea();
                    learningOppInstLoader.createDemoDataIkea();
                    iiaPartnerLoader.createDemoDataIkea();
                    cooperationConditionLoader.createDemoDataIkea();
                    iiaLoader.createDemoDataIkea();
                    break;
                case POMODORO_U:
                    institutionLoader.createDemoDataPomodoro();
                    personLoader.createDemoDataPomodoro();
                    coordinatorLoader.createDemoDataPomodoro();
                    academicYearLoader.createDemoDataPomodoro();
                    academicTermLoader.createDemoDataPomodoro();
                    learningOppSpecLoader.createDemoDataPomodoro();
                    learningOppInstLoader.createDemoDataPomodoro();
                    iiaPartnerLoader.createDemoDataPomodoro();
                    cooperationConditionLoader.createDemoDataPomodoro();
                    iiaLoader.createDemoDataPomodoro();
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(StartupLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
