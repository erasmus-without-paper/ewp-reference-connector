package eu.erasmuswithoutpaper.internal;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractStartupLoader {
    @PersistenceContext(unitName = "connector")
    protected EntityManager em;

    public abstract void createDemoDataIkea() throws IOException;
    public abstract void createDemoDataPomodoro() throws IOException;
   
    /**
     * Override injected Entity Manager.
     * For test purpose
     * @param em 
     */
    public void setEntityManger(EntityManager em) {
        this.em = em;
    }
}
