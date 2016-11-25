
package eu.erasmuswithoutpaper.course.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = AcademicYear.findAll, query = "SELECT a FROM AcademicYear a"),
    @NamedQuery(name = AcademicYear.findByKeys, query = "SELECT a FROM AcademicYear a WHERE a.startYear = :startYear AND a.endYear = :endYear")
})
public class AcademicYear implements Serializable {

    private static final String PREFIX = "eu.erasmuswithoutpaper.course.entity.AcademicYear.";
    public static final String findAll = PREFIX + "all";
    public static final String findByKeys = PREFIX + "byKeys";
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String startYear;
    private String endYear;

    public AcademicYear() {
    }

    public AcademicYear(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }
    
    public String getAcademicYear(){
        return this.startYear + "/" + this.endYear;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final AcademicYear other = (AcademicYear) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
