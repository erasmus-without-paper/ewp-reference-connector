package eu.erasmuswithoutpaper.course.control;

import eu.erasmuswithoutpaper.api.courses.CoursesResponse;
import eu.erasmuswithoutpaper.api.types.academicterm.AcademicTerm;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToMultilineStringWithOptionalLang;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToStringWithOptionalLang;
import static eu.erasmuswithoutpaper.common.control.ConverterHelper.convertToXmlGregorianCalendar;
import eu.erasmuswithoutpaper.course.entity.Credit;
import eu.erasmuswithoutpaper.course.entity.GradingScheme;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecificationType;
import eu.erasmuswithoutpaper.mobility.entity.ResultDistribution;
import eu.erasmuswithoutpaper.mobility.entity.ResultDistributionCategory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.datatype.DatatypeConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LearningOpportunitySpecificationConverter {
    private static final Logger logger = LoggerFactory.getLogger(LearningOpportunitySpecificationConverter.class);

    public CoursesResponse.LearningOpportunitySpecification convertToLos(LearningOpportunitySpecification los, String loisBefore, String loisAfter, String losAtDate) {
        CoursesResponse.LearningOpportunitySpecification course = new CoursesResponse.LearningOpportunitySpecification();
        course.getDescription().addAll(convertToMultilineStringWithOptionalLang(los.getDescription()));
        course.getTitle().addAll(convertToStringWithOptionalLang(los.getName()));
        
        course.setContains(convertToContains(los.getLearningOpportunitySpecifications()));
        course.setIscedCode(los.getIscedf());
        course.setLosId(LearningOpportunitySpecificationType.abbreviation(los.getType()) + "/" + los.getId());
        course.setLosCode(los.getLosCode());
        course.setSpecifies(convertToSpecifies(los.getLearningOpportunityInstances(), LearningOpportunitySpecificationType.loiAbbreviation(los.getType()), loisBefore, loisAfter));
        course.setSubjectArea(los.getSubjectArea());
        course.setType(LearningOpportunitySpecificationType.displayName(los.getType()));
        
        if (los.getUrl() != null) {
            course.getUrl().addAll(ConverterHelper.convertToHttpWithOptionalLang(los.getUrl()));
        }
        
        return course;
    }
    
    private CoursesResponse.LearningOpportunitySpecification.Contains convertToContains(List<LearningOpportunitySpecification> los) {
        if (los == null || los.isEmpty()) {
            return null;
        }
        CoursesResponse.LearningOpportunitySpecification.Contains contains = new CoursesResponse.LearningOpportunitySpecification.Contains();
        contains.getLosId().addAll(los.stream().map(LearningOpportunitySpecification::getLosCode).collect(Collectors.toList()));
        
        return contains;
    }

    private CoursesResponse.LearningOpportunitySpecification.Specifies convertToSpecifies(List<LearningOpportunityInstance> loi, String loiAbbreviation, String loisBefore, String loisAfter) {
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
                logger.error("Can't convert date", ex);
            }
            
            converted.setEngagementHours(l.getEngagementHours());
            converted.setGradingScheme(convertTotGradingScheme(l.getGradingScheme()));
            converted.setLanguageOfInstruction(l.getLanguageOfInstruction());
            converted.setLoiId(loiAbbreviation + "/" + l.getId());
            converted.setResultDistribution(convertToResultDistribution(l.getResultDistribution()));
            try {
                converted.setStart(convertToXmlGregorianCalendar(l.getAcademicTerm().getStartDate()));
            } catch (DatatypeConfigurationException ex) {
                logger.error("Can't convert date", ex);
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
        converted.setAcademicYearId(academicTerm.getAcademicYear().getAcademicYear());
        try {
            converted.setEndDate(convertToXmlGregorianCalendar(academicTerm.getEndDate()));
        } catch (DatatypeConfigurationException ex) {
            logger.error("Can't convert date", ex);
        }
        
        return converted;
    }

    private CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.GradingScheme convertTotGradingScheme(GradingScheme gradingScheme) {
        if (gradingScheme == null) {
            return null;
        }
        CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.GradingScheme converted = new CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.GradingScheme();
        converted.getDescription().addAll(convertToMultilineStringWithOptionalLang(gradingScheme.getDescription()));
        converted.getLabel().addAll(convertToStringWithOptionalLang(gradingScheme.getLabel()));
        
        return converted;
    }

    private CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution convertToResultDistribution(ResultDistribution resultDistribution) {
        if (resultDistribution == null) {
            return null;
        }
        CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution converted = new CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution();
        converted.getCategory().addAll(convertToCategory(resultDistribution.getResultDistributionCategory()));
        converted.getDescription().addAll(convertToMultilineStringWithOptionalLang(resultDistribution.getDescription()));
        
        return converted;
    }

    private List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category> convertToCategory(List<ResultDistributionCategory> resultDistributionCategory) {
        if (resultDistributionCategory == null) {
            return new ArrayList<>();
        }
        
        return resultDistributionCategory.stream().map(cat -> {
            CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category category = new CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category();
            category.setLabel(cat.getLabel());
            category.setCount(cat.getCount());
            return category;
        }).collect(Collectors.toList());
    }
}
