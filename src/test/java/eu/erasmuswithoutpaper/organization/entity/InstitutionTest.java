
package eu.erasmuswithoutpaper.organization.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InstitutionTest {
    
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
    public void testPersistInstitution() {
        Institution institution = new Institution();
        institution.setInstitutionId("InstId1");
        institution.setName("TestInst");
        
        this.tx.begin();
        this.em.persist(institution);
        this.tx.commit();
        this.em.clear();
        
        Institution result = em.find(Institution.class, "InstId1");
        Assert.assertNotNull(result);
        Assert.assertEquals("TestInst", result.getName());
    }
    
}
