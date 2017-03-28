
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
        RecognizedLaComponent learningAgreementComponent = new RecognizedLaComponent();
        learningAgreementComponent.setStatus(LearningAgreementComponentStatus.RECOMMENDED);
        
        this.tx.begin();
        this.em.persist(learningAgreementComponent);
        RecognizedLaComponent result = em.find(RecognizedLaComponent.class, learningAgreementComponent.getId());
        this.tx.rollback();
        
        Assert.assertNotNull(result);
        Assert.assertEquals(LearningAgreementComponentStatus.RECOMMENDED, result.getStatus());
    }
    
}
