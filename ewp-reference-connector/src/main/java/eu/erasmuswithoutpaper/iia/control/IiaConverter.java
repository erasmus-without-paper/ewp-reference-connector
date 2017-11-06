package eu.erasmuswithoutpaper.iia.control;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.CooperationConditions;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

            CooperationConditions conditions = new CooperationConditions();
            converted.setCooperationConditions(conditions);
            converted.setInEffect(true);

            return converted;
        }).collect(Collectors.toList());
    }

    private IiasGetResponse.Iia.CooperationConditions convertToCooperationConditions(List<CooperationCondition> cooperationConditions) {
        // TODO: Add this
        IiasGetResponse.Iia.CooperationConditions converted = new IiasGetResponse.Iia.CooperationConditions();
        converted.getStaffTeacherMobilitySpec();
        converted.getStaffTrainingMobilitySpec();
        converted.getStudentStudiesMobilitySpec();
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

}
