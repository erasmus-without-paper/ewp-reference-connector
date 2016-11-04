
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class IiaPartnerId implements Serializable{

    private String iiaId;
    private String institutionId;
    private String organizationUnitId;
    
    public IiaPartnerId(){
    }

    public IiaPartnerId(String iiaId, String institutionId, String organizationUnitId) {
        this.iiaId = iiaId;
        this.institutionId = institutionId;
        this.organizationUnitId = organizationUnitId;
    }
    
    public String getIiaId() {
        return iiaId;
    }

    public void setIiaId(String iiaId) {
        this.iiaId = iiaId;
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
    
    
}
