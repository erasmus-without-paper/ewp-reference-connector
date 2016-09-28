package eu.erasmuswithoutpaper.institutions.preload;

import eu.erasmuswithoutpaper.institutions.entity.Institution;
import eu.erasmuswithoutpaper.internal.StartupLoader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class InstitutionLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoData(StartupLoader.University university) {
        switch (university) {
            case IKEA_U:
                em.persist(new Institution("id1", "se", "IKEA university", "http://ewp.ikea.university.se"));
            case POMODORO_U:
                em.persist(new Institution("id2", "it", "Pomodoro University", "http://ewp.pomodoro.ac.it"));

        }
    }
}
