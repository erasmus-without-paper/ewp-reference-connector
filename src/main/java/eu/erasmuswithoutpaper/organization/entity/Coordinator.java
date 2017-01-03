
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = Coordinator.findAll, query = "SELECT c FROM Coordinator c"),
    @NamedQuery(name = Coordinator.findByInstAndOrgUnit, query = "SELECT c FROM Coordinator c WHERE c.institutionId = :institutionId AND c.organizationUnitId = :organizationUnitId"),
    @NamedQuery(name = Coordinator.findByInstWithNoOrgUnit, query = "SELECT c FROM Coordinator c WHERE c.institutionId = :institutionId AND c.organizationUnitId is NULL")
})
public class Coordinator implements Serializable{

    private static final String PREFIX = "eu.erasmuswithoutpaper.organization.entity.Coordinator.";
    public static final String findAll = PREFIX + "all";
    public static final String findByInstAndOrgUnit = PREFIX + "byInstAndOrgUnit";
    public static final String findByInstWithNoOrgUnit = PREFIX + "byInstWithNoOrgUnit";
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private String institutionId;
    private String organizationUnitId;
    private CoordinatorRoles role;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    private Person person;

    public Coordinator(){}
    public Coordinator(String institutionId, String organizationUnitId, CoordinatorRoles role) {
        this.institutionId = institutionId;
        this.organizationUnitId = organizationUnitId;
        this.role = role;
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

    public CoordinatorRoles getRole() {
        return role;
    }

    public void setRole(CoordinatorRoles role) {
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Coordinator other = (Coordinator) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
