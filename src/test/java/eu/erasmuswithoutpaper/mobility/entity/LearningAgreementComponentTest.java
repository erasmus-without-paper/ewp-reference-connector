
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
        LearningAgreementComponentId id = new LearningAgreementComponentId();
        id.setInstitutionId("instId1");
        id.setOrganizationUnitId("orgUnitId1");
        id.setMobilityId("mobId1");
        id.setMobilityRevision(1);
        id.setLearningAgreementRevision(2);
        id.setTermName("term1");
        id.setLosCode("AAA111");
        learningAgreementComponent.setLearningAgreementComponentId(id);
        learningAgreementComponent.setStatus(LearningAgreementComponentStatus.RECOMMENDED);
        
        this.tx.begin();
        this.em.persist(learningAgreementComponent);
        this.tx.commit();
        this.em.clear();
        
        LearningAgreementComponent result = em.find(LearningAgreementComponent.class, id);
        Assert.assertNotNull(result);
        Assert.assertEquals("AAA111", result.getLearningAgreementComponentId().getLosCode());
        Assert.assertEquals(LearningAgreementComponentStatus.RECOMMENDED, result.getStatus());
    }
    
}
