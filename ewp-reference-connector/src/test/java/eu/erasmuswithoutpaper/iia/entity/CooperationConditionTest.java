
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
        MobilityType mobilityType = new MobilityType();
        mobilityType.setMobilityCategory("Student");
        mobilityType.setMobilityGroup("Studies");
        cooperationCondition.setMobilityType(mobilityType);
        cooperationCondition.setStartDate(new Date());
        cooperationCondition.setEndDate(new Date());
        cooperationCondition.setEqfLevel((byte)3);
        MobilityNumber moblilityNumber = new MobilityNumber();
        moblilityNumber.setVariant("AVERAGE");
        moblilityNumber.setNumber(5);
        cooperationCondition.setMobilityNumber(moblilityNumber);
        
        this.tx.begin();
        this.em.persist(mobilityType);
        this.em.persist(cooperationCondition);
        CooperationCondition result = em.find(CooperationCondition.class, cooperationCondition.getId());
        this.tx.rollback();
        
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getEqfLevel());
        Assert.assertEquals("Studies", result.getMobilityType().getMobilityGroup());
        Assert.assertEquals("AVERAGE", result.getMobilityNumber().getVariant());
    }

}
