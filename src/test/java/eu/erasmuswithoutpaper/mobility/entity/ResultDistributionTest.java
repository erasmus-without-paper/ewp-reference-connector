
package eu.erasmuswithoutpaper.mobility.entity;

import eu.erasmuswithoutpaper.omobility.entity.ResultDistribution;
import eu.erasmuswithoutpaper.omobility.entity.ResultDistributionCategory;
import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResultDistributionTest {
    
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
    public void testPersistResultDistribution() {
        List<ResultDistributionCategory> resultDistributionCategories = new ArrayList<>();
        ResultDistributionCategory resultDistributionCategory = new ResultDistributionCategory();
        resultDistributionCategory.setLabel("A");
        resultDistributionCategory.setCount(BigInteger.TEN);
        resultDistributionCategories.add(resultDistributionCategory);
        ResultDistribution resultDistribution = new ResultDistribution();
        resultDistribution.setResultDistributionCategory(resultDistributionCategories);
        
        List<LanguageItem> descriptions = new ArrayList<>();
        LanguageItem description = new LanguageItem();
        description.setLang("se");
        description.setText("Simple course where all students got A's.");
        resultDistribution.setDescription(descriptions);
        
        this.tx.begin();
        this.em.persist(resultDistribution);
        ResultDistribution result = em.find(ResultDistribution.class, resultDistribution.getId());
        this.tx.rollback();

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getResultDistributionCategory().size());
    }
    
}
