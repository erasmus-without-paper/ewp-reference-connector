
package eu.erasmuswithoutpaper.iia.entity;

import eu.erasmuswithoutpaper.organization.entity.Coordinator;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQuery(name = IiaPartner.findAll, query = "SELECT i FROM IiaPartner i")
public class IiaPartner implements Serializable{
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.course.entity.IiaPartner.";
    public static final String findAll = PREFIX + "all";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String iiaId;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "iia_partner_inst")
    private Institution institution;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "iia_partner_org")
    private OrganizationUnit organizationUnit;
    
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "iia_partner_coordinator")
    private List<Coordinator> coordinators;

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

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public OrganizationUnit getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(OrganizationUnit organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public List<Coordinator> getCoordinators() {
        return coordinators;
    }

    public void setCoordinators(List<Coordinator> coordinators) {
        this.coordinators = coordinators;
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
