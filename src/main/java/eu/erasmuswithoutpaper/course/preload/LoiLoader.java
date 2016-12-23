
package eu.erasmuswithoutpaper.course.preload;

import eu.erasmuswithoutpaper.course.entity.AcademicTerm;
import eu.erasmuswithoutpaper.course.entity.AcademicYear;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class LoiLoader {
@PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        
        // Course1
        LearningOpportunitySpecification course1Los = getLos("IU001");
        LearningOpportunityInstance course1Loi1 = createLearningOppInst("{'credits':'5'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"));
        LearningOpportunityInstance course1Loi2 = createLearningOppInst("{'organizationUnitId':'ikea.ou1.se','credits':'7.5'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Spring"));
        LearningOpportunityInstance course1Loi3 = createLearningOppInst("{'organizationUnitId':'ikea.ou1.se','credits':'7.5'}", getAcademicTerm(getAcademicYear("2016", "2017"), "Fall"));
        List<LearningOpportunityInstance> course1LoiList = new ArrayList<>();
        course1LoiList.add(course1Loi1);
        course1LoiList.add(course1Loi2);
        course1LoiList.add(course1Loi3);
        course1Los.setLearningOpportunityInstances(course1LoiList);
        em.merge(course1Los);
        
        // Course2
        LearningOpportunitySpecification course2Los = getLos("IU002");
        LearningOpportunityInstance course2Loi1 = createLearningOppInst("{'credits':'15'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"));
        LearningOpportunityInstance course2Loi2 = createLearningOppInst("{'credits':'15'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Spring"));
        List<LearningOpportunityInstance> course2LoiList = new ArrayList<>();
        course2LoiList.add(course2Loi1);
        course2LoiList.add(course2Loi2);
        course2Los.setLearningOpportunityInstances(course2LoiList);
        em.merge(course2Los);
        
        // Module1
        LearningOpportunitySpecification module1Los = getLos("MOD01");
        LearningOpportunityInstance module1Loi1 = createLearningOppInst("{'organizationUnitId':'ikea.ou1.se','credits':'15'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"));
        List<LearningOpportunityInstance> module1LoiList = new ArrayList<>();
        module1LoiList.add(module1Loi1);
        module1Los.setLearningOpportunityInstances(module1LoiList);
        em.merge(module1Los);
        
        // Course3, part of Module1
        LearningOpportunitySpecification course3Los = getLos("IUJ04");
        LearningOpportunityInstance course3Loi1 = createLearningOppInst("{'organizationUnitId':'ikea.ou1.se','credits':'7.5'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"));
        List<LearningOpportunityInstance> course3LoiList = new ArrayList<>();
        course3LoiList.add(course3Loi1);
        course3Los.setLearningOpportunityInstances(course3LoiList);
        em.merge(course3Los);
       
        // Course4, part of Module1
        LearningOpportunitySpecification course4Los = getLos("IUJ05");
        LearningOpportunityInstance course4Loi1 = createLearningOppInst("{'organizationUnitId':'ikea.ou1.se','credits':'7.5'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"));
        List<LearningOpportunityInstance> course4LoiList = new ArrayList<>();
        course4LoiList.add(course4Loi1);
        course4Los.setLearningOpportunityInstances(course4LoiList);
        em.merge(course4Los);
        
        // Class1, part of Course4
        LearningOpportunitySpecification class1Los = getLos("IUCL1");
        LearningOpportunityInstance class1Loi1 = createLearningOppInst("{'organizationUnitId':'ikea.ou1.se','credits':'3.5'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"));
        List<LearningOpportunityInstance> class1LoiList = new ArrayList<>();
        class1LoiList.add(class1Loi1);
        class1Los.setLearningOpportunityInstances(class1LoiList);
        em.merge(class1Los);
        
        // Class2, part of Course4
        LearningOpportunitySpecification class2Los = getLos("IUCL2");
        LearningOpportunityInstance class2Loi1 = createLearningOppInst("{'organizationUnitId':'ikea.ou1.se','credits':'4'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"));
        List<LearningOpportunityInstance> class2LoiList = new ArrayList<>();
        class2LoiList.add(class2Loi1);
        class2Los.setLearningOpportunityInstances(class2LoiList);
        em.merge(class2Los);
    }

    public void createDemoDataPomodoro() throws IOException {
        //TODO Create data for Pomodoro
        
        // Course1
        LearningOpportunitySpecification course1Los = getLos("PU001");
        LearningOpportunityInstance course1Loi1 = createLearningOppInst("{'credits':'5'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"));
        LearningOpportunityInstance course1Loi2 = createLearningOppInst("{'organizationUnitId':'pomodoro.ou1.it','credits':'7.5'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Spring"));
        LearningOpportunityInstance course1Loi3 = createLearningOppInst("{'organizationUnitId':'pomodoro.ou1.it','credits':'7.5'}", getAcademicTerm(getAcademicYear("2016", "2017"), "Fall"));
        List<LearningOpportunityInstance> course1LoiList = new ArrayList<>();
        course1LoiList.add(course1Loi1);
        course1LoiList.add(course1Loi2);
        course1LoiList.add(course1Loi3);
        course1Los.setLearningOpportunityInstances(course1LoiList);
        em.merge(course1Los);
        
        // Course2
        LearningOpportunitySpecification course2Los = getLos("PU002");
        LearningOpportunityInstance course2Loi1 = createLearningOppInst("{'credits':'15'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"));
        LearningOpportunityInstance course2Loi2 = createLearningOppInst("{'credits':'15'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Spring"));
        List<LearningOpportunityInstance> course2LoiList = new ArrayList<>();
        course2LoiList.add(course2Loi1);
        course2LoiList.add(course2Loi2);
        course2Los.setLearningOpportunityInstances(course2LoiList);
        em.merge(course2Los);
    }
    
    private LearningOpportunityInstance createLearningOppInst(String loiJson, AcademicTerm academicTerm) throws IOException {
        LearningOpportunityInstance learningOppInst = JsonHelper.mapToObject(LearningOpportunityInstance.class, loiJson);
        learningOppInst.setAcademicTerm(academicTerm);
        return learningOppInst;
    }
    
    private AcademicYear getAcademicYear(String startYear, String endYear) throws IOException {
        Query query = em.createNamedQuery(AcademicYear.findByKeys).setParameter("startYear", startYear).setParameter("endYear", endYear);
        List<AcademicYear> academicYearList = query.getResultList();
        if (academicYearList.size() != 1) {
           throw new IllegalArgumentException("Academic year " + startYear + "/" + endYear + " doesn't return an unique academic year.");
        }
        
        return academicYearList.get(0);
    }

    private AcademicTerm getAcademicTerm(AcademicYear academicYear, String academicTermId) throws IOException {
        long academicYearId = academicYear.getId();
        Query query = em.createNamedQuery(AcademicTerm.findByAcademicYearAndTermId).setParameter("academicYearId", academicYearId).setParameter("academicTermId", academicTermId);
        List<AcademicTerm> academicTermList = query.getResultList();
        if (academicTermList.size() != 1) {
           throw new IllegalArgumentException("Academic term " + academicYear.getAcademicYear() + academicTermId + " doesn't return an unique academic term.");
        }
        
        return academicTermList.get(0);
    }

    private LearningOpportunitySpecification getLos(String losCode) {
        Query query = em.createNamedQuery(LearningOpportunitySpecification.findByLosCode).setParameter("losCode", losCode);
        List<LearningOpportunitySpecification> losList = query.getResultList();
        if (losList.size() != 1) {
           throw new IllegalArgumentException("Los code " + losCode + " doesn't return an unique los.");
        }
        
        return losList.get(0);
    }
}
