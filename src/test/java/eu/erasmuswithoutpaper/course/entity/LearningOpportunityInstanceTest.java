
package eu.erasmuswithoutpaper.course.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LearningOpportunityInstanceTest {
    
    EntityManager em;
    EntityTransaction tx;
    
    @Before
    public void setUp() {
        this.em = Persistence.createEntityManagerFactory("connector-test").createEntityManager();
        this.tx = this.em.getTransaction();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPersistLearningOpportunityInstance() {
        LearningOpportunityInstance loi = new LearningOpportunityInstance();
        loi.setInstitutionId("instId1");
        loi.setLosCode("losCode3");
        loi.setAcademicYearId("academicYearId1");
        loi.setAcademicTermId("academicTermId1");
        loi.setCredits("10");
        
        this.tx.begin();
        this.em.persist(loi);
        this.tx.commit();
        this.em.clear();
        
        LearningOpportunityInstance result = em.find(LearningOpportunityInstance.class, loi.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals("10", result.getCredits());
    }
    
}
