
package eu.erasmuswithoutpaper.course.preload;

import eu.erasmuswithoutpaper.course.entity.AcademicTerm;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AcademicTermLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        String dispNames = "[{'text':'Höst','lang':'sv'},{'text':'Fall','lang':'en'}]";
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicYearId':'2016/2017','academicTermId':'Fall16','dispName':" + dispNames + ",'startDate':'2016-09-03','endDate':'2017-01-18'}");
    }

    public void createDemoDataPomdoro() throws IOException {
        String dispNames = "[{'text':'Vår','lang':'sv'},{'text':'Spring','lang':'en'}]";
        persistAcademicTerm("{'institutionId':'pomodoro.university.it,'organizationUnitId':'pomodoro.ou1.it','academicYearId':'2016/2017','academicTermId':'Spring17','dispName':" + dispNames + ",'startDate':'2017-01-19','endDate':'2017-06-30'}");
    }
    
    private void persistAcademicTerm(String academicTermJson) throws IOException {
        AcademicTerm academicTerm = JsonHelper.mapToObject(AcademicTerm.class, academicTermJson);
        em.persist(academicTerm);
    }
}
