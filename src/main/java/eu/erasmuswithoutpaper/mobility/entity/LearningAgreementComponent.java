
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LearningAgreementComponent implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String institutionId;
    private String organizationUnitId;
    private String mobilityId;
    private int mobilityRevision;
    private int learningAgreementRevision;
    private String termName;
    private String losCode;
    private LearningAgreementComponentStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
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

    public int getLearningAgreementRevision() {
        return learningAgreementRevision;
    }

    public void setLearningAgreementRevision(int learningAgreementRevision) {
        this.learningAgreementRevision = learningAgreementRevision;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }

    public LearningAgreementComponentStatus getStatus() {
        return status;
    }

    public void setStatus(LearningAgreementComponentStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final LearningAgreementComponent other = (LearningAgreementComponent) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
