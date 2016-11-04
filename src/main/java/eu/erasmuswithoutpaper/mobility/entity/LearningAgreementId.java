
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class LearningAgreementId implements Serializable{
    
    private String mobilityId;
    private int mobilityRevision;
    private int learningAgreementRevision;

    public LearningAgreementId() {
    }

    public LearningAgreementId(String mobilityId, int mobilityRevision, int learningAgreementRevision) {
        this.mobilityId = mobilityId;
        this.mobilityRevision = mobilityRevision;
        this.learningAgreementRevision = learningAgreementRevision;
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
    
}
