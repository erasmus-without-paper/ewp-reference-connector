package eu.erasmuswithoutpaper.internal;

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

    public enum University {
        IKEA_U,
        POMODORO_U
    }
    
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

            String ewpHost = System.getProperty("ewp.host");
            if (ewpHost == null || ewpHost.equals("IKEA")) {
                institutionLoader.createDemoData(University.IKEA_U);
                personLoader.createDemoData(University.IKEA_U);
                coordinatorLoader.createDemoData(University.IKEA_U);
            } else {
                institutionLoader.createDemoData(University.POMODORO_U);
                personLoader.createDemoData(University.POMODORO_U);
                coordinatorLoader.createDemoData(University.POMODORO_U);
            }
        } catch (IOException ex) {
            Logger.getLogger(StartupLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
