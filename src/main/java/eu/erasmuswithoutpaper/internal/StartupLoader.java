package eu.erasmuswithoutpaper.internal;

import eu.erasmuswithoutpaper.course.preload.AcademicTermLoader;
import eu.erasmuswithoutpaper.course.preload.AcademicYearLoader;
import eu.erasmuswithoutpaper.course.preload.LoiLoader;
import eu.erasmuswithoutpaper.course.preload.LosLoader;
import eu.erasmuswithoutpaper.internal.control.GlobalPropertiesController;
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
    private GlobalPropertiesController properties;
    
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
    
    @PostConstruct
    public void loadDemoData() {
        try {
            if (institutionLoader.dataAlreadyExist()) {
                Logger.getLogger(StartupLoader.class.getName()).log(Level.INFO, "Database already exist, no default data will be loaded. Remove DB to restore database content.");
                return;
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
                    break;
                case POMODORO_U:
                    institutionLoader.createDemoDataPomodoro();
                    personLoader.createDemoDataPomodoro();
                    coordinatorLoader.createDemoDataPomodoro();
                    academicYearLoader.createDemoDataPomodoro();
                    academicTermLoader.createDemoDataPomodoro();
                    learningOppSpecLoader.createDemoDataPomodoro();
                    learningOppInstLoader.createDemoDataPomodoro();
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(StartupLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
