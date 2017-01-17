
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LearningAgreement implements Serializable{
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String mobilityId;
    private int mobilityRevision;
    private int learningAgreementRevision;

    public LearningAgreement() {}
    public LearningAgreement(String mobilityId, int mobilityRevision, int learningAgreementRevision) {
        this.mobilityId = mobilityId;
        this.mobilityRevision = mobilityRevision;
        this.learningAgreementRevision = learningAgreementRevision;
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

    public int getLearningAgreementRevision() {
        return learningAgreementRevision;
    }

    public void setLearningAgreementRevision(int learningAgreementRevision) {
        this.learningAgreementRevision = learningAgreementRevision;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
