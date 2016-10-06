package eu.erasmuswithoutpaper.iias.preload;

import eu.erasmuswithoutpaper.iias.entity.Condition;
import eu.erasmuswithoutpaper.iias.entity.Iia;
import eu.erasmuswithoutpaper.iias.entity.Partner;
import eu.erasmuswithoutpaper.iias.entity.Term;
import eu.erasmuswithoutpaper.iias.entity.Title;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class IiaLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoData() {
        Condition condition = new Condition(123);
        
        List<String> mobilityTypeList = new ArrayList<>();
        mobilityTypeList.add("mt1");
        mobilityTypeList.add("mt2");
        condition.setMobilityType(mobilityTypeList);
        condition.setSubjectArea(12345);
        
        Term term = new Term();
        term.setEndDate(Date.from(Instant.now()));
        term.setStartDate(Date.from(Instant.now()));
        
        List<Title> titles = new ArrayList<>();

        Title seTitle = new Title();
        seTitle.setLang("se");
        seTitle.setTitle("Svensk titel");
        titles.add(seTitle);
        Title enTitle = new Title();
        enTitle.setLang("en");
        enTitle.setTitle("Engelsk titel");
        titles.add(enTitle);
                
        term.setTitle(titles);
        condition.setTerm(term);
        
        List<Condition> conditions = new ArrayList<>();
        conditions.add(condition);
        
        em.persist(new Iia(UUID.randomUUID().toString(), new Partner("institutionId1", "organizationUnitId1", conditions)));

        Condition condition2 = new Condition(234);
        Condition condition3 = new Condition(345);
        List<Condition> conditions2 = new ArrayList<>();
        conditions2.add(condition2);
        conditions2.add(condition3);
        em.persist(new Iia(UUID.randomUUID().toString(), new Partner("institutionId1", "organizationUnitId2", conditions2)));
    }
}
