
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
        ResultDistributionId id = new ResultDistributionId();
        id.setInstitutionId("InstId1");
        id.setLabel("TestLabel");
        id.setLosCode("Los1");
        id.setTermName("TestTerm");
        resultDistribution.setResultDistributionId(id);
        resultDistribution.setCount(3);
        
        this.tx.begin();
        this.em.persist(resultDistribution);
        this.tx.commit();
        this.em.clear();
        
        ResultDistribution result = em.find(ResultDistribution.class, id);
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getCount());
    }
    
}
