
package eu.erasmuswithoutpaper.course.entity;

import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
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
    @NamedQuery(name = LearningOpportunitySpecification.findAll, query = "SELECT l FROM LearningOpportunitySpecification l"),
    @NamedQuery(name = LearningOpportunitySpecification.findByLosCode, query = "SELECT l FROM LearningOpportunitySpecification l WHERE l.losCode = :losCode"),
    @NamedQuery(name = LearningOpportunitySpecification.findByInstitutionId, query = "SELECT l FROM LearningOpportunitySpecification l WHERE l.institutionId = :institutionId")
})
public class LearningOpportunitySpecification implements Serializable{
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification.";
    public static final String findAll = PREFIX + "all";
    public static final String findByLosCode = PREFIX + "byLosCode";
    public static final String findByInstitutionId = PREFIX + "byInstitutionId";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private String institutionId;
    private String losCode;
    private String type;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "los_name")
    private List<LanguageItem> name;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "los_descr")
    private List<LanguageItem> description;

    public LearningOpportunitySpecification() {}
    
    public LearningOpportunitySpecification(String institutionId, String losCode) {
        this.institutionId = institutionId;
        this.losCode = losCode;
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

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LanguageItem> getName() {
        return name;
    }

    public void setName(List<LanguageItem> name) {
        this.name = name;
    }

    public List<LanguageItem> getDescription() {
        return description;
    }

    public void setDescription(List<LanguageItem> description) {
        this.description = description;
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
        final LearningOpportunitySpecification other = (LearningOpportunitySpecification) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
