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
        String otherIdsIkea = "[{'idType':'erasmus','idValue':'S Ikea01'},{'idType':'local','idValue':'IK1234'}]";
        String namesIkea = "[{'text':'IKEA universitet','lang':'sv'},{'text':'IKEA university','lang':'en'}]";
        String organizationUnit1NamesIkea = "[{'text':'Institutionen för psykologi','lang':'sv'},{'text':'Department of Psychology','lang':'en'}]";
        String organizationUnit2NamesIkea = "[{'text':'Institutionen för Datavetenskap','lang':'sv'},{'text':'Department of Computing Science','lang':'en'}]";
        String otherIdsOrgUnit1 = "[{'idType':'erasmus','idValue':'S Ikea331'},{'idType':'local','idValue':'IKORG01'}]";
        String otherIdsOrgUnit2 = "[{'idType':'erasmus','idValue':'S Ikea332'},{'idType':'local','idValue':'IKORG02'}]";
        String organizationUnits="[{'organizationUnitId':'ikea.ou1.se','otherId':" + otherIdsOrgUnit1 + ",'name':" + organizationUnit1NamesIkea + ",'country':'se'},{'organizationUnitId':'ikea.ou2.se','otherId':" + otherIdsOrgUnit2 + ",'name':" + organizationUnit2NamesIkea + ",'country':'se'}]";
        persistInstitution("{'institutionId':'ikea.university.se','otherId':" + otherIdsIkea + ",'name':" + namesIkea + ",'country':'se','organizationUnits':" + organizationUnits +"}");
    }
    public void createDemoDataPomodoro() throws IOException {
        String otherIdsPomodoro = "[{'idType':'euc','idValue':'23654'},{'idType':'local','idValue':'POMO22'}]";
        String namesPomodoro = "[{'text':'Pomodoro Universitet','lang':'sv'},{'text':'Pomodoro University','lang':'en'}]";
        String organizationUnit1NamesPomodoro = "[{'text':'Institutionen för matematik och matematisk statistik','lang':'sv'},{'text':'Department of Mathematics and Mathematical Statistics','lang':'en'}]";
        String otherIdsOrgUnit1 = "[{'idType':'euc','idValue':'23998'},{'idType':'local','idValue':'P5445'}]";
        String organizationUnitsPomodoro="[{'organizationUnitId':'pomodoro.ou1.it','otherId':" + otherIdsOrgUnit1 + ",'name':" + organizationUnit1NamesPomodoro + ",'country':'se'}]";
        persistInstitution("{'institutionId':'pomodoro.university.it','otherId':" + otherIdsPomodoro + ",'name':" + namesPomodoro + ",'country':'it','organizationUnits':" + organizationUnitsPomodoro +"}");
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
