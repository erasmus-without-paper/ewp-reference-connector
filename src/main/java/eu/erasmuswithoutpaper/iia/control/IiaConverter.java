package eu.erasmuswithoutpaper.iia.control;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.CooperationConditions;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class IiaConverter {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public List<IiasGetResponse.Iia> convertToIias(List<Iia> iiaList) {
        return iiaList.stream().map(iia -> {
            IiasGetResponse.Iia converted = new IiasGetResponse.Iia();
            
            // TODO: partner
            //converted.getPartner().addAll(iia.getPartner());
            
            CooperationConditions conditions = new CooperationConditions();
            //conditions.getStaffTeacherMobilitySpec().addAll(c);
            //conditions.getStaffTrainingMobilitySpec().addAll(c);
            //conditions.getStudentStudiesMobilitySpec().addAll(c);
            //conditions.getStudentTraineeshipMobilitySpec().addAll(c);
 
            converted.setCooperationConditions(conditions);
            // TODO: add this?
            //converted.setInEffect(true);
                       
            return converted;
        }).collect(Collectors.toList());
    }

    private IiasGetResponse.Iia.CooperationConditions convertToCooperationConditions(List<CooperationCondition> cooperationConditions) {
        // TODO: nothing match!!!
        IiasGetResponse.Iia.CooperationConditions converted = new IiasGetResponse.Iia.CooperationConditions();
        converted.getStaffTeacherMobilitySpec();
        converted.getStaffTrainingMobilitySpec();
        converted.getStudentStudiesMobilitySpec();
        converted.getStudentTraineeshipMobilitySpec();
        
        return converted;
    }

}
