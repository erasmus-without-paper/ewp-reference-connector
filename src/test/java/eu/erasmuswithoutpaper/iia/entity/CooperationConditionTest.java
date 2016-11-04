
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
        CooperationConditionId id = new CooperationConditionId();
        id.setIiaId("iiaId1");
        id.setFromInstitutionId("instId1");
        id.setFromOrganizationUnitId("orgUnitId1");
        id.setToInstitutionId("instId2");
        id.setToOrganizationUnitId("orgUnitId2");
        id.setMobilityType(CooperationConditionMobilityType.STAFF_TEACHING);
        id.setStartDate(new Date());
        id.setEndDate(new Date());
        cooperationCondition.setCooperationConditionId(id);
        cooperationCondition.setEqfLevel("Level1");
        cooperationCondition.setMobilityNumberVariant(CooperationConditionMobilityNumberVariant.TOTAL);
        
        this.tx.begin();
        this.em.persist(cooperationCondition);
        this.tx.commit();
        this.em.clear();
     
        CooperationCondition result = em.find(CooperationCondition.class, id);
        Assert.assertNotNull(result);
        Assert.assertEquals("Level1", result.getEqfLevel());
        Assert.assertEquals(CooperationConditionMobilityType.STAFF_TEACHING, result.getCooperationConditionId().getMobilityType());
        Assert.assertEquals(CooperationConditionMobilityNumberVariant.TOTAL, result.getMobilityNumberVariant());
    }

}
