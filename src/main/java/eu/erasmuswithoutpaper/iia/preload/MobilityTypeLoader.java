
package eu.erasmuswithoutpaper.iia.preload;

import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;

public class MobilityTypeLoader extends AbstractStartupLoader {
    
    @Override
    public void createDemoDataIkea() throws IOException {
        persistMobilityType("{'mobilityGroup':'Student','mobilityCategory':'Studies'}");
        persistMobilityType("{'mobilityGroup':'Student','mobilityCategory':'Training'}");
        persistMobilityType("{'mobilityGroup':'Staff','mobilityCategory':'Teaching'}");
        persistMobilityType("{'mobilityGroup':'Staff','mobilityCategory':'Training'}");
    }

    @Override
    public void createDemoDataPomodoro() throws IOException {
        createDemoDataIkea();
    }
    
    private void persistMobilityType(String mobilityTypeJson) throws IOException {
        MobilityType mobilityType = JsonHelper.mapToObject(MobilityType.class, mobilityTypeJson);
        em.persist(mobilityType);
    }
}
