
package eu.erasmuswithoutpaper.course.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class LearningOpportunitySpecificationId implements Serializable{
    
    private String institutionId;
    private String losCode;

    public LearningOpportunitySpecificationId() {
    }
    
    public LearningOpportunitySpecificationId(String institutionId, String losCode) {
        this.institutionId = institutionId;
        this.losCode = losCode;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }
    
}
