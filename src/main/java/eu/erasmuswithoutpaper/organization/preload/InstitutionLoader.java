package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.io.IOException;
import java.util.List;

public class InstitutionLoader extends AbstractStartupLoader {
    public static final String IKEA_OU1_ID = "8965F285-E763-IKEA-8163-C52C8B654035";
    public static final String IKEA_OU2_ID = "8965F285-E763-IKEA-8163-C52C8B654036";
    public static final String POMODORO_OU1_ID = "8965F285-E763-POMO-8163-C52C8B654030";
    
    @Override
    public void createDemoDataIkea() throws IOException {
        String otherIds = "[{'idType':'erasmus','idValue':'S Ikea01'},{'idType':'local','idValue':'IK1234'}]";
        String names = "[{'text':'IKEA universitet','lang':'sv'},{'text':'IKEA university','lang':'en'}]";
        String streetAddressForIkea = "{'addressLine':['Furniture street 1','1st floor'],'locality':'Assemble city','country':'se'}";
        String websiteUrlForIkea = "[{'text':'www.ikeauniversitet.se','lang':'sv'},{'text':'www.ikeauniversity.se','lang':'en'}]";
        String organizationUnit1Names = "[{'text':'Institutionen för psykologi','lang':'sv'},{'text':'Department of Psychology','lang':'en'}]";
        String streetAddressOrgUnit1 = "{'addressLine':['Furniture street 11'],'locality':'Assemble city','country':'se'}";
        String websiteUrlForOrgUnit1 = "[{'text':'www.ikeapsykologi.se','lang':'sv'},{'text':'www.ikeapsychology.se','lang':'en'}]";
        String organizationUnit2Names = "[{'text':'Institutionen för Datavetenskap','lang':'sv'},{'text':'Department of Computing Science','lang':'en'}]";
        String streetAddressOrgUnit2 = "{'addressLine':['Furniture street 12'],'locality':'Assemble city','country':'se'}";
        String otherIdsOrgUnit1 = "[{'idType':'erasmus','idValue':'S Ikea331'},{'idType':'local','idValue':'IKORG01'}]";
        String otherIdsOrgUnit2 = "[{'idType':'erasmus','idValue':'S Ikea332'},{'idType':'local','idValue':'IKORG02'}]";
        String organizationUnits="[{'id':'" + IKEA_OU1_ID + "','organizationUnitCode':'ikea.ou1.se','otherId':" + otherIdsOrgUnit1 + ",'name':" + organizationUnit1Names + ",'streetAddress':" + streetAddressOrgUnit1 + ",'websiteUrl':" + websiteUrlForOrgUnit1 + "},{'id':'" + IKEA_OU2_ID + "','organizationUnitCode':'ikea.ou2.se','otherId':" + otherIdsOrgUnit2 + ",'name':" + organizationUnit2Names + ",'streetAddress':" + streetAddressOrgUnit2 + "}]";
        persistInstitution("{'institutionId':'ikea.university.se','otherId':" + otherIds + ",'name':" + names + ",'streetAddress':" + streetAddressForIkea + ",'organizationUnits':" + organizationUnits + ",'websiteUrl':" + websiteUrlForIkea + "}");
    }
    
    @Override
    public void createDemoDataPomodoro() throws IOException {
        String otherIds = "[{'idType':'euc','idValue':'23654'},{'idType':'local','idValue':'POMO22'}]";
        String names = "[{'text':'Pomodoro Universitet','lang':'sv'},{'text':'Pomodoro University','lang':'en'}]";
        String streetAddressForPomodoro = "{'addressLine':['Pizza valley 12'],'locality':'Sunny town','country':'it'}";
        String websiteUrlForPomodoro = "[{'text':'www.pomodorouniversita.it','lang':'it'},{'text':'www.pomodorouniversity.it','lang':'en'}]";
        String organizationUnit1Names = "[{'text':'Institutionen för matematik och matematisk statistik','lang':'sv'},{'text':'Department of Mathematics and Mathematical Statistics','lang':'en'}]";
        String streetAddressOrgUnit1 = "{'addressLine':['Pizza valley 121'],'locality':'Sunny town','country':'it'}";
        String websiteUrlForOrgUnit1 = "[{'text':'www.pomodoromatematik.it','lang':'it'},{'text':'www.pomodoromath.it','lang':'en'}]";
        String otherIdsOrgUnit1 = "[{'idType':'euc','idValue':'23998'},{'idType':'local','idValue':'P5445'}]";
        String organizationUnits = "[{'id':'" + POMODORO_OU1_ID + "','organizationUnitCode':'pomodoro.ou1.it','otherId':" + otherIdsOrgUnit1 + ",'name':" + organizationUnit1Names + ",'streetAddress':" + streetAddressOrgUnit1 + ",'websiteUrl':" + websiteUrlForOrgUnit1 + "}]";
        persistInstitution("{'institutionId':'pomodoro.university.it','otherId':" + otherIds + ",'name':" + names + ",'streetAddress':" + streetAddressForPomodoro + ",'organizationUnits':" + organizationUnits + ",'websiteUrl':" + websiteUrlForPomodoro + "}");
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
