package eu.erasmuswithoutpaper.iia.control;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffTeacherMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffTrainingMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentStudiesMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentTraineeshipMobilitySpec;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class IiaConverter {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public List<IiasGetResponse.Iia> convertToIias(String hei_id, List<Iia> iiaList) {
        return iiaList.stream().map((Iia iia) -> {
            IiasGetResponse.Iia converted = new IiasGetResponse.Iia();
            HashMap<String, IiaPartner> uniquePartners = new HashMap<>();
            for (CooperationCondition condition : iia.getCooperationConditions()) {
                uniquePartners.put(condition.getSendingPartner().getInstitutionId(), condition.getSendingPartner());
                uniquePartners.put(condition.getReceivingPartner().getInstitutionId(), condition.getReceivingPartner());
            }

            Set<String> partnerKeys = uniquePartners.keySet();
            for (String partnerKey : partnerKeys) {
                IiaPartner partner = uniquePartners.get(partnerKey);
                converted.getPartner().add(convertToPartner(iia, partner));
            }

            /**
             * The value of `hei-id` of the first `partner` MUST match the value passed in
             * the `hei_id` request parameter,
             */
            Comparator<? super IiasGetResponse.Iia.Partner> heiIdComparator = new Comparator<IiasGetResponse.Iia.Partner>() {
                //     return 1 if rhs should be before lhs
                //     return -1 if lhs should be before rhs
                //     return 0 otherwise
                @Override
                public int compare(IiasGetResponse.Iia.Partner lhs, IiasGetResponse.Iia.Partner rhs) {
                    if (rhs.getHeiId().equals(hei_id)) {
                        return 1;
                    }
                    if (lhs.getHeiId().equals(hei_id)) {
                        return -1;
                    }
                    return 0;
                }
            };
            converted.getPartner().sort(heiIdComparator);

            converted.setCooperationConditions(convertToCooperationConditions(iia.getCooperationConditions()));
            converted.setInEffect(true);

            return converted;
        }).collect(Collectors.toList());
    }

    private IiasGetResponse.Iia.CooperationConditions convertToCooperationConditions(List<CooperationCondition> cooperationConditions) {
        // TODO: Add this
        Map<String, List<CooperationCondition>> ccMap = cooperationConditions
                .stream()
                .collect(Collectors.groupingBy(cc -> cc.getMobilityType().getMobilityGroup() + "-" + cc.getMobilityType().getMobilityCategory()));
                
        IiasGetResponse.Iia.CooperationConditions converted = new IiasGetResponse.Iia.CooperationConditions();

        if (ccMap.containsKey("Staff-Teaching")) {
            converted.getStaffTeacherMobilitySpec().addAll(
                    ccMap.get("Staff-Teaching")
                            .stream()
                            .map(this::convertToStaffTeacherMobilitySpec)
                            .collect(Collectors.toList()));
        }
        if (ccMap.containsKey("Staff-Training")) {
            converted.getStaffTrainingMobilitySpec().addAll(
                    ccMap.get("Staff-Training")
                            .stream()
                            .map(this::convertToStaffTrainingMobilitySpec)
                            .collect(Collectors.toList()));
        }
        if (ccMap.containsKey("Student-Studies")) {
            converted.getStudentStudiesMobilitySpec().addAll(
                    ccMap.get("Student-Studies")
                            .stream()
                            .map(this::convertToStudentStudiesMobilitySpec)
                            .collect(Collectors.toList()));
        }
        if (ccMap.containsKey("Student-Training")) {
            converted.getStudentTraineeshipMobilitySpec().addAll(
                    ccMap.get("Student-Studies")
                            .stream()
                            .map(this::convertToStudentTraineeshipMobilitySpec)
                            .collect(Collectors.toList()));
        }
        converted.getStudentTraineeshipMobilitySpec();

        return converted;
    }

    private IiasGetResponse.Iia.Partner convertToPartner(Iia iia, IiaPartner partner) {
        IiasGetResponse.Iia.Partner converted = new IiasGetResponse.Iia.Partner();
        converted.setHeiId(partner.getInstitutionId());
        converted.setOunitId(partner.getOrganizationUnitId());
        converted.setIiaCode(iia.getIiaCode());
        return converted;
    }

    private StaffTeacherMobilitySpec convertToStaffTeacherMobilitySpec(CooperationCondition cc) {
        StaffTeacherMobilitySpec conv = new StaffTeacherMobilitySpec();
        addToStaffMobilitySpecification(conv, cc);
        return conv;
    }
    private StaffTrainingMobilitySpec convertToStaffTrainingMobilitySpec(CooperationCondition cc) {
        StaffTrainingMobilitySpec conv = new StaffTrainingMobilitySpec();
        addToStaffMobilitySpecification(conv, cc);
        return conv;
    }
    private StudentStudiesMobilitySpec convertToStudentStudiesMobilitySpec(CooperationCondition cc) {
        StudentStudiesMobilitySpec conv = new StudentStudiesMobilitySpec();
        addToStudentMobilitySpecification(conv, cc);
        conv.getEqfLevel().add(cc.getEqfLevel());
        return conv;
    }
    private StudentTraineeshipMobilitySpec convertToStudentTraineeshipMobilitySpec(CooperationCondition cc) {
        StudentTraineeshipMobilitySpec conv = new StudentTraineeshipMobilitySpec();
        addToStudentMobilitySpecification(conv, cc);
        return conv;
    }
    
    private void addToMobilitySpecification(MobilitySpecification conv , CooperationCondition cc) {
        //conv.getReceivingAcademicYearId();
        //conv.getReceivingContact();
        if (cc.getReceivingPartner().getOrganizationUnitId() != null) {
            conv.getReceivingOunitId().add(cc.getReceivingPartner().getOrganizationUnitId());
        }
        conv.getRecommendedLanguageSkill();
        conv.getSendingContact();
        if (cc.getSendingPartner().getOrganizationUnitId() != null) {
            conv.getSendingOunitId().add(cc.getSendingPartner().getOrganizationUnitId());
        }
        //conv.setIscedFCode();
        conv.setMobilitiesPerYear(BigInteger.valueOf(cc.getMobilityNumber().getNumber()));
        conv.setReceivingHeiId(cc.getReceivingPartner().getInstitutionId());
        conv.setSendingHeiId(cc.getSendingPartner().getInstitutionId());
    }
    
    private void addToStudentMobilitySpecification(StudentMobilitySpecification conv, CooperationCondition cc) {
        //conv.setAvgMonths(BigInteger.ONE);
        conv.setTotalMonths(cc.getDuration().getNumber().toBigInteger());
    }

    private void addToStaffMobilitySpecification(StaffMobilitySpecification conv, CooperationCondition cc) {
        //conv.setAvgDays(BigInteger.ONE);
        conv.setTotalDays(cc.getDuration().getNumber().toBigInteger());
    }
}
