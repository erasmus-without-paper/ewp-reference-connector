package eu.erasmuswithoutpaper.iias.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    
    
}
