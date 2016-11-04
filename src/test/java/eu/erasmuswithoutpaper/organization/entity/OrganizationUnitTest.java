
package eu.erasmuswithoutpaper.organization.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrganizationUnitTest {
    
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
    public void testPersistOrganizationUnit() {
        OrganizationUnit organizationUnit = new OrganizationUnit();
        organizationUnit.setOrganizationUnitId("OrgUnitId1");
        organizationUnit.setName("OrgUnitTest");
        
        this.tx.begin();
        this.em.persist(organizationUnit);
        this.tx.commit();
        this.em.clear();
        
        OrganizationUnit result = em.find(OrganizationUnit.class, "OrgUnitId1");
        Assert.assertNotNull(result);
        Assert.assertEquals("OrgUnitTest", result.getName());
    }

}
