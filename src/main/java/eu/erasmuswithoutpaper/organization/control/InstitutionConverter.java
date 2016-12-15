package eu.erasmuswithoutpaper.organization.control;

import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse;
import eu.erasmuswithoutpaper.api.registry.OtherHeiId;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import eu.erasmuswithoutpaper.organization.entity.IdentificationItem;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InstitutionConverter {
    public List<InstitutionsResponse.Hei> convertToHei(List<Institution> institutionList) {
        return
            institutionList.stream().map((institution) -> {
                InstitutionsResponse.Hei hei = new InstitutionsResponse.Hei();

                hei.getContact().addAll(convertToContact());
                // TODO: get URL:s
                hei.getMobilityFactsheetUrl();
                hei.getName().addAll(convertToStringWithOptionalLang(institution.getName()));
                hei.getOtherId().addAll(convertToOtherHeiId(institution.getOtherId()));
                hei.getOunitId().addAll(getOrganizationUnitIds(institution.getOrganizationUnits()));
                // TODO: sit URL:S
                hei.getWebsiteUrl();
                
                hei.setCountry(institution.getCountry());
                hei.setHeiId(institution.getInstitutionId());
                // TODO: set LOGO URL
                //hei.setLogoUrl("");
                // TODO: set mail address
                //hei.setMailingAddress();
                // TODO: set street address
                //hei.setStreetAddress();
                
                return hei;
            }).collect(Collectors.toList());
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

    private List<OtherHeiId> convertToOtherHeiId(List<IdentificationItem> identificationItems) {
        return
            identificationItems.stream().map((identificationItem) -> {
                OtherHeiId otherHeiId = new OtherHeiId();
                otherHeiId.setType(identificationItem.getIdType());
                otherHeiId.setValue(identificationItem.getIdValue());
                return otherHeiId;
            }).collect(Collectors.toList());
    }
    
    private List<Contact> convertToContact() {
        List<Contact> contacts = new ArrayList<>();
        // TODO: get contact persons from DB?
        //List<Institution> institutionList = em.createNamedQuery(Coordinator.findByInstitutionId).setParameter("institutionId", heiId).getResultList();
        return contacts;
    }

    private List<String> getOrganizationUnitIds(List<OrganizationUnit> organizationUnits) {
        return 
            organizationUnits.stream().map((organizationUnit) -> {
                return organizationUnit.getOrganizationUnitId();
            }).collect(Collectors.toList());
    }
}
