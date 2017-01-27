
package eu.erasmuswithoutpaper.mobility.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LearningAgreementTest {
    
    EntityManager em;
    EntityTransaction tx;
    
    public LearningAgreementTest() {
    }
    
    @Before
    public void setUp() {
        this.em = Persistence.createEntityManagerFactory("connector-test").createEntityManager();
        this.tx = this.em.getTransaction();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPersistLearningAgreement() {
        LearningAgreement learningAgreement = new LearningAgreement();
        learningAgreement.setLearningAgreementRevision(1);
        
        this.tx.begin();
        this.em.persist(learningAgreement);
        LearningAgreement result = em.find(LearningAgreement.class, learningAgreement.getId());
        this.tx.rollback();

        assertNotNull(result);
        assertEquals(1, result.getLearningAgreementRevision());
    }
    
}
