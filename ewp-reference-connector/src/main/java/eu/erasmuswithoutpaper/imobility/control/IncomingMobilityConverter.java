package eu.erasmuswithoutpaper.imobility.control;

import eu.emrex.elmo.Elmo;
import eu.erasmuswithoutpaper.api.imobilities.endpoints.StudentMobilityForStudies;
import eu.erasmuswithoutpaper.api.imobilities.tors.endpoints.ImobilityTorsGetResponse;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;
import eu.erasmuswithoutpaper.omobility.entity.StudiedLaComponent;
import java.util.List;
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

    public ImobilityTorsGetResponse.Tor convertToTor(Mobility mobility) {
        ImobilityTorsGetResponse.Tor tor = new ImobilityTorsGetResponse.Tor();
        tor.setOmobilityId(mobility.getId());
        if (mobility.getLearningAgreement() != null) {
            tor.setElmo(convertToElmo(mobility.getLearningAgreement().getStudiedLaComponents()));
        }
        return tor;
    }

    private Elmo convertToElmo(List<StudiedLaComponent> studiedLaComponents) {
        Elmo elmo = new Elmo();
        // TODO: implement ELMO
        return elmo;
    }
}
