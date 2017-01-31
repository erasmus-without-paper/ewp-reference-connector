package eu.erasmuswithoutpaper.course.entity;

import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = GradingScheme.findAll, query = "SELECT a FROM AcademicYear a"),
})
public class GradingScheme implements Serializable {
    private static final String PREFIX = "eu.erasmuswithoutpaper.course.entity.GradingScheme.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private List<LanguageItem> label;
    private List<LanguageItem> description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LanguageItem> getLabel() {
        return label;
    }

    public void setLabel(List<LanguageItem> label) {
        this.label = label;
    }

    public List<LanguageItem> getDescription() {
        return description;
    }

    public void setDescription(List<LanguageItem> description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final GradingScheme other = (GradingScheme) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
