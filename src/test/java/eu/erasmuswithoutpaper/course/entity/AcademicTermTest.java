
package eu.erasmuswithoutpaper.course.entity;

import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import java.util.ArrayList;
import java.util.List;
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
        AcademicTerm academicTerm = new AcademicTerm("InstId3", "OrgUnitId3", "TestTerm");
        List<LanguageItem> dispName = new ArrayList();
        LanguageItem dispNameEn = new LanguageItem("DispNameEn", LanguageItem.ENGLISH);
        dispName.add(dispNameEn);
        academicTerm.setDispName(dispName);
        
        this.tx.begin();
        this.em.persist(academicTerm);
        this.tx.commit();
        this.em.clear();
        
        AcademicTerm result = em.find(AcademicTerm.class, academicTerm.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals("DispNameEn", result.getDispName().get(0).getText());
    }

}
