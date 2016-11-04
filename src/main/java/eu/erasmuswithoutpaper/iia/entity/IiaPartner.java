
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class IiaPartner implements Serializable{
    
    @EmbeddedId
    private IiaPartnerId iiaPartnerId;

    public IiaPartnerId getIiaPartnerId() {
        return iiaPartnerId;
    }

    public void setIiaPartnerId(IiaPartnerId iiaPartnerId) {
        this.iiaPartnerId = iiaPartnerId;
    }
    
}
