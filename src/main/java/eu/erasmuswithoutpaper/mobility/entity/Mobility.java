
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Mobility implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
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
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private String iscedCode;
    private String eqfLevel;

    public Mobility() {}
    public Mobility(String mobilityId, int mobilityRevision) {
        this.mobilityId = mobilityId;
        this.mobilityRevision = mobilityRevision;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        hash = 19 * hash + (int) (this.id ^ (this.id >>> 32));
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
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
