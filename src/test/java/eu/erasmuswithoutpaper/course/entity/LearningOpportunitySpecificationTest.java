
package eu.erasmuswithoutpaper.course.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LearningOpportunitySpecificationTest {
    
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
    public void testPersistLearningOpportunitySpecification() {
        LearningOpportunitySpecification los = new LearningOpportunitySpecification();
        los.setLearningOpportunitySpecificationId(new LearningOpportunitySpecificationId("instId1", "losCode1"));
        los.setType("Test");
        
        this.tx.begin();
        this.em.persist(los);
        this.tx.commit();
        this.em.clear();
        
        LearningOpportunitySpecification result = em.find(LearningOpportunitySpecification.class, new LearningOpportunitySpecificationId("instId1", "losCode1"));
        Assert.assertNotNull(result);
        Assert.assertEquals("Test", result.getType());
    }

}
