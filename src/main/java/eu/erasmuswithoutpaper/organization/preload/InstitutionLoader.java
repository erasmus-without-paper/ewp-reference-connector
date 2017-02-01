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
        String streetAddressForIkea = "{'addressLine':['Furniture street 1','1st floor'],'postalCode':'80845','locality':'Assemble city','country':'se'}";
        String websiteUrlForIkea = "[{'text':'www.ikeauniversitet.se','lang':'sv'},{'text':'www.ikeauniversity.se','lang':'en'}]";
        String organizationUnit1Names = "[{'text':'Institutionen för psykologi','lang':'sv'},{'text':'Department of Psychology','lang':'en'}]";
        String streetAddressOrgUnit1 = "{'addressLine':['Furniture street 11'],'buildingNumber':'4','floor':'1','postalCode':'80920','locality':'Assemble city','country':'se'}";
        String websiteUrlForOrgUnit1 = "[{'text':'www.ikeapsykologi.se','lang':'sv'},{'text':'www.ikeapsychology.se','lang':'en'}]";
        String organizationUnit2Names = "[{'text':'Institutionen för Datavetenskap','lang':'sv'},{'text':'Department of Computing Science','lang':'en'}]";
        String streetAddressOrgUnit2 = "{'addressLine':['Furniture street 12'],'floor':'2','postalCode':'80920','locality':'Assemble city','country':'se'}";
        String otherIdsOrgUnit1 = "[{'idType':'erasmus','idValue':'S Ikea331'},{'idType':'local','idValue':'IKORG01'}]";
        String otherIdsOrgUnit2 = "[{'idType':'erasmus','idValue':'S Ikea332'},{'idType':'local','idValue':'IKORG02'}]";
        String factSheetOrgUnit1 = "{'url':[{'text':'http://mobility-factsheet.ikea.se/orgunit1','lang':'en'}],'contactDetails':{'streetAddress':" + streetAddressOrgUnit1 + ",'url':" + websiteUrlForOrgUnit1 + ",'phoneNumber':{'e164':'+4579145656'}}}";
        String factSheetOrgUnit2 = "{'url':[{'text':'http://mobility-factsheet.ikea.se/orgunit2','lang':'en'}],'contactDetails':{'streetAddress':" + streetAddressOrgUnit2 + ",'phoneNumber':{'e164':'+4579145664'}}}";
        String organizationUnits="[{'id':'" + IKEA_OU1_ID + "','organizationUnitCode':'ikea.ou1.se','otherId':" + otherIdsOrgUnit1 + ",'name':" + organizationUnit1Names + ",'factSheet':" + factSheetOrgUnit1 + "},{'id':'" + IKEA_OU2_ID + "','organizationUnitCode':'ikea.ou2.se','otherId':" + otherIdsOrgUnit2 + ",'name':" + organizationUnit2Names + ",'factSheet':" + factSheetOrgUnit2 + "}]";
        String factSheetInstitution = "{'url':[{'text':'http://mobility-factsheet.ikea.se/ikea','lang':'en'}],'contactDetails':{'streetAddress':" + streetAddressForIkea + ",'url':" + websiteUrlForIkea + ",'email':['info@ikea.university.se'],'mailingAddress':{'postOfficeBox':'Box 123'},'phoneNumber':{'e164':'+4579123245'},'faxNumber':{'e164':'+45791232457'}}}";
        persistInstitution("{'institutionId':'ikea.university.se','otherId':" + otherIds + ",'name':" + names + ",'organizationUnits':" + organizationUnits + ",'factSheet':" + factSheetInstitution + "}");
    }
    
    @Override
    public void createDemoDataPomodoro() throws IOException {
        String otherIds = "[{'idType':'euc','idValue':'23654'},{'idType':'local','idValue':'POMO22'}]";
        String names = "[{'text':'Pomodoro Universitet','lang':'sv'},{'text':'Pomodoro University','lang':'en'}]";
        String streetAddressForPomodoro = "{'addressLine':['Pizza valley 12'],'postalCode':'55667','locality':'Sunny town','country':'it'}";
        String websiteUrlForPomodoro = "[{'text':'www.pomodorouniversita.it','lang':'it'},{'text':'www.pomodorouniversity.it','lang':'en'}]";
        String organizationUnit1Names = "[{'text':'Institutionen för matematik och matematisk statistik','lang':'sv'},{'text':'Department of Mathematics and Mathematical Statistics','lang':'en'}]";
        String streetAddressOrgUnit1 = "{'addressLine':['Pizza valley 121'],'floor':'3','postalCode':'55668','locality':'Sunny town','country':'it'}";
        String websiteUrlForOrgUnit1 = "[{'text':'www.pomodoromatematik.it','lang':'it'},{'text':'www.pomodoromath.it','lang':'en'}]";
        String otherIdsOrgUnit1 = "[{'idType':'euc','idValue':'23998'},{'idType':'local','idValue':'P5445'}]";
        String factSheetOrgUnit1 = "{'url':[{'text':'http://mobility-factsheet.pomodoro.it/orgunit1','lang':'en'}],'contactDetails':{'streetAddress':" + streetAddressOrgUnit1 + ",'url':" + websiteUrlForOrgUnit1 + "}}";
        String organizationUnits = "[{'id':'" + POMODORO_OU1_ID + "','organizationUnitCode':'pomodoro.ou1.it','otherId':" + otherIdsOrgUnit1 + ",'name':" + organizationUnit1Names + ",'factSheet':" + factSheetOrgUnit1 + "}]";
        String factSheetInstitution = "{'url':[{'text':'http://mobility-factsheet.pomodoro.it/orgunit1','lang':'en'}],'contactDetails':{'streetAddress':" + streetAddressForPomodoro + ",'url':" + websiteUrlForPomodoro + ",'phoneNumber':{'e164':'+567897891'}}}";
        persistInstitution("{'institutionId':'pomodoro.university.it','otherId':" + otherIds + ",'name':" + names + ",'organizationUnits':" + organizationUnits + ",'factSheet':" + factSheetInstitution + "}");
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
