package eu.erasmuswithoutpaper.iias.preload;

import eu.erasmuswithoutpaper.iias.entity.Condition;
import eu.erasmuswithoutpaper.iias.entity.Iia;
import eu.erasmuswithoutpaper.iias.entity.Partner;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class IiaLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoData() {
        Condition condition = new Condition(123);
        List<Condition> conditions = new ArrayList<>();
        conditions.add(condition);
        em.persist(new Iia("id1", new Partner("institutionId", "organizationUnitId", conditions)));
    }
}
