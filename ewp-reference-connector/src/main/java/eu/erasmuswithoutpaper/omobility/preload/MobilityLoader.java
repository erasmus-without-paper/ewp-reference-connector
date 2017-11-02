
package eu.erasmuswithoutpaper.omobility.preload;

import eu.erasmuswithoutpaper.course.preload.LoiLoader;
import eu.erasmuswithoutpaper.course.preload.LosLoader;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.omobility.entity.LearningAgreement;
import eu.erasmuswithoutpaper.omobility.entity.LearningAgreementComponentStatus;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;
import eu.erasmuswithoutpaper.omobility.entity.RecognizedLaComponent;
import eu.erasmuswithoutpaper.omobility.entity.StudiedLaComponent;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import eu.erasmuswithoutpaper.organization.preload.InstitutionLoader;
import eu.erasmuswithoutpaper.organization.preload.MobilityParticipantLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

public class MobilityLoader extends AbstractStartupLoader {
    
    @Override
    public void createDemoDataIkea() throws IOException {
        String ouIdIkea = InstitutionLoader.IKEA_OU1_ID;
        String ouIdPomodoro = InstitutionLoader.POMODORO_OU1_ID;
        String losId = LosLoader.IKEA_LOS1_ID;
        String loiId = LoiLoader.IKEA_LOI1_ID;
        persistMobility("{'mobilityRevision':'1','iiaId':'" + getIiaId("IK-POM-01") + "','sendingInstitutionId':'ikea.university.se','sendingOrganizationUnitId':'" + ouIdIkea + 
                "','receivingInstitutionId':'pomodoro.university.it','receivingOrganizationUnitId':'" + ouIdPomodoro + 
                "','mobilityParticipantId':'" + MobilityParticipantLoader.IKEA_MOBILITY_PARTICIPANT1_ID + "','status':'NOMINATION','plannedArrivalDate':'2017-03-14','plannedDepartureDate':'2017-05-15','iscedCode':'ISC123','eqfLevel':'3'}", 
                getMobilityType("Student", "Studies"), getLearningAgreement("{'learningAgreementRevision':'1'}", 
                        getStudiedLearningAgreementComponents(losId, loiId, "Data Collection and Analysis", "Fall semester 2015"), getRecognizedLearningAgreementComponents(losId, loiId)), getCoopConditionId("IK-POM-01"));
        persistMobility("{'mobilityRevision':'1','iiaId':'" + getIiaId("IK-POM-01") + "','sendingInstitutionId':'pomodoro.university.it','sendingOrganizationUnitId':'" + ouIdIkea + 
                "','receivingInstitutionId':'ikea.university.se','receivingOrganizationUnitId':'" + ouIdPomodoro + 
                "','mobilityParticipantId':'" + MobilityParticipantLoader.IKEA_MOBILITY_PARTICIPANT1_ID + "','status':'NOMINATION','plannedArrivalDate':'2017-03-14','plannedDepartureDate':'2017-05-15','iscedCode':'ISC123','eqfLevel':'3'}", 
                getMobilityType("Student", "Studies"), getLearningAgreement("{'learningAgreementRevision':'1'}", 
                        getStudiedLearningAgreementComponents(losId, loiId, "Data Collection and Analysis", "Fall semester 2015"), getRecognizedLearningAgreementComponents(losId, loiId)), getCoopConditionId("IK-POM-01"));
    }
    
    @Override
    public void createDemoDataPomodoro() throws IOException {
        createDemoDataIkea(); // Same data for Ikea and Pomodoro since they both are part of the same mobility.
    }
    
    private void persistMobility(String mobilityJson, MobilityType mobilityType, LearningAgreement learningAgreement, String coopConditionId) throws IOException {
        Mobility mobility = JsonHelper.mapToObject(Mobility.class, mobilityJson);
        mobility.setMobilityType(mobilityType);
        mobility.setLearningAgreement(learningAgreement);
        mobility.setCooperationConditionId(coopConditionId);
        em.persist(mobility);
    }
    
    private MobilityType getMobilityType(String group, String category) throws IOException {
        Query query = em.createNamedQuery(MobilityType.findByGroupAndCategory).setParameter("mobilityGroup", group).setParameter("mobilityCategory", category);
        List<MobilityType> mobilityTypes = query.getResultList();
        if (mobilityTypes.size() != 1) {
           throw new IllegalArgumentException("Group " + group + " and category " + category + "doesn't return an unique mobilityType.");
        }
        
        return mobilityTypes.get(0);
    }

    private LearningAgreement getLearningAgreement(String learningAgreementJson, List<StudiedLaComponent> studiedLaComponents, List<RecognizedLaComponent> recognizedLaComponents) throws IOException {
        LearningAgreement la = JsonHelper.mapToObject(LearningAgreement.class, learningAgreementJson);
        la.setStudiedLaComponents(studiedLaComponents);
        la.setRecognizedLaComponents(recognizedLaComponents);
        return la;
    }

    private List<RecognizedLaComponent> getRecognizedLearningAgreementComponents(String losId, String loiId) {
        List<RecognizedLaComponent> laComponents = new ArrayList<>();
        RecognizedLaComponent laComponent = new RecognizedLaComponent();
        laComponent.setLosId(losId);
        laComponent.setLoiId(loiId);
        laComponents.add(laComponent);
        
        return laComponents;
    }
    
    private List<StudiedLaComponent> getStudiedLearningAgreementComponents(String losId, String loiId, String title, String academicTermDisplayName) {
        List<StudiedLaComponent> laComponents = new ArrayList<>();
        StudiedLaComponent laComponent = new StudiedLaComponent();
        laComponent.setStatus(LearningAgreementComponentStatus.PLANNED);
        laComponent.setLosId(losId);
        laComponent.setLoiId(loiId);
        laComponent.setTitle(title);
        laComponent.setAcademicTermDisplayName(academicTermDisplayName);
        laComponents.add(laComponent);
        
        return laComponents;
    }

    private String getCoopConditionId(String iiaCode) {
        Query query = em.createNamedQuery(Iia.findByIiaCode).setParameter("iiaCode", iiaCode);
        List<Iia> iias = query.getResultList();
        
        if(iias.isEmpty()){
            throw new IllegalArgumentException("iiaCode " + iiaCode + " doesn't return any IIA");
        }
        
        // Returning the first cooperation conditions id in the list, to keep it simple
        return iias.get(0).getCooperationConditions().get(0).getId();
    }
    
    private String getOrganizationUnitId(String organizationUnitCode) throws IOException {
        Query query = em.createNamedQuery(OrganizationUnit.findByOrganizationUnitCode).setParameter("organizationUnitCode", organizationUnitCode);
        List<OrganizationUnit> organizationUnitList = query.getResultList();
        if (organizationUnitList.size() != 1) {
           throw new IllegalArgumentException("Organization unit code " + organizationUnitCode + " doesn't return an unique organization unit.");
        }
        
        return organizationUnitList.get(0).getId();
    }

    private String getIiaId(String iiaCode) {
        Query query = em.createNamedQuery(Iia.findByIiaCode).setParameter("iiaCode", iiaCode);
        List<Iia> iiaList = query.getResultList();
        
        if (iiaList.size() != 1) {
           throw new IllegalArgumentException("Iia code " + iiaCode + " doesn't return an unique IIA.");
        }
        
        return iiaList.get(0).getId();
    }
}
