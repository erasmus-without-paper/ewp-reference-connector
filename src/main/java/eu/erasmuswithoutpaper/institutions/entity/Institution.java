package eu.erasmuswithoutpaper.institutions.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INSTITUTION")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IID")
    private String iid;

    @Column(name = "COUNTRY")
    private String country;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "URL")
    private String url;
    
    public Institution() {
    }
    public Institution(String id, String country, String name, String url) {
        this.iid = id;
        this.country = country;
        this.name = name;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getIid() {
        return iid;
    }

    public void setIid(String id) {
        this.iid = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
