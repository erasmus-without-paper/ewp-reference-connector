package eu.erasmuswithoutpaper.iias.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Iia implements Serializable {
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

    public Long getIiaId() {
        return iiaId;
    }

    public void setIiaId(Long iiaId) {
        this.iiaId = iiaId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
