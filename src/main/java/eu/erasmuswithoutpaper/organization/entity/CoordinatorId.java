
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class CoordinatorId implements Serializable{
    
    private String institutionId;
    private String organizationUnitId;
    private String personId;
    private String header;

    public CoordinatorId() {
    }
    
    public CoordinatorId(String instId, String unitId, String personId, String header) {
        this.institutionId = instId;
        this.organizationUnitId = unitId;
        this.personId = personId;
        this.header = header;
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    
}
