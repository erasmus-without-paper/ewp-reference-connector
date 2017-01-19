
package eu.erasmuswithoutpaper.course.entity;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;
import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.johnzon.mapper.JohnzonConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = AcademicTerm.findAll, query = "SELECT a FROM AcademicTerm a"),
    @NamedQuery(name = AcademicTerm.findByAcademicYearAndTermId, query = "SELECT a FROM AcademicTerm a WHERE a.academicYear.id = :academicYearId AND a.academicTermId = :academicTermId")
})
public class AcademicTerm implements Serializable{

    private static final String PREFIX = "eu.erasmuswithoutpaper.course.entity.AcademicTerm.";
    public static final String findAll = PREFIX + "all";
    public static final String findByAcademicYearAndTermId = PREFIX + "byAcademicYearAndTermId";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String institutionId;
    private String organizationUnitId;
    private String academicTermId;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "academic_term_name")
    private List<LanguageItem> dispName;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public String getAcademicTermId() {
        return academicTermId;
    }

    public void setAcademicTermId(String academicTermId) {
        this.academicTermId = academicTermId;
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
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
