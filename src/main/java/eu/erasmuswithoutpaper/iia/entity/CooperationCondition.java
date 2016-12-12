
package eu.erasmuswithoutpaper.iia.entity;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.johnzon.mapper.JohnzonConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = CooperationCondition.findAll, query = "SELECT c FROM CooperationCondition c"),
    @NamedQuery(name = CooperationCondition.findByIiaId, query = "SELECT c FROM CooperationCondition c WHERE c.iiaId = :iiaId")
})
public class CooperationCondition implements Serializable{
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.CooperationCondition.";
    public static final String findAll = PREFIX + "all";
    public static final String findByIiaId = PREFIX + "byIiaId";
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    private String iiaId;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "SENDING_PARTNER_ID", referencedColumnName = "ID")
    private IiaPartner sendingPartner;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "RECEIVING_PARTNER_ID", referencedColumnName = "ID")
    private IiaPartner receivingPartner;
    
    private CooperationConditionMobilityType mobilityType;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    private CooperationConditionMobilityNumberVariant mobilityNumberVariant;
    private String duration;
    private String eqfLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIiaId() {
        return iiaId;
    }

    public void setIiaId(String iiaId) {
        this.iiaId = iiaId;
    }

    public IiaPartner getSendingPartner() {
        return sendingPartner;
    }

    public void setSendingPartner(IiaPartner sendingPartner) {
        this.sendingPartner = sendingPartner;
    }

    public IiaPartner getReceivingPartner() {
        return receivingPartner;
    }

    public void setReceivingPartner(IiaPartner receivingPartner) {
        this.receivingPartner = receivingPartner;
    }

    public CooperationConditionMobilityType getMobilityType() {
        return mobilityType;
    }

    public void setMobilityType(CooperationConditionMobilityType mobilityType) {
        this.mobilityType = mobilityType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CooperationConditionMobilityNumberVariant getMobilityNumberVariant() {
        return mobilityNumberVariant;
    }

    public void setMobilityNumberVariant(CooperationConditionMobilityNumberVariant mobilityNumberVariant) {
        this.mobilityNumberVariant = mobilityNumberVariant;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEqfLevel() {
        return eqfLevel;
    }

    public void setEqfLevel(String eqfLevel) {
        this.eqfLevel = eqfLevel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final CooperationCondition other = (CooperationCondition) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
