
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ResultDistribution implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String institutionId;
    private String losCode;
    private String AcademicYearId;
    private String AcademicTermId;
    private String label;
    private int distrubutionCount;

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

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }

    public String getAcademicYearId() {
        return AcademicYearId;
    }

    public void setAcademicYearId(String AcademicYearId) {
        this.AcademicYearId = AcademicYearId;
    }

    public String getAcademicTermId() {
        return AcademicTermId;
    }

    public void setAcademicTermId(String AcademicTermId) {
        this.AcademicTermId = AcademicTermId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDistrubutionCount() {
        return distrubutionCount;
    }

    public void setDistrubutionCount(int distrubutionCount) {
        this.distrubutionCount = distrubutionCount;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final ResultDistribution other = (ResultDistribution) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
