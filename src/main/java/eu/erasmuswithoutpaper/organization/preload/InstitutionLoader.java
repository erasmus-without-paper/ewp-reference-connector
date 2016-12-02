package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class InstitutionLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoDataIkea() throws IOException {
        String namesIkea = "[{'text':'IKEA universitet','lang':'sv'},{'text':'IKEA university','lang':'en'}]";
        String descriptionIkea = "[{'text':'Svensk beskrivning av IKEA universitet','lang':'sv'},{'text':'English description for IKEA university','lang':'en'}]";
        String organizationUnit1NamesIkea = "[{'text':'Institutionen för psykologi','lang':'sv'},{'text':'Department of Psychology','lang':'en'}]";
        String organizationUnit2NamesIkea = "[{'text':'Institutionen för Datavetenskap','lang':'sv'},{'text':'Department of Computing Science','lang':'en'}]";
        String descriptionUnit1NamesIkea = "[{'text':'En beskrivning av Institutionen för psykologi.','lang':'sv'},{'text':'Some description for Department of Psychology.','lang':'en'}]";
        String descriptionUnit2NamesIkea = "[{'text':'En beskrivning av Institutionen för Datavetenskap.','lang':'sv'},{'text':'Some description for Department of Computing Science.','lang':'en'}]";
        String organizationUnits="[{'organizationUnitId':'ikea.ou1.se','otherId':'oid1','name':" + organizationUnit1NamesIkea + ",'country':'se','description':" + descriptionUnit1NamesIkea + "},{'organizationUnitId':'ikea.ou2.se','otherId':'oid2','name':" + organizationUnit2NamesIkea + ",'country':'se','description':" + descriptionUnit2NamesIkea + "}]";
        persistInstitution("{'institutionId':'ikea.university.se','otherId':'aid1','name':" + namesIkea + ",'country':'se','description':" + descriptionIkea + ",'organizationUnits':" + organizationUnits +"}");
    }
    public void createDemoDataPomodoro() throws IOException {
        String namesPomodoro = "[{'text':'Pomodoro Universitet','lang':'sv'},{'text':'Pomodoro University','lang':'en'}]";
        String descriptionPomodore = "[{'text':'Svensk beskrivning av Pomodoro universitet','lang':'sv'},{'text':'English description for Pomodoro university','lang':'en'}]";
        String organizationUnit1NamesPomodoro = "[{'text':'Institutionen för matematik och matematisk statistik','lang':'sv'},{'text':'Department of Mathematics and Mathematical Statistics','lang':'en'}]";
        String descriptionUnit1NamesPomodoro = "[{'text':'En beskrivning av Institutionen för matematik och matematisk statistik.','lang':'sv'},{'text':'Some description for Department of Mathematics and Mathematical Statistics','lang':'en'}]";
        String organizationUnitsPomodoro="[{'organizationUnitId':'pomodoro.ou1.it','otherId':'oid1','name':" + organizationUnit1NamesPomodoro + ",'country':'se','description':" + descriptionUnit1NamesPomodoro + "}]";
        persistInstitution("{'institutionId':'pomodoro.university.it','otherId':'aid2','name':" + namesPomodoro + ",'country':'it','description':" + descriptionPomodore + ",'organizationUnits':" + organizationUnitsPomodoro +"}");
    }
    
    public boolean dataAlreadyExist() {
        List<Institution> institutionList = em.createNamedQuery(Institution.findAll).getResultList();
        return institutionList.size() > 0;
    }

    
    private void persistInstitution(String institutionJson) throws IOException {
        Institution institution = JsonHelper.mapToObject(Institution.class, institutionJson);
        em.persist(institution);
    }
}
