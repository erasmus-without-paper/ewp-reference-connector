package eu.erasmuswithoutpaper.organization.control;

import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.ounits.OunitsResponse;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertFlexibleAddress;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToHttpWithOptionalLang;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToStringWithOptionalLang;
import eu.erasmuswithoutpaper.organization.entity.Coordinator;
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
        ounit.getMobilityFactsheetUrl().addAll(convertToHttpWithOptionalLang(organizationUnit.getFactsheetUrl()));
        ounit.getName().addAll(convertToStringWithOptionalLang(organizationUnit.getName()));
        ounit.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(organizationUnit.getWebsiteUrl()));
        ounit.setMailingAddress(convertFlexibleAddress(organizationUnit.getMailingAddress()));
        ounit.setOunitId(organizationUnit.getOrganizationUnitId());
        ounit.setParentOunitId(parentOrganizationUnitId);
        ounit.setStreetAddress(convertFlexibleAddress(organizationUnit.getStreetAddress()));
        
        return ounit;
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
