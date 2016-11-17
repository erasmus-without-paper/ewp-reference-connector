
package eu.erasmuswithoutpaper.organization.entity;

import java.util.ArrayList;
import java.util.List;
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
        List<LanguageItem> names = new ArrayList<>();
        LanguageItem nameSv = new LanguageItem("TestInstSv", LanguageItem.SWEDISH);
        LanguageItem nameEn = new LanguageItem("TestInstEn", LanguageItem.ENGLISH);
        names.add(nameSv);
        names.add(nameEn);
        organizationUnit.setName(names);
        List<LanguageItem> descriptions = new ArrayList<>();
        LanguageItem descriptionSv = new LanguageItem("TestDescSv", LanguageItem.SWEDISH);
        LanguageItem descriptionEn = new LanguageItem("TestDescEn", LanguageItem.ENGLISH);
        descriptions.add(descriptionSv);
        descriptions.add(descriptionEn);
        organizationUnit.setDescription(descriptions);
        
        this.tx.begin();
        this.em.persist(organizationUnit);
        this.tx.commit();
        this.em.clear();
        
        OrganizationUnit result = em.find(OrganizationUnit.class, organizationUnit.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals("TestInstSv", result.getName().get(0).getText());
        Assert.assertEquals(LanguageItem.SWEDISH, result.getName().get(0).getLang());
        Assert.assertEquals("TestInstEn", result.getName().get(1).getText());
        Assert.assertEquals(LanguageItem.ENGLISH, result.getName().get(1).getLang());
        
        Assert.assertEquals("TestDescSv", result.getDescription().get(0).getText());
        Assert.assertEquals(LanguageItem.SWEDISH, result.getDescription().get(0).getLang());
        Assert.assertEquals("TestDescEn", result.getDescription().get(1).getText());
        Assert.assertEquals(LanguageItem.ENGLISH, result.getDescription().get(1).getLang());
    }

}
