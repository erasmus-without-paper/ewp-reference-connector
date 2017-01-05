
package eu.erasmuswithoutpaper.course.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity(name="loi")
@NamedQuery(name = LearningOpportunityInstance.findAll, query = "SELECT l FROM loi l")
public class LearningOpportunityInstance implements Serializable {
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance.";
    public static final String findAll = PREFIX + "all";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private String organizationUnitId;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ACADEMIC_TERM_ID", referencedColumnName = "ID")
    private AcademicTerm academicTerm;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "loi_credits")
    private List<Credit> credits;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }
    
    public AcademicTerm getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(AcademicTerm academicTerm) {
        this.academicTerm = academicTerm;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final LearningOpportunityInstance other = (LearningOpportunityInstance) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
