
package eu.erasmuswithoutpaper.course.entity;

import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AcademicTerm implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String institutionId;
    private String organizationUnitId;
    private String termName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "academic_term_name")
    private List<LanguageItem> dispName;
    
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public AcademicTerm(){
    }
    
    public AcademicTerm(String institutionId, String organizationUnitId, String termName) {
        this.institutionId = institutionId;
        this.organizationUnitId = organizationUnitId;
        this.termName = termName;
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

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public List<LanguageItem> getDispName() {
        return dispName;
    }

    public void setDispName(List<LanguageItem> dispName) {
        this.dispName = dispName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final AcademicTerm other = (AcademicTerm) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
