
package eu.erasmuswithoutpaper.organization.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {
    
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
    public void testPersistPerson() {
        Person person = new Person();
        person.setPersonId("8801012233");
        person.setFirstName("Albin");
        person.setLastName("Ek");
        
        this.tx.begin();
        this.em.persist(person);
        this.tx.commit();
        this.em.clear();
        
        Person result = em.find(Person.class, "8801012233");
        Assert.assertNotNull(result);
        Assert.assertEquals("Albin", result.getFirstName());
    }
    
}
