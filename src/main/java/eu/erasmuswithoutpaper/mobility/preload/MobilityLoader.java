
package eu.erasmuswithoutpaper.mobility.preload;

import eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.mobility.entity.LearningAgreement;
import eu.erasmuswithoutpaper.mobility.entity.LearningAgreementComponent;
import eu.erasmuswithoutpaper.mobility.entity.LearningAgreementComponentStatus;
import eu.erasmuswithoutpaper.mobility.entity.Mobility;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import eu.erasmuswithoutpaper.organization.preload.InstitutionLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class MobilityLoader {
    
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public void createDemoDataIkea() throws IOException {
        String ouIdIkea = InstitutionLoader.IKEA_OU1_ID;
        String ouIdPomodoro = InstitutionLoader.POMODORO_OU1_ID;
        persistMobility("{'mobilityRevision':'1','iiaId':'iiaId001','sendingInstitutionId':'ikea.university.se','sendingOrganizationUnitId':'" + ouIdIkea + "','receivingInstitutionId':'pomodoro.university.it','receivingOrganizationUnitId':'" + ouIdPomodoro + "','personId':'9011046365','status':'NOMINATED','plannedArrivalDate':'2017-03-14','plannedDepartureDate':'2017-05-15','iscedCode':'ISC123','eqfLevel':'3'}", getMobilityType("Student", "Studies"), getLearningAgreement("{'learningAgreementRevision':'1'}", getLearningAgreementComponents("IU001")), getCoopConditionId("iiaId001"));
    }
    
    public void createDemoDataPomodoro() throws IOException {
        //TODO Create data for Pomodoro
        createDemoDataIkea();
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

    private LearningAgreement getLearningAgreement(String learningAgreementJson, List<LearningAgreementComponent> laComponents) throws IOException {
        LearningAgreement la = JsonHelper.mapToObject(LearningAgreement.class, learningAgreementJson);
        la.setLaComponents(laComponents);
        
        return la;
    }

    private List<LearningAgreementComponent> getLearningAgreementComponents(String losCode) {
        List<LearningAgreementComponent> laComponents = new ArrayList<>();
        
        Query query = em.createNamedQuery(LearningOpportunitySpecification.findByLosCode).setParameter("losCode", losCode);
        List<LearningOpportunitySpecification> los = query.getResultList();
        
        if(los.size() != 1){
            throw new IllegalArgumentException("losCode " + losCode + " doesn't return a unique LearningOpportunitySpecification.");
        }
        
        LearningAgreementComponent laComponent = new LearningAgreementComponent();
        laComponent.setStatus(LearningAgreementComponentStatus.PLANNED);
        laComponent.setLosId(los.get(0).getId());
        
        // Returning the first learning opp instance in the list, to keep it simple
        laComponent.setLoiId(los.get(0).getLearningOpportunityInstances().get(0).getId());
        
        laComponents.add(laComponent);
        
        return laComponents;
    }

    private String getCoopConditionId(String iiaId) {
        Query query = em.createNamedQuery(Iia.findByIiaId).setParameter("iiaId", iiaId);
        List<Iia> iias = query.getResultList();
        
        if(iias.isEmpty()){
            throw new IllegalArgumentException("iiaId " + iiaId + " doesn't return any IIA");
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
}
