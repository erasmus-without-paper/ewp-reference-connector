/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.department.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * Department.
 */
@Entity
@Table(name = "DEPARTMENT")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;
    
    @Column(name = "OWNER_UNIT_ID")
    private String ownerUnitId;
    
    @Column(name = "NAME")
    private String name;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Column(name = "COUNTRY")
    private String country;
    
    @Column(name = "URL")
    private String url;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "RECLANGCOMP_ID")
    private RecLangComp recLangComp;
    
    public Department() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String iid) {
        this.departmentId = iid;
    }

    public String getOwnerUnitId() {
        return ownerUnitId;
    }

    public void setOwnerUnitId(String ownerUnitId) {
        this.ownerUnitId = ownerUnitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RecLangComp getRecLangComp() {
        return recLangComp;
    }

    public void setRecLangComp(RecLangComp RecLangComp) {
        this.recLangComp = RecLangComp;
    }

    
}
