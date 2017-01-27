
package eu.erasmuswithoutpaper.iia.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IiaPartnerTest {
    
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
    public void testPersistPartner() {
        IiaPartner iiaPartner = new IiaPartner();
        
        this.tx.begin();
        this.em.persist(iiaPartner);
        IiaPartner result = em.find(IiaPartner.class, iiaPartner.getId());
        this.tx.rollback();
        
        Assert.assertNotNull(result);
    }
}
