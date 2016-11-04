
package eu.erasmuswithoutpaper.course.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AcademicTermTest {
    
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
    public void testPersistAcademicTerm() {
        AcademicTerm academicTerm = new AcademicTerm();
        academicTerm.setAcademicTermId(new AcademicTermId("InstId3", "OrgUnitId3", "TestTerm"));
        academicTerm.setDispName("DispName");
        
        this.tx.begin();
        this.em.persist(academicTerm);
        this.tx.commit();
        this.em.clear();
        
        AcademicTerm result = em.find(AcademicTerm.class, new AcademicTermId("InstId3", "OrgUnitId3", "TestTerm"));
        Assert.assertNotNull(result);
        Assert.assertEquals("DispName", result.getDispName());
    }

}
