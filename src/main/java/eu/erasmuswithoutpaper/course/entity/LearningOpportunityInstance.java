
package eu.erasmuswithoutpaper.course.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LearningOpportunityInstance implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private String institutionId;
    private String losCode;
    private String termName;
    private String credits;

    public LearningOpportunityInstance() {}
    public LearningOpportunityInstance(String institutionId, String losCode, String termName) {
        this.institutionId = institutionId;
        this.losCode = losCode;
        this.termName = termName;
    }

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

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final LearningOpportunityInstance other = (LearningOpportunityInstance) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
