
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = Institution.findAll, query = "SELECT i FROM Institution i"),
    @NamedQuery(name = Institution.findByInstitutionId, query = "SELECT i FROM Institution i WHERE i.institutionId = :institutionId")
})
public class Institution implements Serializable{

    private static final String PREFIX = "eu.erasmuswithoutpaper.organization.entity.Institution.";
    public static final String findAll = PREFIX + "all";
    public static final String findByInstitutionId = PREFIX + "byInstitutionId";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private String institutionId;
    private String otherId;
    private String country;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "institution_name")
    private List<LanguageItem> name;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "institution_descr")
    private List<LanguageItem> description;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "inst_org_unit")
    private List<OrganizationUnit> organizationUnits;

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

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public List<LanguageItem> getName() {
        return name;
    }

    public void setName(List<LanguageItem> name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<LanguageItem> getDescription() {
        return description;
    }

    public void setDescription(List<LanguageItem> description) {
        this.description = description;
    }

    public List<OrganizationUnit> getOrganizationUnits() {
        return organizationUnits;
    }

    public void setOrganizationUnits(List<OrganizationUnit> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Institution other = (Institution) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
