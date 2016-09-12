package eu.erasmuswithoutpaper.internal;

import eu.erasmuswithoutpaper.iias.preload.IiaLoader;
import eu.erasmuswithoutpaper.institutions.preload.InstitutionLoader;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartupLoader {
    
    @Inject
    private InstitutionLoader institutionLoader;
    
    @Inject
    private IiaLoader iiaLoader;
    
    @PostConstruct
    public void loadDemoData() {
        institutionLoader.createDemoData();
        iiaLoader.createDemoData();
    }
}
