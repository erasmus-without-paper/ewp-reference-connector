
package eu.erasmuswithoutpaper.mobility.entity;

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
import javax.persistence.OneToMany;

@Entity
public class ResultDistribution implements Serializable{

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "DISTR_CATEGORIES")
    private List<ResultDistributionCategory> resultDistributionCategory;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "DISTR_DESCRIPTION")
    private List<LanguageItem> description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ResultDistributionCategory> getResultDistributionCategory() {
        return resultDistributionCategory;
    }

    public void setResultDistributionCategory(List<ResultDistributionCategory> resultDistributionCategory) {
        this.resultDistributionCategory = resultDistributionCategory;
    }

    public List<LanguageItem> getDescription() {
        return description;
    }

    public void setDescription(List<LanguageItem> description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
