
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CooperationCondition implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private String iiaId;
    private String fromInstitutionId;
    private String toInstitutionId;
    private String fromOrganizationUnitId;
    private String toOrganizationUnitId;
    private CooperationConditionMobilityType mobilityType;
    
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private CooperationConditionMobilityNumberVariant mobilityNumberVariant;
    private String duration;
    private String eqfLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIiaId() {
        return iiaId;
    }

    public void setIiaId(String iiaId) {
        this.iiaId = iiaId;
    }

    public String getFromInstitutionId() {
        return fromInstitutionId;
    }

    public void setFromInstitutionId(String fromInstitutionId) {
        this.fromInstitutionId = fromInstitutionId;
    }

    public String getToInstitutionId() {
        return toInstitutionId;
    }

    public void setToInstitutionId(String toInstitutionId) {
        this.toInstitutionId = toInstitutionId;
    }

    public String getFromOrganizationUnitId() {
        return fromOrganizationUnitId;
    }

    public void setFromOrganizationUnitId(String fromOrganizationUnitId) {
        this.fromOrganizationUnitId = fromOrganizationUnitId;
    }

    public String getToOrganizationUnitId() {
        return toOrganizationUnitId;
    }

    public void setToOrganizationUnitId(String toOrganizationUnitId) {
        this.toOrganizationUnitId = toOrganizationUnitId;
    }

    public CooperationConditionMobilityType getMobilityType() {
        return mobilityType;
    }

    public void setMobilityType(CooperationConditionMobilityType mobilityType) {
        this.mobilityType = mobilityType;
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

    public CooperationConditionMobilityNumberVariant getMobilityNumberVariant() {
        return mobilityNumberVariant;
    }

    public void setMobilityNumberVariant(CooperationConditionMobilityNumberVariant mobilityNumberVariant) {
        this.mobilityNumberVariant = mobilityNumberVariant;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEqfLevel() {
        return eqfLevel;
    }

    public void setEqfLevel(String eqfLevel) {
        this.eqfLevel = eqfLevel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final CooperationCondition other = (CooperationCondition) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
