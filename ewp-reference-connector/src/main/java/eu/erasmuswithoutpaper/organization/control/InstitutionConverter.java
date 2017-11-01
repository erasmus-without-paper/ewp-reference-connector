package eu.erasmuswithoutpaper.organization.control;

import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse;
import eu.erasmuswithoutpaper.api.registry.OtherHeiId;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToFlexibleAddress;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToHttpWithOptionalLang;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToStringWithOptionalLang;
import eu.erasmuswithoutpaper.organization.entity.ContactDetails;
import eu.erasmuswithoutpaper.organization.entity.FactSheet;
import eu.erasmuswithoutpaper.organization.entity.IdentificationItem;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class InstitutionConverter {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public List<InstitutionsResponse.Hei> convertToHei(List<Institution> institutionList) {
        return
            institutionList.stream().map((institution) -> {
                InstitutionsResponse.Hei hei = new InstitutionsResponse.Hei();

                hei.getContact().addAll(convertToContact(institution.getInstitutionId()));
                hei.getName().addAll(convertToStringWithOptionalLang(institution.getName()));
                hei.getOtherId().addAll(convertToOtherHeiId(institution.getOtherId()));
                hei.getOunitId().addAll(getOrganizationUnitIds(institution.getOrganizationUnits()));
                hei.setHeiId(institution.getInstitutionId());
                hei.setLogoUrl(institution.getLogoUrl());
                
                // TODO: add this when it exist in the schema file
                //hei.setAbbreviation(institution.getAbbreviation());
                
                // SKIP: no root ounit is available
                //hei.setRootOunitId();
                
                FactSheet factSheet = institution.getFactSheet();
                if (factSheet != null) {
                    ContactDetails contactDetails = factSheet.getContactDetails();
                    hei.getMobilityFactsheetUrl().addAll(convertToHttpWithOptionalLang(factSheet.getUrl()));
                    if (contactDetails != null) {
                        hei.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(contactDetails.getUrl()));
                        hei.setMailingAddress(convertToFlexibleAddress(contactDetails.getMailingAddress()));
                        hei.setStreetAddress(convertToFlexibleAddress(contactDetails.getStreetAddress()));
                    }
                    
                }
                
                return hei;
            }).collect(Collectors.toList());
    }
    
    private List<OtherHeiId> convertToOtherHeiId(List<IdentificationItem> identificationItems) {
        return
            identificationItems.stream().map((identificationItem) -> {
                OtherHeiId otherHeiId = new OtherHeiId();
                otherHeiId.setType(identificationItem.getIdType());
                otherHeiId.setValue(identificationItem.getIdValue());
                return otherHeiId;
            }).collect(Collectors.toList());
    }
    
    private List<Contact> convertToContact(String institutionId) {
        Query query = em.createNamedQuery(eu.erasmuswithoutpaper.organization.entity.Contact.findByInstWithNoOrgUnit).setParameter("institutionId", institutionId);
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

    private List<String> getOrganizationUnitIds(List<OrganizationUnit> organizationUnits) {
        List<String> organizationUnitIds = new ArrayList<>();
        organizationUnits.stream().map((ou) -> {
            organizationUnitIds.add(ou.getId());
            return ou;
        }).forEachOrdered((ou) -> {
            organizationUnitIds.addAll(getOrganizationUnitIds(ou.getOrganizationUnits()));
        });
        return organizationUnitIds;
    }
}
