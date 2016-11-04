
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class LearningAgreementComponent implements Serializable{
    
    @EmbeddedId
    private LearningAgreementComponentId learningAgreementComponentId;
    private LearningAgreementComponentStatus status;

    public LearningAgreementComponentId getLearningAgreementComponentId() {
        return learningAgreementComponentId;
    }

    public void setLearningAgreementComponentId(LearningAgreementComponentId learningAgreementComponentId) {
        this.learningAgreementComponentId = learningAgreementComponentId;
    }

    public LearningAgreementComponentStatus getStatus() {
        return status;
    }

    public void setStatus(LearningAgreementComponentStatus status) {
        this.status = status;
    }

}
