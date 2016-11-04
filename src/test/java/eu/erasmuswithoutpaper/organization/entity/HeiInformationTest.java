
package eu.erasmuswithoutpaper.organization.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HeiInformationTest {
    
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
    public void testGetHeiInformationId() {
        HeiInformation heiInformation = new HeiInformation();
        heiInformation.setHeiInformationId(new HeiInformationId("InstId1", "OrgUnitId1", "Header"));
        heiInformation.setContent("Test");
        
        this.tx.begin();
        this.em.persist(heiInformation);
        this.tx.commit();
        this.em.clear();
        
        HeiInformation result = em.find(HeiInformation.class, new HeiInformationId("InstId1", "OrgUnitId1", "Header"));
        Assert.assertNotNull(result);
        Assert.assertEquals("Test", result.getContent());
    }

}
