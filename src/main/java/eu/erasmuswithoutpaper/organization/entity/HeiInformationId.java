
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class HeiInformationId implements Serializable{

    private String institutionId;
    private String organizationUnitId;
    private String header;
    
    public HeiInformationId() {
    }

    public HeiInformationId(String institutionId, String organizationUnitId, String header) {
        this.institutionId = institutionId;
        this.organizationUnitId = organizationUnitId;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    
}
