
package eu.erasmuswithoutpaper.course.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class LearningOpportunitySpecification implements Serializable{
    
    @EmbeddedId
    private LearningOpportunitySpecificationId learningOpportunitySpecificationId;
    private String type;
    private String name;
    private String description;

    public LearningOpportunitySpecificationId getLearningOpportunitySpecificationId() {
        return learningOpportunitySpecificationId;
    }

    public void setLearningOpportunitySpecificationId(LearningOpportunitySpecificationId learningOpportunitySpecificationId) {
        this.learningOpportunitySpecificationId = learningOpportunitySpecificationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
