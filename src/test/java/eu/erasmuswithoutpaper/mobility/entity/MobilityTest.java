
package eu.erasmuswithoutpaper.mobility.entity;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MobilityTest {
    
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
    public void testPersistMobility() {
        Mobility mobility = new Mobility();
        mobility.setPlannedArrivalDate(new Date());
        mobility.setMobilityParticipantId("9101015566");
        mobility.setStatus(MobilityStatus.NOMINATION);
        
        this.tx.begin();
        this.em.persist(mobility);
        Mobility result = em.find(Mobility.class, mobility.getId());
        this.tx.rollback();

        Assert.assertNotNull(result);
        Assert.assertEquals("9101015566", result.getMobilityParticipantId());
        Assert.assertEquals(MobilityStatus.NOMINATION, result.getStatus());
    }
    
}
