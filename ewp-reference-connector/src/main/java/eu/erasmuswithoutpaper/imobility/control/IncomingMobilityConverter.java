package eu.erasmuswithoutpaper.imobility.control;

import eu.erasmuswithoutpaper.api.imobilities.endpoints.StudentMobilityForStudies;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncomingMobilityConverter {
    private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);
    
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public StudentMobilityForStudies convertToStudentMobilityForStudies(Mobility mobility) {
        StudentMobilityForStudies studentMobilityForStudies = new StudentMobilityForStudies();
        
        // TODO: add this
        //studentMobilityForStudies.setActualArrivalDate();
        //studentMobilityForStudies.setActualDepartureDate();
        studentMobilityForStudies.setOmobilityId(mobility.getId());
        
        return studentMobilityForStudies;
    }
}
