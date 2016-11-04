
package eu.erasmuswithoutpaper.course.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class LearningOpportunityInstanceId implements Serializable{

    private String institutionId;
    private String losCode;
    private String termName;

    public LearningOpportunityInstanceId(){
    }
    
    public LearningOpportunityInstanceId(String institutionId, String losCode, String termName) {
        this.institutionId = institutionId;
        this.losCode = losCode;
        this.termName = termName;
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

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
    
    
}
