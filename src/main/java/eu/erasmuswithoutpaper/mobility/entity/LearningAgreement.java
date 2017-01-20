
package eu.erasmuswithoutpaper.mobility.entity;

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
public class LearningAgreement implements Serializable {
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private int learningAgreementRevision;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "LA_AGREEMENT_COMPONENT")
    private List<LearningAgreementComponent> laComponents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLearningAgreementRevision() {
        return learningAgreementRevision;
    }

    public void setLearningAgreementRevision(int learningAgreementRevision) {
        this.learningAgreementRevision = learningAgreementRevision;
    }

    public List<LearningAgreementComponent> getLaComponents() {
        return laComponents;
    }

    public void setLaComponents(List<LearningAgreementComponent> laComponents) {
        this.laComponents = laComponents;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final LearningAgreement other = (LearningAgreement) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
