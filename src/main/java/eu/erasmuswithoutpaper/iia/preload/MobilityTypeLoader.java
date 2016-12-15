
package eu.erasmuswithoutpaper.iia.preload;

import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MobilityTypeLoader {
    
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        persistMobilityType("{'mobilityGroup':'Student','mobilityCategory':'Studies'}");
        persistMobilityType("{'mobilityGroup':'Student','mobilityCategory':'Training'}");
        persistMobilityType("{'mobilityGroup':'Staff','mobilityCategory':'Teaching'}");
        persistMobilityType("{'mobilityGroup':'Staff','mobilityCategory':'Training'}");
    }
    public void createDemoDataPomodoro() throws IOException {
        createDemoDataIkea();
    }
    
    private void persistMobilityType(String mobilityTypeJson) throws IOException {
        MobilityType mobilityType = JsonHelper.mapToObject(MobilityType.class, mobilityTypeJson);
        em.persist(mobilityType);
    }
}
