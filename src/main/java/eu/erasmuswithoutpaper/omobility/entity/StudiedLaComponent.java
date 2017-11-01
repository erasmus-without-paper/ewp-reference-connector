
package eu.erasmuswithoutpaper.omobility.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StudiedLaComponent implements Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String losId;
    private String loiId;
    private String title;
    private String losCode;
    private String academicTermDisplayName;
    private LearningAgreementComponentStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLosId() {
        return losId;
    }

    public void setLosId(String losId) {
        this.losId = losId;
    }

    public String getLoiId() {
        return loiId;
    }

    public void setLoiId(String loiId) {
        this.loiId = loiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }

    public String getAcademicTermDisplayName() {
        return academicTermDisplayName;
    }

    public void setAcademicTermDisplayName(String academicTermDisplayName) {
        this.academicTermDisplayName = academicTermDisplayName;
    }
    
    public LearningAgreementComponentStatus getStatus() {
        return status;
    }

    public void setStatus(LearningAgreementComponentStatus status) {
        this.status = status;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final RecognizedLaComponent other = (RecognizedLaComponent) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
