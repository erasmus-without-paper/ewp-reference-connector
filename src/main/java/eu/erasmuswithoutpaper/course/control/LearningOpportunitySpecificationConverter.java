package eu.erasmuswithoutpaper.course.control;

import eu.erasmuswithoutpaper.api.courses.CoursesResponse;
import eu.erasmuswithoutpaper.api.types.academicterm.AcademicTerm;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToStringWithOptionalLang;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToXmlGregorianCalendar;
import eu.erasmuswithoutpaper.course.entity.Credit;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.datatype.DatatypeConfigurationException;

public class LearningOpportunitySpecificationConverter {

    public CoursesResponse.LearningOpportunitySpecification convertToLos(LearningOpportunitySpecification los, String loisBefore, String loisAfter, String losAtDate) {
        CoursesResponse.LearningOpportunitySpecification course = new CoursesResponse.LearningOpportunitySpecification();
        // TODO: add description?
        //course.getDescription().addAll();
        
        course.getTitle().addAll(convertToStringWithOptionalLang(los.getName()));
        
        course.setContains(convertToContains(los.getLearningOpportunitySpecifications()));
        
        // TODO: add ISCED code?
        //course.setIscedCode();
        
        course.setLosId(los.getLosCode());
        course.setSpecifies(convertToSpecifies(los.getLearningOpportunityInstances(), loisBefore, loisAfter));
        
        // TODO: add subject area?
        //course.setSubjectArea(los.get);
        
        course.setType(los.getType());
        
        // TODO: should API change to multilanguage URL
        course.setUrl(los.getUrl() == null || los.getUrl().isEmpty() ? null : los.getUrl().get(0).getText());
        
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

    private CoursesResponse.LearningOpportunitySpecification.Specifies convertToSpecifies(List<LearningOpportunityInstance> loi, String loisBefore, String loisAfter) {
        if (loi == null || loi.isEmpty()) {
            return null;
        }
        CoursesResponse.LearningOpportunitySpecification.Specifies specifies = new CoursesResponse.LearningOpportunitySpecification.Specifies();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance> loiList = loi.stream()
                .filter((l) -> {
                    if (loisBefore == null || loisBefore.isEmpty()) {
                        return true;
                    }
                    String endDate = sdf.format(l.getAcademicTerm().getEndDate());
                    return endDate.compareTo(loisBefore) < 0;
                })
                .filter((l) -> {
                    if (loisAfter == null || loisAfter.isEmpty()) {
                        return true;
                    }
                    String startDate = sdf.format(l.getAcademicTerm().getStartDate());
                    return startDate.compareTo(loisAfter) > 0;
                })
                .map((l) -> {
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
            
            converted.setLoiId("LOI" + l.getId());
            
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

    private List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit> convertToCredits(List<Credit> credits) {
        return credits.stream().map((c) -> {
            CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit credit = new CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit();
            credit.setLevel(c.getLevel());
            credit.setScheme(c.getScheme());
            credit.setValue(c.getValue());
            return credit;
        }).collect(Collectors.toList());
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
