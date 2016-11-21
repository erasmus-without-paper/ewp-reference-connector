
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
        String dispNames2016Spring = "[{'text':'2016 Vårtermin','lang':'sv'},{'text':'2016 Spring semester','lang':'en'}]";
        String dispNames2016Fall = "[{'text':'2016 Hösttermin','lang':'sv'},{'text':'2016 Fall semester','lang':'en'}]";
        String dispNames2017Spring = "[{'text':'2017 Vårtermin','lang':'sv'},{'text':'2017 Spring semester','lang':'en'}]";
        String dispNames2017Fall = "[{'text':'2017 Hösttermin','lang':'sv'},{'text':'2017 Fall semester','lang':'en'}]";
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicYearId':'2016/2017','academicTermId':'Spring','dispName':" + dispNames2016Spring + ",'startDate':'2016-01-16','endDate':'2016-06-29'}");
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicYearId':'2016/2017','academicTermId':'Fall','dispName':" + dispNames2016Fall + ",'startDate':'2016-09-03','endDate':'2017-01-18'}");
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicYearId':'2017/2018','academicTermId':'Spring','dispName':" + dispNames2017Spring + ",'startDate':'2017-01-19','endDate':'2017-06-30'}");
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicYearId':'2017/2018','academicTermId':'Fall','dispName':" + dispNames2017Fall + ",'startDate':'2016-09-05','endDate':'2018-01-16'}");
        
    }

    public void createDemoDataPomdoro() throws IOException {
        String dispNames2016Spring = "[{'text':'2016 Vårtermin','lang':'sv'},{'text':'2016 Spring semester','lang':'en'}]";
        String dispNames2016Fall = "[{'text':'2016 Hösttermin','lang':'sv'},{'text':'2016 Fall semester','lang':'en'}]";
        persistAcademicTerm("{'institutionId':'pomodoro.university.it,'organizationUnitId':'pomodoro.ou1.it','academicYearId':'2016/2017','academicTermId':'Spring','dispName':" + dispNames2016Spring + ",'startDate':'2016-01-17','endDate':'2017-06-30'}");
        persistAcademicTerm("{'institutionId':'pomodoro.university.it,'organizationUnitId':'pomodoro.ou1.it','academicYearId':'2016/2017','academicTermId':'Fall','dispName':" + dispNames2016Fall + ",'startDate':'2016-09-05','endDate':'2017-01-17'}");
    }
    
    private void persistAcademicTerm(String academicTermJson) throws IOException {
        AcademicTerm academicTerm = JsonHelper.mapToObject(AcademicTerm.class, academicTermJson);
        em.persist(academicTerm);
    }
}
