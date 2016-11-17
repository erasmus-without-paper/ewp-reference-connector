
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LearningAgreement implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String mobilityId;
    private int mobilityRevision;
    private int learningAgreementRevision;

    public LearningAgreement() {}
    public LearningAgreement(String mobilityId, int mobilityRevision, int learningAgreementRevision) {
        this.mobilityId = mobilityId;
        this.mobilityRevision = mobilityRevision;
        this.learningAgreementRevision = learningAgreementRevision;
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

    public int getLearningAgreementRevision() {
        return learningAgreementRevision;
    }

    public void setLearningAgreementRevision(int learningAgreementRevision) {
        this.learningAgreementRevision = learningAgreementRevision;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final LearningAgreement other = (LearningAgreement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
