package eu.erasmuswithoutpaper.iias.preload;

import eu.erasmuswithoutpaper.iias.entity.Iia;
import eu.erasmuswithoutpaper.internal.JsonToObject;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class IiaLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoData() {
        
        try {
            persistIia("{'partner':{'condition':[" +
                    "{'term':{'title':[{'lang':'en','title':'English Title'}],'startDate':'2016-10-01','endDate':'2016-10-06'},'subjectArea':123,'mobilityType':['type1']}" +
                    "],'institutionId':'hei1','organizationUnitId':'ouid1'}}");
            
            persistIia("{'partner':{'condition':[" +
                    "{'term':{'title':[{'title':'English Title'},{'lang':'se','title':'Swedish Title'}],'startDate':'2016-10-01','endDate':'2016-10-06'},'subjectArea':234,'mobilityType':['type2','type3']}," +
                    "{'term':{'title':[{'title':'English Title'},{'lang':'se','title':'Swedish Title'}],'startDate':'2016-09-11','endDate':'2016-10-08'},'subjectArea':345,'mobilityType':['type3']}" +
                    "],'institutionId':'hei2','organizationUnitId':'ouid2'}}");
            
        } catch (IOException ex) {
            Logger.getLogger(IiaLoader.class.getName()).log(Level.SEVERE, "Could not load IIA json to object", ex);
        }
    }
    
    private void persistIia(String iiaJson) throws IOException {
        Iia iia = JsonToObject.mapToObject(Iia.class, iiaJson);
        iia.setId(UUID.randomUUID().toString());
        em.persist(iia);
    }
}
