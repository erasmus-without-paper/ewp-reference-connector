
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Mobility implements Serializable{

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String mobilityId;
    private int mobilityRevision;
    private String iiaId;
    private String sendingInstitutionId;
    private String receivingInstitutionId;
    private String sendingOrganizationUnitId;
    private String receivingOrganizationUnitId;
    private String personId;
    private String mobilityType;
    private MobilityStatus status;
    
    @Temporal(TemporalType.DATE)
    private Date plannedArrivalDate;
    
    @Temporal(TemporalType.DATE)
    private Date actualArrivalDate;
    
    @Temporal(TemporalType.DATE)
    private Date plannedDepartureDate;
    
    @Temporal(TemporalType.DATE)
    private Date actualDepartureDate;
    
    private String iscedCode;
    private String eqfLevel;

    public Mobility() {}
    public Mobility(String mobilityId, int mobilityRevision) {
        this.mobilityId = mobilityId;
        this.mobilityRevision = mobilityRevision;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobilityId() {
        return mobilityId;
    }

    public void setMobilityId(String mobilityId) {
        this.mobilityId = mobilityId;
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getMobilityType() {
        return mobilityType;
    }

    public void setMobilityType(String mobilityType) {
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

    public Date getActualArrivalDate() {
        return actualArrivalDate;
    }

    public void setActualArrivalDate(Date actualArrivalDate) {
        this.actualArrivalDate = actualArrivalDate;
    }

    public Date getPlannedDepartureDate() {
        return plannedDepartureDate;
    }

    public void setPlannedDepartureDate(Date plannedDepartureDate) {
        this.plannedDepartureDate = plannedDepartureDate;
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

    public String getEqfLevel() {
        return eqfLevel;
    }

    public void setEqfLevel(String eqfLevel) {
        this.eqfLevel = eqfLevel;
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
