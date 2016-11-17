package eu.erasmuswithoutpaper.internal;

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
                    break;
                case POMODORO_U:
                    institutionLoader.createDemoDataPomdoro();
                    personLoader.createDemoDataPomdoro();
                    coordinatorLoader.createDemoDataPomdoro();
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(StartupLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
