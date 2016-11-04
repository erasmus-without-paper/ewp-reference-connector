
package eu.erasmuswithoutpaper.iia.entity;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IiaTest {
    
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
    public void testPersistIia() {
        Iia iia = new Iia("IiaId1");
        iia.setStartDate(new Date());
        
        this.tx.begin();
        this.em.persist(iia);
        this.tx.commit();
        this.em.clear();
        
        Iia result = em.find(Iia.class, "IiaId1");
        Assert.assertNotNull(result);
    }
    
}
