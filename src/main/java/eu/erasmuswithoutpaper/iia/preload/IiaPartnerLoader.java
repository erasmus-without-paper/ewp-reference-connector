
package eu.erasmuswithoutpaper.iia.preload;

import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Coordinator;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class IiaPartnerLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        persistIiaPartner("{'iiaId':'iiaId001'}", getInstitution("ikea.university.se"), getOrganizationUnit("ikea.ou1.se"), getCoordinators("ikea.university.se", "ikea.ou1.se"));
    }
    public void createDemoDataPomodoro() throws IOException {
        createDemoDataIkea(); //TODO Insert data
    }

    private void persistIiaPartner(String iiaPartnerJson, Institution institution, OrganizationUnit organizationUnit, List<Coordinator> coordinators) throws IOException {
        IiaPartner iiaPartner = JsonHelper.mapToObject(IiaPartner.class, iiaPartnerJson);
        iiaPartner.setInstitution(institution);
        iiaPartner.setOrganizationUnit(organizationUnit);
        iiaPartner.setCoordinators(coordinators);
        em.persist(iiaPartner);
    }
    
    private List<Coordinator> getCoordinators(String institutionId, String orgUnitId) throws IOException {
        Query query = em.createNamedQuery(Coordinator.findByInstAndOrgUnit).setParameter("institutionId", institutionId).setParameter("organizationUnitId", orgUnitId);
        List<Coordinator> coordinators = query.getResultList();

        return coordinators;
    }

    private Institution getInstitution(String institutionId) throws IOException {
        Query query = em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", institutionId);
        List<Institution> institutionList = query.getResultList();
        if (institutionList.size() != 1) {
           throw new IllegalArgumentException("Institution Id " + institutionId + " doesn't return an unique Institution.");
        }

        return institutionList.get(0);
    }

    private OrganizationUnit getOrganizationUnit(String organizationUnitId) throws IOException {
        Query query = em.createNamedQuery(OrganizationUnit.findByOrganizationUnitId).setParameter("organizationUnitId", organizationUnitId);
        List<OrganizationUnit> organizationUnitList = query.getResultList();
        if (organizationUnitList.size() != 1) {
           throw new IllegalArgumentException("organization unit Id " + organizationUnitId + " doesn't return an unique organization unit.");
        }

        return organizationUnitList.get(0);
    }
}
