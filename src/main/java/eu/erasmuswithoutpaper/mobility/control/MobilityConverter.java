package eu.erasmuswithoutpaper.mobility.control;

import eu.erasmuswithoutpaper.api.mobilities.endpoints.ComponentRecognized;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.ComponentStudied;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.MobilityStatus;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.StudentMobilityForStudies;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.mobility.entity.Mobility;
import eu.erasmuswithoutpaper.mobility.entity.RecognizedLaComponent;
import eu.erasmuswithoutpaper.mobility.entity.StudiedLaComponent;
import eu.erasmuswithoutpaper.organization.entity.Person;
import eu.erasmuswithoutpaper.organization.entity.MobilityParticipant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;

public class MobilityConverter {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public StudentMobilityForStudies convertToStudentMobilityForStudies(Mobility mobility) {
        StudentMobilityForStudies studentMobilityForStudies = new StudentMobilityForStudies();
        if (mobility.getLearningAgreement() != null) {
            studentMobilityForStudies.getComponentRecognized().addAll(convertToComponentRecognized(mobility.getLearningAgreement().getRecognizedLaComponents()));
            studentMobilityForStudies.getComponentStudied().addAll(convertToComponentStudied(mobility.getLearningAgreement().getStudiedLaComponents()));
        }
        
        // TODO: add this
        //studentMobilityForStudies.getNomineeLanguageSkill();
        //studentMobilityForStudies.setActualArrivalDate();
        //studentMobilityForStudies.setActualDepartureDate();
        studentMobilityForStudies.setMobilityId(mobility.getId());
        
        // TODO: add this
        studentMobilityForStudies.setNomineeEqfLevel(mobility.getEqfLevel());
        //studentMobilityForStudies.setNomineeIscedFCode(value);
        
        try {
            studentMobilityForStudies.setPlannedArrivalDate(ConverterHelper.convertToXmlGregorianCalendar(mobility.getPlannedArrivalDate()));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(MobilityConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            studentMobilityForStudies.setPlannedDepartureDate(ConverterHelper.convertToXmlGregorianCalendar(mobility.getPlannedDepartureDate()));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(MobilityConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // TODO: How do we get the IIA id for receiving HEI?
        studentMobilityForStudies.setReceivingHei(convertToReceivingHei(null, mobility.getReceivingInstitutionId(), mobility.getReceivingOrganizationUnitId()));
        studentMobilityForStudies.setSendingHei(convertToSendingHei(mobility.getIiaId(), mobility.getSendingInstitutionId(), mobility.getSendingOrganizationUnitId()));
        studentMobilityForStudies.setStatus(convertToMobilityStatus(mobility.getStatus()));
        studentMobilityForStudies.setStudent(convertToStudent(mobility.getMobilityParticipantId()));
        
        // TODO: add this
        //studentMobilityForStudies.setTimeline(value);
        
        return studentMobilityForStudies;
    }

    private List<ComponentRecognized> convertToComponentRecognized(List<RecognizedLaComponent> recognizedLaComponents) {
        if (recognizedLaComponents == null) {
            return new ArrayList<>();
        }
        return recognizedLaComponents.stream().map((c) -> {
            ComponentRecognized componentRecognized = new ComponentRecognized();
            componentRecognized.setLosId(c.getLosId());
            componentRecognized.setLoiId(c.getLoiId());
            return componentRecognized;
        }).collect(Collectors.toList());
    }
    
    private List<ComponentStudied> convertToComponentStudied(List<StudiedLaComponent> studiedLaComponents) {
        if (studiedLaComponents == null) {
            return new ArrayList<>();
        }
        return studiedLaComponents.stream().map((c) -> {
            ComponentStudied componentStudied = new ComponentStudied();
            componentStudied.setLosId(c.getLosId());
            componentStudied.setLosCode(c.getLosCode());
            componentStudied.setLoiId(c.getLoiId());
            componentStudied.setAcademicTermDisplayName(c.getAcademicTermDisplayName());
            componentStudied.setTitle(c.getTitle());
            
            // TODO: Add this
            //componentStudied.getCredit();
            return componentStudied;
        }).collect(Collectors.toList());
    }

    private StudentMobilityForStudies.ReceivingHei convertToReceivingHei(String iiaId, String institutionId, String organizationUnitId) {
        StudentMobilityForStudies.ReceivingHei receivingHei = new StudentMobilityForStudies.ReceivingHei();
        receivingHei.setIiaId(iiaId);
        receivingHei.setHeiId(institutionId);
        receivingHei.setOunitId(organizationUnitId);
        return receivingHei;
    }

    private StudentMobilityForStudies.SendingHei convertToSendingHei(String iiaId, String institutionId, String organizationUnitId) {
        StudentMobilityForStudies.SendingHei sendingHei = new StudentMobilityForStudies.SendingHei();
        sendingHei.setIiaId(iiaId);
        sendingHei.setHeiId(institutionId);
        sendingHei.setOunitId(organizationUnitId);
        return sendingHei;
    }

    private MobilityStatus convertToMobilityStatus(eu.erasmuswithoutpaper.mobility.entity.MobilityStatus status) {
        MobilityStatus mobilityStatus = MobilityStatus.fromValue(status.value());
        return mobilityStatus;
    }

    private StudentMobilityForStudies.Student convertToStudent(String studentId) {
        MobilityParticipant student =  em.find(MobilityParticipant.class, studentId);
        StudentMobilityForStudies.Student mobilityStudent = new StudentMobilityForStudies.Student();
        if (student != null) {
            Person person = student.getPerson();
            mobilityStudent.getPhoneNumber().addAll(ConverterHelper.convertToPhoneNumber(student.getContactDetails().getPhoneNumber()));
            
            try {
                mobilityStudent.setBirthDate(ConverterHelper.convertToXmlGregorianCalendar(person.getBirthDate()));
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(MobilityConverter.class.getName()).log(Level.SEVERE, null, ex);
            }

            mobilityStudent.setCitizenship(person.getCountryCode());
            if (student.getContactDetails().getEmail() != null && student.getContactDetails().getEmail().size() > 0) {
                mobilityStudent.setEmail(student.getContactDetails().getEmail().get(0));
            }

            mobilityStudent.setFamilyName(person.getLastName());
            mobilityStudent.setGender(person.getGender().value());
            mobilityStudent.setGivenNames(person.getFirstNames());
            mobilityStudent.setMailingAddress(ConverterHelper.convertToFlexibleAddress(student.getContactDetails().getMailingAddress()));
            mobilityStudent.setStreetAddress(ConverterHelper.convertToFlexibleAddress(student.getContactDetails().getStreetAddress()));
        }
        return mobilityStudent;
    }
}
