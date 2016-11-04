package eu.erasmuswithoutpaper.internal;

import eu.erasmuswithoutpaper.organization.preload.InstitutionLoader;
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
    
    @PostConstruct
    public void loadDemoData() {
        try {
            String ewpHost = System.getProperty("ewp.host");
            if (ewpHost == null || ewpHost.equals("IKEA")) {
                    institutionLoader.createDemoData(University.IKEA_U);

            } else {
                institutionLoader.createDemoData(University.POMODORO_U);
            }
        } catch (IOException ex) {
            Logger.getLogger(StartupLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
