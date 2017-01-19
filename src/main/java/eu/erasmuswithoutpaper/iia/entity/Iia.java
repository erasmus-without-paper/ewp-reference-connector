
package eu.erasmuswithoutpaper.iia.entity;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.johnzon.mapper.JohnzonConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = Iia.findAll, query = "SELECT i FROM Iia i"),
    @NamedQuery(name = Iia.findByIiaId, query = "SELECT i FROM Iia i WHERE i.iiaId = :iiaId")
})
public class Iia implements Serializable{
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.Iia.";
    public static final String findAll = PREFIX + "all";
    public static final String findByIiaId = PREFIX + "byIiaId";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String iiaId;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date modifyDate;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name = "iia_id")
    List<CooperationCondition> cooperationConditions;
    
    public Iia(){
    }
    
    public Iia(String iiaId){
        this.iiaId = iiaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIiaId() {
        return iiaId;
    }

    public void setIiaId(String iiaId) {
        this.iiaId = iiaId;
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

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<CooperationCondition> getCooperationConditions() {
        return cooperationConditions;
    }

    public void setCooperationConditions(List<CooperationCondition> cooperationConditions) {
        this.cooperationConditions = cooperationConditions;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Iia other = (Iia) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
