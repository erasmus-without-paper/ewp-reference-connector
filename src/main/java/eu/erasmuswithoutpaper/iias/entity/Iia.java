package eu.erasmuswithoutpaper.iias.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IIA")
public class Iia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IIA_ID")
    private Long iiaId;

    @Column(name = "ID")
    private String id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "partner_id")
    private Partner partner;
    
    public Iia() {
    }
    public Iia(String id, Partner partner) {
        this.id = id;
        this.partner = partner;
    }

    public Long getId() {
        return iiaId;
    }

    public void setId(Long iiaId) {
        this.iiaId = iiaId;
    }
    public String getIid() {
        return id;
    }

    public void setIid(String id) {
        this.id = id;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
