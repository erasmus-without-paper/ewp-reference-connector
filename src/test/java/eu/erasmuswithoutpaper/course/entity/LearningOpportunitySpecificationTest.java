
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

public class LearningOpportunitySpecificationTest {
    
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
    public void testPersistLearningOpportunitySpecification() {
        LearningOpportunitySpecification los = new LearningOpportunitySpecification("instId1", "losCode1");
        los.setType("Test");
        List<LanguageItem> names = new ArrayList<>();
        LanguageItem nameSv = new LanguageItem("LearningOpSpecNameSv", LanguageItem.SWEDISH);
        LanguageItem nameEn = new LanguageItem("LearningOpSpecNameEn", LanguageItem.ENGLISH);
        names.add(nameSv);
        names.add(nameEn);
        los.setName(names);
        List<LanguageItem> urls = new ArrayList<>();
        LanguageItem urlSv = new LanguageItem("www.learn.se", LanguageItem.SWEDISH);
        LanguageItem urlEn = new LanguageItem("www.learn.en", LanguageItem.ENGLISH);
        urls.add(urlSv);
        urls.add(urlEn);
        los.setUrl(urls);
        
        this.tx.begin();
        this.em.persist(los);
        this.tx.commit();
        this.em.clear();
        
        LearningOpportunitySpecification result = em.find(LearningOpportunitySpecification.class, los.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals("Test", result.getType());
        
        Assert.assertEquals("LearningOpSpecNameSv", result.getName().get(0).getText());
        Assert.assertEquals(LanguageItem.SWEDISH, result.getName().get(0).getLang());
        Assert.assertEquals("LearningOpSpecNameEn", result.getName().get(1).getText());
        Assert.assertEquals(LanguageItem.ENGLISH, result.getName().get(1).getLang());
        
        Assert.assertEquals("www.learn.se", result.getUrl().get(0).getText());
        Assert.assertEquals(LanguageItem.SWEDISH, result.getUrl().get(0).getLang());
        Assert.assertEquals("www.learn.en", result.getUrl().get(1).getText());
        Assert.assertEquals(LanguageItem.ENGLISH, result.getUrl().get(1).getLang());
    }

}
