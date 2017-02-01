package eu.erasmuswithoutpaper.iia.control;

import eu.erasmuswithoutpaper.api.iia.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;

public class IiaConverter {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public List<IiasGetResponse.Iia> convertToIias(List<Iia> iiaList) {
        return iiaList.stream().map(iia -> {
            IiasGetResponse.Iia converted = new IiasGetResponse.Iia();
            // TODO: add contacts to IIA?
            //converted.getContact().addAll(iia.getContact());
            
            //converted.getPartnerHei().addAll(iia.get);
            
            converted.setCooperationConditions(convertToCooperationConditions(iia.getCooperationConditions()));
            try {
                converted.setEndDate(ConverterHelper.convertToXmlGregorianCalendar(iia.getEndDate()));
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(IiaConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            converted.setIiaCode(iia.getIiaCode());
            converted.setIiaId(iia.getId());
            
            // TODO: add this?
            //converted.setInEffect(true);
            //converted.setSigningContact(iia.getSigningContact());
            //converted.setSigningDate(iia.getSigningDate());
            
            try {
                converted.setStartDate(ConverterHelper.convertToXmlGregorianCalendar(iia.getStartDate()));
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(IiaConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
