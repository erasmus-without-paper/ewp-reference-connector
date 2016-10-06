package eu.erasmuswithoutpaper.iias.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PARTNER_CONDITION")
public class Condition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONDITION_ID")
    private Long id;

    @Column(name = "SUBJECT_AREA")
    private float subjectArea;
    
    @ElementCollection
    protected List<String> mobilityType;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "term_id")
    private Term term;

    @ManyToOne(cascade=CascadeType.ALL)
    private Partner partner;
    
    public Condition(float subjectArea) {
        this.subjectArea = subjectArea;
    }
    
    public Condition() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(float subjectArea) {
        this.subjectArea = subjectArea;
    }

    public List<String> getMobilityType() {
        return mobilityType;
    }

    public void setMobilityType(List<String> mobilityType) {
        this.mobilityType = mobilityType;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    
    
}
