
package eu.erasmuswithoutpaper.course.preload;

import eu.erasmuswithoutpaper.course.entity.AcademicTerm;
import eu.erasmuswithoutpaper.course.entity.AcademicYear;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class AcademicTermLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        String dispNames2014Spring = "[{'text':'Vårtermin 2014','lang':'sv'},{'text':'Spring semester 2014','lang':'en'}]";
        String dispNames2014Fall = "[{'text':'Hösttermin 2014','lang':'sv'},{'text':'Fall semester 2014','lang':'en'}]";
        String dispNames2015Spring = "[{'text':'Vårtermin 2015','lang':'sv'},{'text':'Spring semester 2015','lang':'en'}]";
        String dispNames2015Fall = "[{'text':'Hösttermin 2015','lang':'sv'},{'text':'Fall semester 2015','lang':'en'}]";
        String dispNames2016Spring = "[{'text':'Vårtermin 2016','lang':'sv'},{'text':'Spring semester 2016','lang':'en'}]";
        String dispNames2016Fall = "[{'text':'Hösttermin 2016','lang':'sv'},{'text':'Fall semester 2016','lang':'en'}]";
        String dispNames2017Spring = "[{'text':'Vårtermin 2017','lang':'sv'},{'text':'Spring semester 2017','lang':'en'}]";
        String dispNames2017Fall = "[{'text':'Hösttermin 2017','lang':'sv'},{'text':'Fall semester 2017','lang':'en'}]";
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicTermId':'Spring','dispName':" + dispNames2014Spring + ",'startDate':'2014-01-17','endDate':'2014-06-28'}", getAcademicYear("2013", "2014"));
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicTermId':'Fall','dispName':" + dispNames2014Fall + ",'startDate':'2014-09-04','endDate':'2015-01-12'}", getAcademicYear("2014", "2015"));
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicTermId':'Spring','dispName':" + dispNames2015Spring + ",'startDate':'2015-01-13','endDate':'2015-06-27'}", getAcademicYear("2014", "2015"));
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicTermId':'Fall','dispName':" + dispNames2015Fall + ",'startDate':'2015-09-10','endDate':'2016-01-22'}", getAcademicYear("2015", "2016"));
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicTermId':'Spring','dispName':" + dispNames2016Spring + ",'startDate':'2016-01-23','endDate':'2016-06-29'}", getAcademicYear("2015", "2016"));
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicTermId':'Fall','dispName':" + dispNames2016Fall + ",'startDate':'2016-09-03','endDate':'2017-01-18'}", getAcademicYear("2016", "2017"));
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicTermId':'Spring','dispName':" + dispNames2017Spring + ",'startDate':'2017-01-19','endDate':'2017-06-30'}", getAcademicYear("2016", "2017"));
        persistAcademicTerm("{'institutionId':'ikea.university.se','organizationUnitId':'ikea.ou1.se','academicTermId':'Fall','dispName':" + dispNames2017Fall + ",'startDate':'2017-09-05','endDate':'2018-01-16'}", getAcademicYear("2017", "2018"));
    }

    public void createDemoDataPomodoro() throws IOException {
        String dispNames2014Spring = "[{'text':'Vårtermin 2014','lang':'sv'},{'text':'Spring semester 2014','lang':'en'}]";
        String dispNames2014Fall = "[{'text':'Hösttermin 2014','lang':'sv'},{'text':'Fall semester 2014','lang':'en'}]";
        String dispNames2015Spring = "[{'text':'Vårtermin 2015','lang':'sv'},{'text':'Spring semester 2015','lang':'en'}]";
        String dispNames2015Fall = "[{'text':'Hösttermin 2015','lang':'sv'},{'text':'Fall semester 2015','lang':'en'}]";
        String dispNames2016Spring = "[{'text':'Vårtermin 2016','lang':'sv'},{'text':'Spring semester 2016','lang':'en'}]";
        String dispNames2016Fall = "[{'text':'Hösttermin 2016','lang':'sv'},{'text':'Fall semester 2016','lang':'en'}]";
        String dispNames2017Spring = "[{'text':'Vårtermin 2017','lang':'sv'},{'text':'Spring semester 2017','lang':'en'}]";
        String dispNames2017Fall = "[{'text':'Hösttermin 2017','lang':'sv'},{'text':'Fall semester 2017','lang':'en'}]";
        persistAcademicTerm("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','academicTermId':'Spring','dispName':" + dispNames2014Spring + ",'startDate':'2014-01-17','endDate':'2014-06-28'}", getAcademicYear("2013", "2014"));
        persistAcademicTerm("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','academicTermId':'Fall','dispName':" + dispNames2014Fall + ",'startDate':'2014-09-04','endDate':'2015-01-12'}", getAcademicYear("2014", "2015"));
        persistAcademicTerm("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','academicTermId':'Spring','dispName':" + dispNames2015Spring + ",'startDate':'2015-01-13','endDate':'2015-06-27'}", getAcademicYear("2014", "2015"));
        persistAcademicTerm("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','academicTermId':'Fall','dispName':" + dispNames2015Fall + ",'startDate':'2015-09-10','endDate':'2016-01-22'}", getAcademicYear("2015", "2016"));
        persistAcademicTerm("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','academicTermId':'Spring','dispName':" + dispNames2016Spring + ",'startDate':'2016-01-23','endDate':'2016-06-29'}", getAcademicYear("2015", "2016"));
        persistAcademicTerm("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','academicTermId':'Fall','dispName':" + dispNames2016Fall + ",'startDate':'2016-09-03','endDate':'2017-01-18'}", getAcademicYear("2016", "2017"));
        persistAcademicTerm("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','academicTermId':'Spring','dispName':" + dispNames2017Spring + ",'startDate':'2017-01-19','endDate':'2017-06-30'}", getAcademicYear("2016", "2017"));
        persistAcademicTerm("{'institutionId':'pomodoro.university.it','organizationUnitId':'pomodoro.ou1.it','academicTermId':'Fall','dispName':" + dispNames2017Fall + ",'startDate':'2017-09-05','endDate':'2018-01-16'}", getAcademicYear("2017", "2018"));
    }
    
    private void persistAcademicTerm(String academicTermJson, AcademicYear academicYear) throws IOException {
        AcademicTerm academicTerm = JsonHelper.mapToObject(AcademicTerm.class, academicTermJson);
        academicTerm.setAcademicYear(academicYear);
        em.persist(academicTerm);
    }
    
    private AcademicYear getAcademicYear(String startYear, String endYear) throws IOException {
        Query query = em.createNamedQuery(AcademicYear.findByKeys).setParameter("startYear", startYear).setParameter("endYear", endYear);
        List<AcademicYear> academicYearList = query.getResultList();
        if (academicYearList.size() != 1) {
           throw new IllegalArgumentException("Academic year " + startYear + "/" + endYear + " doesn't return an unique academic year.");
        }
        
        return academicYearList.get(0);
    }
}
