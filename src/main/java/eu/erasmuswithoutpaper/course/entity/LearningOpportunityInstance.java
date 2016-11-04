
package eu.erasmuswithoutpaper.course.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class LearningOpportunityInstance implements Serializable {

    @EmbeddedId
    private LearningOpportunityInstanceId learningOpportunityInstanceId;
    private String credits;

    public LearningOpportunityInstanceId getLearningOpportunityInstanceId() {
        return learningOpportunityInstanceId;
    }

    public void setLearningOpportunityInstanceId(LearningOpportunityInstanceId learningOpportunityInstanceId) {
        this.learningOpportunityInstanceId = learningOpportunityInstanceId;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
    
}
