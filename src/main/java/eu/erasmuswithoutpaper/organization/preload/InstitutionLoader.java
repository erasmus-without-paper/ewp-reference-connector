package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.internal.StartupLoader;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.io.IOException;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class InstitutionLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoData(StartupLoader.University university) throws IOException {
        switch (university) {
            case IKEA_U:
                
                String organizationUnits="[{'organizationUnitId':'id1','otherId':'oid1','name':'Org unit 1','country':'se','description':'testar detta'},{'organizationUnitId':'id2','otherId':'oid2','name':'Org unit 2','country':'se','description':'testar detta igen'}]";
                persistInstitution("{'otherId':'aid1','name':'IKEA university','country':'se','description':'http://ewp.ikea.university.se','organinazationUnits':" + organizationUnits +"}");
            case POMODORO_U:
                persistInstitution("{'otherId':'aid2','name':'Pomodoro University','country':'it','description':'http://ewp.pomodoro.ac.it'}");

        }
    }
    private void persistInstitution(String institutionJson) throws IOException {
        Institution institution = JsonHelper.mapToObject(Institution.class, institutionJson);
        institution.setInstitutionId(UUID.randomUUID().toString());
        em.persist(institution);
    }
}
