
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class LearningAgreement implements Serializable{
    
    @EmbeddedId
    private LearningAgreementId learningAgreementId;

    public LearningAgreementId getLearningAgreementId() {
        return learningAgreementId;
    }

    public void setLearningAgreementId(LearningAgreementId learningAgreementId) {
        this.learningAgreementId = learningAgreementId;
    }
    
}
