package eu.erasmuswithoutpaper.course.control;

import eu.erasmuswithoutpaper.api.courses.CoursesResponse;
import eu.erasmuswithoutpaper.api.types.academicterm.AcademicTerm;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToStringWithOptionalLang;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToXmlGregorianCalendar;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.datatype.DatatypeConfigurationException;

public class LearningOpportunitySpecificationConverter {

    public CoursesResponse.LearningOpportunitySpecification convertToLos(LearningOpportunitySpecification los) {
        CoursesResponse.LearningOpportunitySpecification course = new CoursesResponse.LearningOpportunitySpecification();
        // TODO: add description?
        //course.getDescription().addAll();
        
        course.getTitle().addAll(convertToStringWithOptionalLang(los.getName()));
        
        course.setContains(convertToContains(los.getLearningOpportunitySpecifications()));
        
        // TODO: add ISCED code?
        //course.setIscedCode();
        
        course.setLosId(los.getLosCode());
        course.setSpecifies(convertToSpecifies(los.getLearningOpportunityInstances()));
        //course.setSubjectArea();
        //course.setType();
        //course.setUrl();
        
        return course;
    }

    private CoursesResponse.LearningOpportunitySpecification.Contains convertToContains(List<LearningOpportunitySpecification> los) {
        if (los == null || los.isEmpty()) {
            return null;
        }
        CoursesResponse.LearningOpportunitySpecification.Contains contains = new CoursesResponse.LearningOpportunitySpecification.Contains();
        contains.getLosId().addAll(los.stream().map((l) -> l.getLosCode()).collect(Collectors.toList()));
        
        return contains;
    }

    private CoursesResponse.LearningOpportunitySpecification.Specifies convertToSpecifies(List<LearningOpportunityInstance> loi) {
        if (loi == null || loi.isEmpty()) {
            return null;
        }
        CoursesResponse.LearningOpportunitySpecification.Specifies specifies = new CoursesResponse.LearningOpportunitySpecification.Specifies();
        
        List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance> loiList = loi.stream().map((l) -> {
            CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance converted = new CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance();
            converted.getCredit().addAll(convertToCredits(l.getCredits()));
            converted.setAcademicTerm(convertToAcademicTerm(l.getAcademicTerm()));
            try {
                converted.setEnd(convertToXmlGregorianCalendar(l.getAcademicTerm().getEndDate()));
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(LearningOpportunitySpecificationConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // TODO: add this?
            //converted.setEngagementHours(BigDecimal.ONE);
            
            // TODO: add grading scheme
            //converted.setGradingScheme(value);
            
            // TODO: add?
            //converted.setLanguageOfInstruction(value);
            
            converted.setLoiId("MISSING IN DB MODEL");
            
            // TODO: add result distribution
            //converted.setResultDistribution(value);
            try {
                converted.setStart(convertToXmlGregorianCalendar(l.getAcademicTerm().getStartDate()));
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(LearningOpportunitySpecificationConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            return converted;
        }).collect(Collectors.toList());
        
        specifies.getLearningOpportunityInstance().addAll(loiList);
        
        return specifies;
    }

    private List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit> convertToCredits(BigDecimal credits) {
        List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit> loiCredits = new ArrayList<>();
        CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit loiCredit = new CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit();
        // TODO: replace with content from DB
        loiCredit.setLevel("Master");
        loiCredit.setScheme("ECTS");
        loiCredit.setValue(credits);
        loiCredits.add(loiCredit);
        
        return loiCredits;
    }

    private AcademicTerm convertToAcademicTerm(eu.erasmuswithoutpaper.course.entity.AcademicTerm academicTerm) {
        AcademicTerm converted = new AcademicTerm();
        converted.getDisplayName().addAll(convertToStringWithOptionalLang(academicTerm.getDispName()));
        converted.setAcademicYearId(academicTerm.getAcademicTermId());
        try {
            converted.setEndDate(convertToXmlGregorianCalendar(academicTerm.getEndDate()));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(LearningOpportunitySpecificationConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return converted;
    }
}
