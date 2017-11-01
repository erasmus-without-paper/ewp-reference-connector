
package eu.erasmuswithoutpaper.iia.entity;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.johnzon.mapper.JohnzonConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = CooperationCondition.findAll, query = "SELECT c FROM CooperationCondition c"),
})
public class CooperationCondition implements Serializable{
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.CooperationCondition.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SENDING_PARTNER_ID")
    private IiaPartner sendingPartner;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "RECEIVING_PARTNER_ID")
    private IiaPartner receivingPartner;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "MOBILITY_TYPE_ID", referencedColumnName = "ID")
    private MobilityType mobilityType;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MobilityNumber mobilityNumber;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Duration duration;
    
    private byte eqfLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public MobilityType getMobilityType() {
        return mobilityType;
    }

    public void setMobilityType(MobilityType mobilityType) {
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

    public MobilityNumber getMobilityNumber() {
        return mobilityNumber;
    }

    public void setMobilityNumber(MobilityNumber mobilityNumber) {
        this.mobilityNumber = mobilityNumber;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public byte getEqfLevel() {
        return eqfLevel;
    }

    public void setEqfLevel(byte eqfLevel) {
        this.eqfLevel = eqfLevel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
