package eu.erasmuswithoutpaper.course.control;

import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.courses.CoursesResponse;
import eu.erasmuswithoutpaper.api.ounits.OunitsResponse;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LearningOpportunitySpecificationConverter {
    public OunitsResponse.Ounit convertToOunit(OrganizationUnit organizationUnit, String parentOrganizationUnitId) {
        OunitsResponse.Ounit ounit = new OunitsResponse.Ounit();
        ounit.getContact().addAll(convertToContact());
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

    private List<Contact> convertToContact() {
        List<Contact> contacts = new ArrayList<>();
        // TODO: get contact persons from DB?
        //List<Institution> institutionList = em.createNamedQuery(Coordinator.findByInstitutionId).setParameter("institutionId", heiId).getResultList();
        return contacts;
    }

    public CoursesResponse.LearningOpportunitySpecification convertToLos(LearningOpportunitySpecification los) {
        CoursesResponse.LearningOpportunitySpecification course = new CoursesResponse.LearningOpportunitySpecification();
        //course.getDescription().addAll();
        //course.getTitle().addAll();
        
        //course.setContains();
        //course.setIscedCode();
        //course.setLosId();
        //course.setSpecifies();
        //course.setSubjectArea();
        //course.setType();
        //course.setUrl();
        
        return course;
    }
}
