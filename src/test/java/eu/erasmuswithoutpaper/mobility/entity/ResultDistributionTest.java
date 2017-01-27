
package eu.erasmuswithoutpaper.mobility.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResultDistributionTest {
    
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
    public void testPersistResultDistribution() {
        ResultDistribution resultDistribution = new ResultDistribution();
        resultDistribution.setInstitutionId("InstId1");
        resultDistribution.setLabel("TestLabel");
        resultDistribution.setLosCode("Los1");
        resultDistribution.setDistrubutionCount(3);
        
        this.tx.begin();
        this.em.persist(resultDistribution);
        ResultDistribution result = em.find(ResultDistribution.class, resultDistribution.getId());
        this.tx.rollback();

        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getDistrubutionCount());
    }
    
}
