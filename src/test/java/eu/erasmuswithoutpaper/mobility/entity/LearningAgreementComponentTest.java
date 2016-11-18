
package eu.erasmuswithoutpaper.mobility.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LearningAgreementComponentTest {
    
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
    public void testPersistLearningAgreementComponent() {
        LearningAgreementComponent learningAgreementComponent = new LearningAgreementComponent();
        learningAgreementComponent.setInstitutionId("instId1");
        learningAgreementComponent.setOrganizationUnitId("orgUnitId1");
        learningAgreementComponent.setMobilityId("mobId1");
        learningAgreementComponent.setMobilityRevision(1);
        learningAgreementComponent.setLearningAgreementRevision(2);
        learningAgreementComponent.setLosCode("AAA111");
        learningAgreementComponent.setStatus(LearningAgreementComponentStatus.RECOMMENDED);
        
        this.tx.begin();
        this.em.persist(learningAgreementComponent);
        this.tx.commit();
        this.em.clear();
        
        LearningAgreementComponent result = em.find(LearningAgreementComponent.class, learningAgreementComponent.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals("AAA111", result.getLosCode());
        Assert.assertEquals(LearningAgreementComponentStatus.RECOMMENDED, result.getStatus());
    }
    
}
