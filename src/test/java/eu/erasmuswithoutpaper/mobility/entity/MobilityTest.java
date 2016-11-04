/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.mobility.entity;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MobilityTest {
    
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
    public void testPersistMobility() {
        Mobility mobility = new Mobility();
        mobility.setMobilityId(new MobilityId("mobId1", 1));
        mobility.setStartDate(new Date());
        mobility.setEndDate(new Date());
        mobility.setPersonId("9101015566");
        mobility.setStatus(MobilityStatus.ACCEPTED);
        
        this.tx.begin();
        this.em.persist(mobility);
        this.tx.commit();
        this.em.clear();
        
        Mobility result = em.find(Mobility.class, new MobilityId("mobId1", 1));
        Assert.assertNotNull(result);
        Assert.assertEquals("9101015566", result.getPersonId());
        Assert.assertEquals(MobilityStatus.ACCEPTED, result.getStatus());
    }
    
}
