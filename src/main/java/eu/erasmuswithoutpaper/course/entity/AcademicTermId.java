
package eu.erasmuswithoutpaper.course.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class AcademicTermId implements Serializable{

    private String institutionId;
    private String organizationUnitId;
    private String termName;

    public AcademicTermId(){
    }
    
    public AcademicTermId(String institutionId, String organizationUnitId, String termName) {
        this.institutionId = institutionId;
        this.organizationUnitId = organizationUnitId;
        this.termName = termName;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
    
}
