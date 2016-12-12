
package eu.erasmuswithoutpaper.iia.entity;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CooperationConditionTest {
    
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
    public void testPersistCondition() {
        CooperationCondition cooperationCondition = new CooperationCondition();
        cooperationCondition.setIiaId("iiaId1");
        cooperationCondition.setMobilityType(CooperationConditionMobilityType.STAFF_TEACHING);
        cooperationCondition.setStartDate(new Date());
        cooperationCondition.setEndDate(new Date());
        cooperationCondition.setEqfLevel("Level1");
        cooperationCondition.setMobilityNumberVariant(CooperationConditionMobilityNumberVariant.TOTAL);
        
        this.tx.begin();
        this.em.persist(cooperationCondition);
        this.tx.commit();
        this.em.clear();
     
        CooperationCondition result = em.find(CooperationCondition.class, cooperationCondition.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals("Level1", result.getEqfLevel());
        Assert.assertEquals(CooperationConditionMobilityType.STAFF_TEACHING, result.getMobilityType());
        Assert.assertEquals(CooperationConditionMobilityNumberVariant.TOTAL, result.getMobilityNumberVariant());
    }

}
