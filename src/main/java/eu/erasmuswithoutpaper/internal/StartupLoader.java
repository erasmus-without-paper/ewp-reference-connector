package eu.erasmuswithoutpaper.internal;

import eu.erasmuswithoutpaper.department.preload.DepartmentLoader;
import eu.erasmuswithoutpaper.iias.preload.IiaLoader;
import eu.erasmuswithoutpaper.institutions.preload.InstitutionLoader;
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
    private IiaLoader iiaLoader;
   
    @Inject
    private DepartmentLoader departmentLoader;
    
    @PostConstruct
    public void loadDemoData() {
        String ewpHost = System.getProperty("ewp.host");
        if (ewpHost == null || ewpHost.equals("IKEA")) {
            institutionLoader.createDemoData(University.IKEA_U);
        } else {
            institutionLoader.createDemoData(University.POMODORO_U);
        }
        iiaLoader.createDemoData();
        departmentLoader.createDemoData();
    }
}
