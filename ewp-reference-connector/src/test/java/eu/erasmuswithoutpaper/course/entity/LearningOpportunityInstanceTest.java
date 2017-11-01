
package eu.erasmuswithoutpaper.course.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        Credit credit = new Credit();
        credit.setValue(new BigDecimal(10));
        List<Credit> credits = new ArrayList<>();
        credits.add(credit);
        loi.setCredits(credits);
        
        this.tx.begin();
        this.em.persist(loi);
        LearningOpportunityInstance result = em.find(LearningOpportunityInstance.class, loi.getId());
        this.tx.rollback();

        Assert.assertNotNull(result);
        Assert.assertEquals("10", result.getCredits().get(0).getValue().toString());
    }
    
}
