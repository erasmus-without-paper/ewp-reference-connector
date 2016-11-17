
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
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = Coordinator.findAll, query = "SELECT c FROM Coordinator c")
public class Coordinator implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private static final String PREFIX = "eu.erasmuswithoutpaper.organization.entity.Coordinator.";
    public static final String findAll = PREFIX + "all";
    
    private String institutionId;
    private String organizationUnitId;
    private CoordinatorHeader header;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    private Person person;

    public Coordinator(){}
    public Coordinator(String institutionId, String organizationUnitId, CoordinatorHeader header) {
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

    public CoordinatorHeader getHeader() {
        return header;
    }

    public void setHeader(CoordinatorHeader header) {
        this.header = header;
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
