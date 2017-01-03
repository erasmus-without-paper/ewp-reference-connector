package eu.erasmuswithoutpaper.organization.control;

import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.ounits.OunitsResponse;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import eu.erasmuswithoutpaper.organization.entity.Coordinator;
import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class OrganizationUnitConverter {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public OunitsResponse.Ounit convertToOunit(OrganizationUnit organizationUnit, String parentOrganizationUnitId,  String parentInstitutionId) {
        OunitsResponse.Ounit ounit = new OunitsResponse.Ounit();
        ounit.getContact().addAll(convertToContact(parentInstitutionId, organizationUnit.getOrganizationUnitId()));
        ounit.getMobilityFactsheetUrl();
        ounit.getName().addAll(convertToStringWithOptionalLang(organizationUnit.getName()));
        ounit.getWebsiteUrl();
        //ounit.setMailingAddress();
        ounit.setOunitId(organizationUnit.getOrganizationUnitId());
        ounit.setParentOunitId(parentOrganizationUnitId);
        //ounit.setStreetAddress();
        
        return ounit;
    }
    
    private List<StringWithOptionalLang> convertToStringWithOptionalLang(List<LanguageItem> languageItems) {
        return
            languageItems.stream().map((name) -> {
                StringWithOptionalLang institutionName = new StringWithOptionalLang();
                institutionName.setLang(name.getLang());
                institutionName.setValue(name.getText());
                return institutionName;
            }).collect(Collectors.toList());
    }

    private List<Contact> convertToContact(String institutionId, String organizationUnitId) {
        Query query = em.createNamedQuery(Coordinator.findByInstAndOrgUnit).setParameter("institutionId", institutionId).setParameter("organizationUnitId", organizationUnitId);
        List<Coordinator> coordinators = query.getResultList();

        return
            coordinators.stream().map((coordinator) -> {
                Contact contact  = new Contact();
                StringWithOptionalLang name = new StringWithOptionalLang();
                name.setValue(coordinator.getPerson().getFirstNames() + " " + coordinator.getPerson().getLastName());
                contact.getContactName().add(name);
                // TODO: add more information
                return contact;
            }).collect(Collectors.toList());
    }
}
