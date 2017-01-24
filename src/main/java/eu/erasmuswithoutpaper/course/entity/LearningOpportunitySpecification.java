
package eu.erasmuswithoutpaper.course.entity;

import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity(name="los")
@NamedQueries({
    @NamedQuery(name = LearningOpportunitySpecification.findAll, query = "SELECT l FROM los l"),
    @NamedQuery(name = LearningOpportunitySpecification.findAllTopLevelParents, query = "SELECT l FROM los l WHERE l.topLevelParent = true"),
    @NamedQuery(name = LearningOpportunitySpecification.findByLosCode, query = "SELECT l FROM los l WHERE l.losCode = :losCode"),
    @NamedQuery(name = LearningOpportunitySpecification.findByInstitutionId, query = "SELECT l FROM los l WHERE l.institutionId = :institutionId")
})
public class LearningOpportunitySpecification implements Serializable{
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.course.entity.LearningOpportunitySpecification.";
    public static final String findAll = PREFIX + "all";
    public static final String findAllTopLevelParents = PREFIX + "allTopLevelParents";
    public static final String findByLosCode = PREFIX + "byLosCode";
    public static final String findByInstitutionId = PREFIX + "byInstitutionId";

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String institutionId;
    private String losCode;
    private LearningOpportunitySpecificationType type;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "LOS_NAME")
    private List<LanguageItem> name;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "LOS_URLS")
    private List<LanguageItem> url;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "LOS_LOS")
    private List<LearningOpportunitySpecification> learningOpportunitySpecifications;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "LOS_LOI")
    private List<LearningOpportunityInstance> learningOpportunityInstances;
    
    private boolean topLevelParent;

    public LearningOpportunitySpecification() {}
    
    public LearningOpportunitySpecification(String institutionId, String losCode) {
        this.institutionId = institutionId;
        this.losCode = losCode;
    }

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

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }

    public LearningOpportunitySpecificationType getType() {
        return type;
    }

    public void setType(LearningOpportunitySpecificationType type) {
        this.type = type;
    }

    public List<LanguageItem> getName() {
        return name;
    }

    public void setName(List<LanguageItem> name) {
        this.name = name;
    }

    public List<LanguageItem> getUrl() {
        return url;
    }

    public void setUrl(List<LanguageItem> url) {
        this.url = url;
    }

    public List<LearningOpportunitySpecification> getLearningOpportunitySpecifications() {
        return learningOpportunitySpecifications;
    }

    public void setLearningOpportunitySpecifications(List<LearningOpportunitySpecification> learningOpportunitySpecifications) {
        this.learningOpportunitySpecifications = learningOpportunitySpecifications;
    }

    public List<LearningOpportunityInstance> getLearningOpportunityInstances() {
        return learningOpportunityInstances;
    }

    public void setLearningOpportunityInstances(List<LearningOpportunityInstance> learningOpportunityInstances) {
        this.learningOpportunityInstances = learningOpportunityInstances;
    }

    public boolean isTopLevelParent() {
        return topLevelParent;
    }

    public void setTopLevelParent(boolean topLevelParent) {
        this.topLevelParent = topLevelParent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
