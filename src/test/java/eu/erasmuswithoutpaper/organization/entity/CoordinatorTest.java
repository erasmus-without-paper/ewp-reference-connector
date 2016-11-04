
package eu.erasmuswithoutpaper.organization.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoordinatorTest {
    
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
    public void testPersistCoordinator() {
        Coordinator coordinator = new Coordinator();
        coordinator.setCoordinatorId(new CoordinatorId("instId1", "unitId1", "9002023344", "roleId1"));
        
        this.tx.begin();
        this.em.persist(coordinator);
        this.tx.commit();
        this.em.clear();
        
        Coordinator result = em.find(Coordinator.class, new CoordinatorId("instId1", "unitId1", "9002023344", "roleId1"));
        Assert.assertNotNull(result);
        Assert.assertEquals("9002023344", result.getCoordinatorId().getPersonId());
    }

}
