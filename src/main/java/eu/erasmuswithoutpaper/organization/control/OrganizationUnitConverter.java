package eu.erasmuswithoutpaper.organization.control;

import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.ounits.OunitsResponse;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToFlexibleAddress;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToHttpWithOptionalLang;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToStringWithOptionalLang;
import eu.erasmuswithoutpaper.organization.entity.ContactDetails;
import eu.erasmuswithoutpaper.organization.entity.FactSheet;
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
        ounit.getContact().addAll(convertToContact(parentInstitutionId, organizationUnit.getId()));
        ounit.getName().addAll(convertToStringWithOptionalLang(organizationUnit.getName()));
        ounit.setOunitId(organizationUnit.getId());
        ounit.setParentOunitId(parentOrganizationUnitId);
        ounit.setOunitCode(organizationUnit.getOrganizationUnitCode());

        FactSheet factSheet = organizationUnit.getFactSheet();
        if (factSheet != null) {
            ContactDetails contactDetails = factSheet.getContactDetails();
            ounit.getMobilityFactsheetUrl().addAll(convertToHttpWithOptionalLang(factSheet.getUrl()));
            if (contactDetails != null) {
                ounit.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(contactDetails.getUrl()));
                ounit.setMailingAddress(convertToFlexibleAddress(contactDetails.getMailingAddress()));
                ounit.setStreetAddress(convertToFlexibleAddress(contactDetails.getStreetAddress()));
            }

        }

        return ounit;
    }
    
    private List<Contact> convertToContact(String institutionId, String organizationUnitId) {
        Query query = em.createNamedQuery(eu.erasmuswithoutpaper.organization.entity.Contact.findByInstAndOrgUnit).setParameter("institutionId", institutionId).setParameter("organizationUnitId", organizationUnitId);
        List<eu.erasmuswithoutpaper.organization.entity.Contact> contacts = query.getResultList();

        return
            contacts.stream().map((cont) -> {
                Contact contact  = new Contact();
                StringWithOptionalLang name = new StringWithOptionalLang();
                name.setValue(cont.getPerson().getFirstNames() + " " + cont.getPerson().getLastName());
                contact.getContactName().add(name);
                // TODO: add more information
                return contact;
            }).collect(Collectors.toList());
    }
}
