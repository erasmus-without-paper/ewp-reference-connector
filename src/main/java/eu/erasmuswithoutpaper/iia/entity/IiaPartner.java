
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IiaPartner implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String iiaId;
    private String institutionId;
    private String organizationUnitId;

    public IiaPartner() {}
    public IiaPartner(String iiaId, String institutionId, String organizationUnitId) {
        this.iiaId = iiaId;
        this.institutionId = institutionId;
        this.organizationUnitId = organizationUnitId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final IiaPartner other = (IiaPartner) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
