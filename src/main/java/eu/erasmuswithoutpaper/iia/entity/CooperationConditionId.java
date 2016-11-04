
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class CooperationConditionId implements Serializable{
    
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

    public CooperationConditionId() {
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
    
}
