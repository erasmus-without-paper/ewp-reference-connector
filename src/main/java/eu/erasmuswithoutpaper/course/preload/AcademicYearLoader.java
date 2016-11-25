
package eu.erasmuswithoutpaper.course.preload;

import eu.erasmuswithoutpaper.course.entity.AcademicYear;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AcademicYearLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        persistAcademicYear("{'startYear':'2012','endYear':'2013'}");
        persistAcademicYear("{'startYear':'2013','endYear':'2014'}");
        persistAcademicYear("{'startYear':'2014','endYear':'2015'}");
        persistAcademicYear("{'startYear':'2015','endYear':'2016'}");
        persistAcademicYear("{'startYear':'2016','endYear':'2017'}");
        persistAcademicYear("{'startYear':'2017','endYear':'2018'}");
    }
    
    public void createDemoDataPomodoro() throws IOException {
        createDemoDataIkea();
    }
    
    private void persistAcademicYear(String academicYearJson) throws IOException {
        AcademicYear academicYear = JsonHelper.mapToObject(AcademicYear.class, academicYearJson);
        em.persist(academicYear);
    }
}
