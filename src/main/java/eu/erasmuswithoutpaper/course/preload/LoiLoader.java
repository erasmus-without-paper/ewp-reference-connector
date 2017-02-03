package eu.erasmuswithoutpaper.course.preload;

import eu.erasmuswithoutpaper.course.entity.AcademicTerm;
import eu.erasmuswithoutpaper.course.entity.AcademicYear;
import eu.erasmuswithoutpaper.course.entity.GradingScheme;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance;
import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;

public class LoiLoader extends AbstractStartupLoader {
    public static final String IKEA_LOI1_ID = "8965F285-E763-IKEA-8163-C52C8B654032";
    public static final String POMODORO_LOI1_ID = "8965F285-E763-POMO-8163-C52C8B654030";
    
    @Override
    public void createDemoDataIkea() throws IOException {
        
        GradingScheme gs1 = persistGradingScheme("{'label':[{'text':'AF','lang':'en'}],'description':[{'text':'A,B,C,D,E = godkänd, F = underkänd','lang':'sv'},{'text':'A,B,C,D,E = passed, F = failed','lang':'en'}]}");
        GradingScheme gs2 = persistGradingScheme("{'label':[{'text':'D','lang':'en'}],'description':[{'text':'G = godkänd, U = underkänd','lang':'sv'},{'text':'G = passed, U = failed','lang':'en'}]}");
        GradingScheme gs3 = persistGradingScheme("{'label':[{'text':'IG','lang':'en'}],'description':[{'text':'G = godkänd, IG = underkänd','lang':'sv'},{'text':'G = passed, IG = failed','lang':'en'}]}");

        String resultDistribution1 = "{'resultDistributionCategory':[{'label':'A','count':'4'},{'label':'B','count':'5'},{'label':'C','count':'12'},{'label':'F','count':'2'}],'description':[{'text':'Grades A-E have passed','lang':'en'},{'text':'Betyg A-E har godkänt','lang':'sv'}]}";
        String resultDistribution2 = "{'resultDistributionCategory':[{'label':'A','count':'1'},{'label':'B','count':'3'},{'label':'D','count':'6'},{'label':'E','count':'3'}],'description':[{'text':'Grades A-E have passed','lang':'en'},{'text':'Betyg A-E har godkänt','lang':'sv'}]}";
        
        // Credits
        String credit1 = "[{'scheme':'ects','level':'Bachelor','value':'5'},{'scheme':'hp','level':'Bachelor','value':'10'}]";
        String credit2 = "[{'scheme':'ects','level':'Bachelor','value':'7.5'}]";
        String credit3 = "[{'scheme':'ects','level':'Bachelor','value':'15'},{'scheme':'hp','level':'Bachelor','value':'30'}]";
        String credit4 = "[{'scheme':'ects','level':'Master','value':'15'},{'scheme':'hp','level':'Bachelor','value':'30'}]";
        String credit5 = "[{'scheme':'ects','level':'Bachelor','value':'3.5'},{'scheme':'hp','level':'Bachelor','value':'7'}]";
        String credit6 = "[{'scheme':'ects','level':'Bachelor','value':'4'}]";
        
        // Course1
        LearningOpportunitySpecification course1Los = getLos("IU001");
        LearningOpportunityInstance course1Loi1 = createLearningOppInst("{'credits':" + credit1 + ",'languageOfInstruction':'en','engagementHours':'40','resultDistribution':" + resultDistribution1 + "}", getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"), gs1);
        LearningOpportunityInstance course1Loi2 = createLearningOppInst("{'credits':" + credit2 + ",'languageOfInstruction':'en','engagementHours':'60','resultDistribution':" + resultDistribution2 + "}", getAcademicTerm(getAcademicYear("2015", "2016"), "Spring"), gs1);
        LearningOpportunityInstance course1Loi3 = createLearningOppInst("{'id':'" + IKEA_LOI1_ID + "','credits':" + credit2 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2016", "2017"), "Fall"), gs1);
        List<LearningOpportunityInstance> course1LoiList = new ArrayList<>();
        course1LoiList.add(course1Loi1);
        course1LoiList.add(course1Loi2);
        course1LoiList.add(course1Loi3);
        course1Los.setLearningOpportunityInstances(course1LoiList);
        em.merge(course1Los);
        
        // Course2
        LearningOpportunitySpecification course2Los = getLos("IU002");
        LearningOpportunityInstance course2Loi1 = createLearningOppInst("{'credits':" + credit4 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"), gs2);
        LearningOpportunityInstance course2Loi2 = createLearningOppInst("{'credits':" + credit4 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Spring"), gs2);
        List<LearningOpportunityInstance> course2LoiList = new ArrayList<>();
        course2LoiList.add(course2Loi1);
        course2LoiList.add(course2Loi2);
        course2Los.setLearningOpportunityInstances(course2LoiList);
        em.merge(course2Los);
        
        // Module1
        LearningOpportunitySpecification module1Los = getLos("MOD01");
        LearningOpportunityInstance module1Loi1 = createLearningOppInst("{'credits':" + credit3 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"), gs1);
        List<LearningOpportunityInstance> module1LoiList = new ArrayList<>();
        module1LoiList.add(module1Loi1);
        module1Los.setLearningOpportunityInstances(module1LoiList);
        em.merge(module1Los);
        
        // Course3, part of Module1
        LearningOpportunitySpecification course3Los = getLos("IUJ04");
        LearningOpportunityInstance course3Loi1 = createLearningOppInst("{'credits':" + credit2 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"), gs1);
        List<LearningOpportunityInstance> course3LoiList = new ArrayList<>();
        course3LoiList.add(course3Loi1);
        course3Los.setLearningOpportunityInstances(course3LoiList);
        em.merge(course3Los);
       
        // Course4, part of Module1
        LearningOpportunitySpecification course4Los = getLos("IUJ05");
        LearningOpportunityInstance course4Loi1 = createLearningOppInst("{'credits':" + credit2 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"), gs2);
        List<LearningOpportunityInstance> course4LoiList = new ArrayList<>();
        course4LoiList.add(course4Loi1);
        course4Los.setLearningOpportunityInstances(course4LoiList);
        em.merge(course4Los);
        
        // Class1, part of Course4
        LearningOpportunitySpecification class1Los = getLos("IUCL1");
        LearningOpportunityInstance class1Loi1 = createLearningOppInst("{'credits':" + credit5 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"), gs3);
        List<LearningOpportunityInstance> class1LoiList = new ArrayList<>();
        class1LoiList.add(class1Loi1);
        class1Los.setLearningOpportunityInstances(class1LoiList);
        em.merge(class1Los);
        
        // Class2, part of Course4
        LearningOpportunitySpecification class2Los = getLos("IUCL2");
        LearningOpportunityInstance class2Loi1 = createLearningOppInst("{'credits':" + credit6 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2014", "2015"), "Spring"), gs2);
        List<LearningOpportunityInstance> class2LoiList = new ArrayList<>();
        class2LoiList.add(class2Loi1);
        class2Los.setLearningOpportunityInstances(class2LoiList);
        em.merge(class2Los);
    }

    @Override
    public void createDemoDataPomodoro() throws IOException {
        //TODO Create more data for Pomodoro
        
        GradingScheme gs1 = persistGradingScheme("{'label':[{'text':'A-F','lang':'se'}],'description':[{'text':'A-E = pass, F = fail','lang':'se'}]}");
        GradingScheme gs2 = persistGradingScheme("{'label':[{'text':'5-1','lang':'en'}],'description':[{'text':'5-2 = godkänd, 1 = underkänd','lang':'sv'},{'text':'5-1 = passed, 1 = not passed','lang':'en'}]}");
        
        String resultDistribution1 = "{'resultDistributionCategory':[{'label':'A','count':'4'},{'label':'B','count':'5'},{'label':'C','count':'12'},{'label':'F','count':'2'}],'description':[{'text':'Grades A-E have passed','lang':'en'},{'text':'Betyg A-E har godkänt','lang':'sv'}]}";
        
        // Credits
        String credit1 = "[{'scheme':'ects','level':'Bachelor','value':'5'},{'scheme':'abc','level':'Bachelor','value':'10'}]";
        String credit2 = "[{'scheme':'ects','level':'Bachelor','value':'7.5'},{'scheme':'abc','level':'Bachelor','value':'15'}]";
        String credit3 = "[{'scheme':'ects','level':'Bachelor','value':'15'}]";
        
        // Course1
        LearningOpportunitySpecification course1Los = getLos("PU001");
        LearningOpportunityInstance course1Loi1 = createLearningOppInst("{'id':'" + POMODORO_LOI1_ID + "','credits':" + credit1 + ",'languageOfInstruction':'en','engagementHours':'40','resultDistribution':" + resultDistribution1 + "}", getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"), gs1);
        LearningOpportunityInstance course1Loi2 = createLearningOppInst("{'credits':" + credit2 + ",'languageOfInstruction':'en','engagementHours':'50','resultDistribution':" + resultDistribution1 + "}", getAcademicTerm(getAcademicYear("2015", "2016"), "Spring"), gs1);
        LearningOpportunityInstance course1Loi3 = createLearningOppInst("{'credits':" + credit2 + ",'languageOfInstruction':'en','engagementHours':'65','resultDistribution':" + resultDistribution1 + "}", getAcademicTerm(getAcademicYear("2016", "2017"), "Fall"), gs1);
        List<LearningOpportunityInstance> course1LoiList = new ArrayList<>();
        course1LoiList.add(course1Loi1);
        course1LoiList.add(course1Loi2);
        course1LoiList.add(course1Loi3);
        course1Los.setLearningOpportunityInstances(course1LoiList);
        em.merge(course1Los);
        
        // Course2
        LearningOpportunitySpecification course2Los = getLos("PU002");
        LearningOpportunityInstance course2Loi1 = createLearningOppInst("{'credits':" + credit3 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Fall"), gs2);
        LearningOpportunityInstance course2Loi2 = createLearningOppInst("{'credits':" + credit3 + ",'languageOfInstruction':'en'}", getAcademicTerm(getAcademicYear("2015", "2016"), "Spring"), gs2);
        List<LearningOpportunityInstance> course2LoiList = new ArrayList<>();
        course2LoiList.add(course2Loi1);
        course2LoiList.add(course2Loi2);
        course2Los.setLearningOpportunityInstances(course2LoiList);
        em.merge(course2Los);
    }
    
    private LearningOpportunityInstance createLearningOppInst(String loiJson, AcademicTerm academicTerm, GradingScheme gradingScheme) throws IOException {
        LearningOpportunityInstance learningOppInst = JsonHelper.mapToObject(LearningOpportunityInstance.class, loiJson);
        learningOppInst.setAcademicTerm(academicTerm);
        learningOppInst.setGradingScheme(gradingScheme);
        return learningOppInst;
    }

    private GradingScheme persistGradingScheme(String gradingSchemeJson) throws IOException {
        GradingScheme gradingScheme = JsonHelper.mapToObject(GradingScheme.class, gradingSchemeJson);
        em.persist(gradingScheme);
        
        return gradingScheme;
    }
    
    private AcademicYear getAcademicYear(String startYear, String endYear) throws IOException {
        Query query = em.createNamedQuery(AcademicYear.findByKeys).setParameter("startYear", startYear).setParameter("endYear", endYear);
        List<AcademicYear> academicYearList = query.getResultList();
        if (academicYearList.size() != 1) {
           throw new IllegalArgumentException("Academic year " + startYear + "/" + endYear + " doesn't return an unique academic year.");
        }
        
        return academicYearList.get(0);
    }

    private AcademicTerm getAcademicTerm(AcademicYear academicYear, String academicTermPartOfText) throws IOException {
        String academicYearId = academicYear.getId();
        Query query = em.createNamedQuery(AcademicTerm.findByAcademicYearId).setParameter("academicYearId", academicYearId);
        List<AcademicTerm> academicTermList = query.getResultList();
        if (academicTermList.isEmpty()) {
           throw new IllegalArgumentException("Academic term with academic year " + academicYear.getAcademicYear() + " doesn't return an academic term.");
        }
        
        Optional<AcademicTerm> academicTerm = academicTermList.stream().filter((at) -> {
            return at.getDispName().stream().anyMatch((langItem) -> (langItem.getText().contains(academicTermPartOfText)));
        }).findFirst();
        
        if (!academicTerm.isPresent()) {
            throw new IllegalArgumentException("Academic term with academic year " + academicYear.getAcademicYear() + " and name that contains " + academicTermPartOfText + " doesn't return an academic term.");
        }
        
        return academicTerm.get();
    }

    private LearningOpportunitySpecification getLos(String losCode) {
        Query query = em.createNamedQuery(LearningOpportunitySpecification.findByLosCode).setParameter("losCode", losCode);
        List<LearningOpportunitySpecification> losList = query.getResultList();
        if (losList.size() != 1) {
           throw new IllegalArgumentException("Los code " + losCode + " doesn't return an unique los.");
        }
        
        return losList.get(0);
    }
    
    private String getOrganizationUnitId(String organizationUnitCode) throws IOException {
        Query query = em.createNamedQuery(OrganizationUnit.findByOrganizationUnitCode).setParameter("organizationUnitCode", organizationUnitCode);
        List<OrganizationUnit> organizationUnitList = query.getResultList();
        if (organizationUnitList.size() != 1) {
           throw new IllegalArgumentException("Organization unit code " + organizationUnitCode + " doesn't return an unique organization unit.");
        }
        
        return organizationUnitList.get(0).getId();
    }
}
