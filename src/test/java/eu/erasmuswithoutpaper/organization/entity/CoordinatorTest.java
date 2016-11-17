
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
        Person person = new Person();
        person.setPersonId("9002023344");
        person.setFirstNames("Albin");
        person.setLastName("Ek");
        Coordinator coordinator = new Coordinator("instId1", "unitId1", CoordinatorHeader.COURSE);
        coordinator.setPerson(person);
        
        this.tx.begin();
        this.em.persist(person);
        this.em.persist(coordinator);
        this.tx.commit();
        this.em.clear();
        
        long id = coordinator.getId();
        Coordinator result = em.find(Coordinator.class, id);
        Assert.assertNotNull(result);
        Assert.assertEquals("9002023344", result.getPerson().getPersonId());
    }

}
