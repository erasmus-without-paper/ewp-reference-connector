package eu.erasmuswithoutpaper.institutions.preload;

import eu.erasmuswithoutpaper.institutions.entity.Institution;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class InstitutionLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoData() {
        em.persist(new Institution("id1", "se", "IKEA university", "http://ewp.ikea.university.se"));
    }
}
