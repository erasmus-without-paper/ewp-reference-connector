
package eu.erasmuswithoutpaper.course.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AcademicYearTest {
    
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
        AcademicYear academicYear = new AcademicYear("2015", "2016");
        
        this.tx.begin();
        this.em.persist(academicYear);
        
        AcademicYear result = em.find(AcademicYear.class, academicYear.getId());
        this.tx.rollback();
        
        Assert.assertNotNull(result);
        Assert.assertEquals("2015", result.getStartYear());
        Assert.assertEquals("2016", result.getEndYear());
        Assert.assertEquals("2015/2016", result.getAcademicYear());
    }
}
