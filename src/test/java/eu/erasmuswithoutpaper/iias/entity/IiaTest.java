/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.iias.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IiaTest {
    
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
    public void testPersistIia() {
//        IiaOld iia = new IiaOld();
//        iia.setId("testId1");
//        iia.setStartDate(new Date());
//        iia.setEndDate(new Date());
//        iia.setModifDate(new Date());
//        Partner p1 = new Partner();
//        p1.setPartnerId(new PartnerId("testId1", "instId1", "unitId1"));
//        Partner p2 = new Partner();
//        p2.setPartnerId(new PartnerId("testId1", "instId1", "unitId2"));
//        List<Partner> partners = new ArrayList<>();
//        partners.add(p1);
//        partners.add(p2);
//        iia.setPartner(partners);
//        
//        this.tx.begin();
//        this.em.persist(iia);
//        this.tx.commit();
//        this.em.clear();
//        
//        IiaOld iiaFetched = em.find(IiaOld.class, "testId1");
//        assertNotNull(iiaFetched);
//        assertEquals("testId1", iiaFetched.getId());
//        assertEquals(2, iiaFetched.getPartner().size());
    }

}
