
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HeiInformation implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private String institutionId;
    private String organizationUnitId;
    private String header;
    private String content;

    public HeiInformation() {}
    public HeiInformation(String institutionId, String organizationUnitId, String header) {
        this.institutionId = institutionId;
        this.organizationUnitId = organizationUnitId;
        this.header = header;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final HeiInformation other = (HeiInformation) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
