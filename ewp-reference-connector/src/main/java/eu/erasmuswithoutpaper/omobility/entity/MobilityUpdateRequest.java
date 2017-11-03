
package eu.erasmuswithoutpaper.omobility.entity;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.johnzon.mapper.JohnzonConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = MobilityUpdateRequest.findAll, query = "SELECT m FROM MobilityUpdateRequest m")
})
public class MobilityUpdateRequest implements Serializable {
    private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.entity.MobilityUpdateRequest.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private MobilityUpdateRequestType type;
    private String sendingHeiId;
    
    @Lob
    private String updateInformation;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date updateRequestDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MobilityUpdateRequestType getType() {
        return type;
    }

    public void setType(MobilityUpdateRequestType type) {
        this.type = type;
    }

    public String getSendingHeiId() {
        return sendingHeiId;
    }

    public void setSendingHeiId(String sendingHeiId) {
        this.sendingHeiId = sendingHeiId;
    }

    public String getUpdateInformation() {
        return updateInformation;
    }

    public void setUpdateInformation(String updateInformation) {
        this.updateInformation = updateInformation;
    }

    public Date getUpdateRequestDate() {
        return updateRequestDate;
    }

    public void setUpdateRequestDate(Date updateRequestDate) {
        this.updateRequestDate = updateRequestDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final MobilityUpdateRequest other = (MobilityUpdateRequest) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
