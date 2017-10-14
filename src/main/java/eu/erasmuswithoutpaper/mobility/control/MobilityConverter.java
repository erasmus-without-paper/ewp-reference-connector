package eu.erasmuswithoutpaper.mobility.control;

import eu.erasmuswithoutpaper.api.mobilities.endpoints.ComponentRecognized;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.ComponentStudied;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.ListOfChangesToComponentsRecognized;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.ListOfChangesToComponentsRecognized.InsertComponentRecognized;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.ListOfChangesToComponentsStudied;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.ListOfChangesToComponentsStudied.InsertComponentStudied;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.MobilityStatus;
import eu.erasmuswithoutpaper.api.mobilities.endpoints.StudentMobilityForStudies;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.mobility.entity.Mobility;
import eu.erasmuswithoutpaper.mobility.entity.RecognizedLaComponent;
import eu.erasmuswithoutpaper.mobility.entity.StudiedLaComponent;
import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import eu.erasmuswithoutpaper.organization.entity.Person;
import eu.erasmuswithoutpaper.organization.entity.MobilityParticipant;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobilityConverter {
    private static final Logger logger = LoggerFactory.getLogger(MobilityConverter.class);
    
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public StudentMobilityForStudies convertToStudentMobilityForStudies(Mobility mobility) {
        StudentMobilityForStudies studentMobilityForStudies = new StudentMobilityForStudies();
        if (mobility.getLearningAgreement() != null) {
            studentMobilityForStudies.setComponentsRecognized(convertToComponentsRecognized(mobility.getLearningAgreement().getRecognizedLaComponents()));
            studentMobilityForStudies.setComponentsStudied(convertToComponentsStudied(mobility.getLearningAgreement().getStudiedLaComponents()));
        }
        
        // TODO: add this
        //studentMobilityForStudies.getNomineeLanguageSkill();
        studentMobilityForStudies.setEqfLevelStudiedAtDeparture(mobility.getEqfLevel());
        studentMobilityForStudies.setEqfLevelStudiedAtNomination(mobility.getEqfLevel());
        //studentMobilityForStudies.setNomineeIscedFCode(value);
        studentMobilityForStudies.setOmobilityId(mobility.getId());
        try {
            studentMobilityForStudies.setPlannedArrivalDate(ConverterHelper.convertToXmlGregorianCalendar(mobility.getPlannedArrivalDate()));
        } catch (DatatypeConfigurationException ex) {
            logger.error("Can't convert date", ex);
        }
        try {
            studentMobilityForStudies.setPlannedDepartureDate(ConverterHelper.convertToXmlGregorianCalendar(mobility.getPlannedDepartureDate()));
        } catch (DatatypeConfigurationException ex) {
            logger.error("Can't convert date", ex);
        }
        studentMobilityForStudies.setReceivingHei(convertToReceivingHei(null, mobility.getReceivingInstitutionId(), mobility.getReceivingOrganizationUnitId()));
        studentMobilityForStudies.setSendingHei(convertToSendingHei(mobility.getIiaId(), mobility.getSendingInstitutionId(), mobility.getSendingOrganizationUnitId()));
        studentMobilityForStudies.setStatus(convertToMobilityStatus(mobility.getStatus()));
        studentMobilityForStudies.setStudent(convertToStudent(mobility.getMobilityParticipantId()));
        
        return studentMobilityForStudies;
    }

    private ListOfChangesToComponentsRecognized convertToComponentRecognized(List<RecognizedLaComponent> recognizedLaComponents) {
        ListOfChangesToComponentsRecognized listOfChangesToComponentsRecognized = new ListOfChangesToComponentsRecognized();
        if (recognizedLaComponents == null) {
            return listOfChangesToComponentsRecognized;
        }
        
        recognizedLaComponents.stream().map((c) -> {
            ComponentRecognized componentRecognized = new ComponentRecognized();
            componentRecognized.setLosId(c.getLosId());
            componentRecognized.setLoiId(c.getLoiId());
            return componentRecognized;
        }).map(cr -> {
            InsertComponentRecognized icr = new InsertComponentRecognized();
            icr.setComponentRecognized(cr);
            return icr;
        }).forEach(icr -> listOfChangesToComponentsRecognized.getInsertComponentRecognizedOrRemoveComponentRecognized().add(icr));
        
        return listOfChangesToComponentsRecognized;
    }
    
    private ListOfChangesToComponentsStudied convertToComponentStudied(List<StudiedLaComponent> studiedLaComponents) {
        ListOfChangesToComponentsStudied listOfChangesToComponentsStudied = new ListOfChangesToComponentsStudied();
        if (studiedLaComponents == null) {
            return listOfChangesToComponentsStudied;
        }
        
        studiedLaComponents.stream().map((c) -> {
            ComponentStudied componentStudied = new ComponentStudied();
            componentStudied.setLosId(c.getLosId());
            componentStudied.setLosCode(c.getLosCode());
            componentStudied.setLoiId(c.getLoiId());
            componentStudied.setAcademicTermDisplayName(c.getAcademicTermDisplayName());
            componentStudied.setTitle(c.getTitle());
            
            // TODO: Add this
            //componentStudied.getCredit();
            return componentStudied;
        }).map(cs -> {
            InsertComponentStudied ics = new InsertComponentStudied();
            ics.setComponentStudied(cs);
            return ics;
        }).forEach(ics -> listOfChangesToComponentsStudied.getInsertComponentStudiedOrRemoveComponentStudied().add(ics));
        
        return listOfChangesToComponentsStudied;
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
                logger.error("Can't convert date", ex);
            }

            mobilityStudent.setCitizenship(person.getCountryCode());
            if (student.getContactDetails().getEmail() != null && student.getContactDetails().getEmail().size() > 0) {
                mobilityStudent.setEmail(student.getContactDetails().getEmail().get(0));
            }

            
            mobilityStudent.getFamilyName().addAll(ConverterHelper.convertToStringWithOptionalLang(Arrays.asList(new LanguageItem(person.getLastName(), LanguageItem.SWEDISH))));
            mobilityStudent.setGender(person.getGender().value());
            mobilityStudent.getGivenNames().addAll(ConverterHelper.convertToStringWithOptionalLang(Arrays.asList(new LanguageItem(person.getFirstNames(), LanguageItem.SWEDISH))));
            mobilityStudent.setMailingAddress(ConverterHelper.convertToFlexibleAddress(student.getContactDetails().getMailingAddress()));
            mobilityStudent.setStreetAddress(ConverterHelper.convertToFlexibleAddress(student.getContactDetails().getStreetAddress()));
        }
        return mobilityStudent;
    }

    private StudentMobilityForStudies.ComponentsRecognized convertToComponentsRecognized(List<RecognizedLaComponent> recognizedLaComponents) {
        StudentMobilityForStudies.ComponentsRecognized componentsRecognized = new StudentMobilityForStudies.ComponentsRecognized();
        componentsRecognized.setLatestApprovedChanges(convertToComponentRecognized(recognizedLaComponents));
        return componentsRecognized;
    }

    private StudentMobilityForStudies.ComponentsStudied convertToComponentsStudied(List<StudiedLaComponent> studiedLaComponents) {
        StudentMobilityForStudies.ComponentsStudied componentsStudied = new StudentMobilityForStudies.ComponentsStudied();
        componentsStudied.setLatestApprovedChanges(convertToComponentStudied(studiedLaComponents));
        return componentsStudied;
    }
}
