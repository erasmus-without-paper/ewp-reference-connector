package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.internal.StartupLoader;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class InstitutionLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoData(StartupLoader.University university) throws IOException {
        switch (university) {
            case IKEA_U:
                String namesIkea = "[{'text':'IKEA universitet','lang':'sv'},{'text':'IKEA university','lang':'en'}]";
                String descriptionIkea = "[{'text':'Svensk beskrivning av IKEA universitet','lang':'sv'},{'text':'English description for IKEA university','lang':'en'}]";
                String organizationUnit1NamesIkea = "[{'text':'IKEA Organisationsenhet 1','lang':'sv'},{'text':'IKEA Organizations Unit 1','lang':'en'}]";
                String organizationUnit2NamesIkea = "[{'text':'IKEA Organisationsenhet 2','lang':'sv'},{'text':'IKEA Organizations Unit 2','lang':'en'}]";
                String descriptionUnit1NamesIkea = "[{'text':'Beskrivning av org unit 1','lang':'sv'},{'text':'Description for Org Unit 1','lang':'en'}]";
                String descriptionUnit2NamesIkea = "[{'text':'Beskrivning av org unit 2','lang':'sv'},{'text':'Description for Org Unit 2','lang':'en'}]";
                String organizationUnits="[{'organizationUnitId':'ikea.ou1.se','otherId':'oid1','name':" + organizationUnit1NamesIkea + ",'country':'se','description':" + descriptionUnit1NamesIkea + "},{'organizationUnitId':'ikea.ou2.se','otherId':'oid2','name':" + organizationUnit2NamesIkea + ",'country':'se','description':" + descriptionUnit2NamesIkea + "}]";
                persistInstitution("{'institutionId':'ikea.university.se','otherId':'aid1','name':" + namesIkea + ",'country':'se','description':" + descriptionIkea + ",'organizationUnits':" + organizationUnits +"}");
                break;
            case POMODORO_U:
                String namesPomodoro = "[{'text':'Pomodoro Universitet','lang':'sv'},{'text':'Pomodoro University','lang':'en'}]";
                String descriptionPomodore = "[{'text':'Svensk beskrivning av Pomodoro universitet','lang':'sv'},{'text':'English description for Pomodoro university','lang':'en'}]";
                String organizationUnit1NamesPomodoro = "[{'text':'Pomodoro Organisationsenhet 1','lang':'sv'},{'text':'Pomodoro Organizations Unit 1','lang':'en'}]";
                String descriptionUnit1NamesPomodoro = "[{'text':'Beskrivning av org unit 1','lang':'sv'},{'text':'Description for Org Unit 1','lang':'en'}]";
                String organizationUnitsPomodoro="[{'organizationUnitId':'pomodoro.ou1.it','otherId':'oid1','name':" + organizationUnit1NamesPomodoro + ",'country':'se','description':" + descriptionUnit1NamesPomodoro + "}]";
                persistInstitution("{'institutionId':'pomodoro.university.it','otherId':'aid2','name':" + namesPomodoro + ",'country':'it','description':" + descriptionPomodore + ",'organizationUnits':" + organizationUnitsPomodoro +"}");
                break;
        }
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
