
package eu.erasmuswithoutpaper.omobility.entity;

import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.internal.StandardDateConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.johnzon.mapper.JohnzonConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = Mobility.findAll, query = "SELECT m FROM Mobility m"),
    @NamedQuery(name = Mobility.findBySendingInstitutionId, query = "SELECT m FROM Mobility m WHERE m.sendingInstitutionId=:sendingInstitutionId"),
    @NamedQuery(name = Mobility.findByReceivingInstitutionId, query = "SELECT m FROM Mobility m WHERE m.receivingInstitutionId=:receivingInstitutionId"),
})
public class Mobility implements Serializable {

    private static final String PREFIX = "eu.erasmuswithoutpaper.mobility.entity.Mobility.";
    public static final String findAll = PREFIX + "all";
    public static final String findBySendingInstitutionId = PREFIX + "findBySendingInstitutionId";
    public static final String findByReceivingInstitutionId = PREFIX + "findByReceivingInstitutionId";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private int mobilityRevision;
    private String iiaId;
    private String cooperationConditionId;
    private String sendingInstitutionId;
    private String sendingOrganizationUnitId;
    private String receivingInstitutionId;
    private String receivingOrganizationUnitId;
    private String mobilityParticipantId;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "MOBILITY_TYPE_ID", referencedColumnName = "ID")
    private MobilityType mobilityType;
    
    private MobilityStatus status;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date plannedArrivalDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date plannedDepartureDate;

    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date actualArrivalDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date actualDepartureDate;
    
    private String iscedCode;
    private byte eqfLevel;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "LEARNING_AGREEMENT")
    private LearningAgreement learningAgreement;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMobilityRevision() {
        return mobilityRevision;
    }

    public void setMobilityRevision(int mobilityRevision) {
        this.mobilityRevision = mobilityRevision;
    }

    public String getIiaId() {
        return iiaId;
    }

    public void setIiaId(String iiaId) {
        this.iiaId = iiaId;
    }

    public String getCooperationConditionId() {
        return cooperationConditionId;
    }

    public void setCooperationConditionId(String cooperationConditionId) {
        this.cooperationConditionId = cooperationConditionId;
    }
    
    public String getSendingInstitutionId() {
        return sendingInstitutionId;
    }

    public void setSendingInstitutionId(String sendingInstitutionId) {
        this.sendingInstitutionId = sendingInstitutionId;
    }

    public String getReceivingInstitutionId() {
        return receivingInstitutionId;
    }

    public void setReceivingInstitutionId(String receivingInstitutionId) {
        this.receivingInstitutionId = receivingInstitutionId;
    }

    public String getSendingOrganizationUnitId() {
        return sendingOrganizationUnitId;
    }

    public void setSendingOrganizationUnitId(String sendingOrganizationUnitId) {
        this.sendingOrganizationUnitId = sendingOrganizationUnitId;
    }

    public String getReceivingOrganizationUnitId() {
        return receivingOrganizationUnitId;
    }

    public void setReceivingOrganizationUnitId(String receivingOrganizationUnitId) {
        this.receivingOrganizationUnitId = receivingOrganizationUnitId;
    }

    public String getMobilityParticipantId() {
        return mobilityParticipantId;
    }

    public void setMobilityParticipantId(String mobilityParticipantId) {
        this.mobilityParticipantId = mobilityParticipantId;
    }

    public MobilityType getMobilityType() {
        return mobilityType;
    }

    public void setMobilityType(MobilityType mobilityType) {
        this.mobilityType = mobilityType;
    }

    public MobilityStatus getStatus() {
        return status;
    }

    public void setStatus(MobilityStatus status) {
        this.status = status;
    }

    public Date getPlannedArrivalDate() {
        return plannedArrivalDate;
    }

    public void setPlannedArrivalDate(Date plannedArrivalDate) {
        this.plannedArrivalDate = plannedArrivalDate;
    }

    public Date getPlannedDepartureDate() {
        return plannedDepartureDate;
    }

    public void setPlannedDepartureDate(Date plannedDepartureDate) {
        this.plannedDepartureDate = plannedDepartureDate;
    }

    public Date getActualArrivalDate() {
        return actualArrivalDate;
    }

    public void setActualArrivalDate(Date actualArrivalDate) {
        this.actualArrivalDate = actualArrivalDate;
    }

    public Date getActualDepartureDate() {
        return actualDepartureDate;
    }

    public void setActualDepartureDate(Date actualDepartureDate) {
        this.actualDepartureDate = actualDepartureDate;
    }

    public String getIscedCode() {
        return iscedCode;
    }

    public void setIscedCode(String iscedCode) {
        this.iscedCode = iscedCode;
    }

    public byte getEqfLevel() {
        return eqfLevel;
    }

    public void setEqfLevel(byte eqfLevel) {
        this.eqfLevel = eqfLevel;
    }

    public LearningAgreement getLearningAgreement() {
        return learningAgreement;
    }

    public void setLearningAgreement(LearningAgreement learningAgreement) {
        this.learningAgreement = learningAgreement;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mobility other = (Mobility) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
