
package eu.erasmuswithoutpaper.course.preload;

import eu.erasmuswithoutpaper.course.entity.AcademicTerm;
import eu.erasmuswithoutpaper.course.entity.AcademicYear;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class LoiLoader {
@PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        persistLearningOppInst("{'institutionId':'ikea.university.se','credits':'5'}", getLearningOppSpec("IU001"), getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"));
        persistLearningOppInst("{'institutionId':'ikea.university.se','credits':'7.5'}", getLearningOppSpec("IU002"), getAcademicTerm(getAcademicYear("2016", "2017"), "Spring"));
    }
    
    public void createDemoDataPomodoro() throws IOException {
        persistLearningOppInst("{'institutionId':'pomodoro.university.it','credits':'5'}", getLearningOppSpec("PU001"), getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"));
        persistLearningOppInst("{'institutionId':'pomodoro.university.it','credits':'7.5'}", getLearningOppSpec("PU002"), getAcademicTerm(getAcademicYear("2016", "2017"), "Spring"));
    }
    
    private void persistLearningOppInst(String loiJson, LearningOpportunitySpecification learningOppSpec, AcademicTerm academicTerm) throws IOException {
        LearningOpportunityInstance learningOppInst = JsonHelper.mapToObject(LearningOpportunityInstance.class, loiJson);
        learningOppInst.setLearningOppSpec(learningOppSpec);
        learningOppInst.setAcademicTerm(academicTerm);
        em.persist(learningOppInst);
    }
    
    private LearningOpportunitySpecification getLearningOppSpec(String losCode) throws IOException {
        Query query = em.createNamedQuery(LearningOpportunitySpecification.findByLosCode).setParameter("losCode", losCode);
        List<LearningOpportunitySpecification> LearningOppInstList = query.getResultList();
        if (LearningOppInstList.size() != 1) {
           throw new IllegalArgumentException("losCode " + losCode + " doesn't return an unique Learning opportunity specification.");
        }
        
        return LearningOppInstList.get(0);
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
}
