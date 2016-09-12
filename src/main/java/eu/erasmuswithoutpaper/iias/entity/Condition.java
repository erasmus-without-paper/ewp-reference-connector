/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.iias.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PARTNER_CONDITION")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONDITION_ID")
    private Long id;
    
    @Column(name = "SUBJECT_AREA")
    private float subjectArea;

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
