
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = MobilityType.findAll, query = "SELECT m FROM MobilityType m"),
    @NamedQuery(name = MobilityType.findByGroupAndCategory, query = "SELECT m FROM MobilityType m WHERE m.mobilityGroup = :mobilityGroup AND m.mobilityCategory = :mobilityCategory")
})
public class MobilityType implements Serializable {
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.MobilityType.";
    public static final String findAll = PREFIX + "all";
    public static final String findByGroupAndCategory = PREFIX + "byGroupAndCategory";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String mobilityGroup;
    private String mobilityCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobilityGroup() {
        return mobilityGroup;
    }

    public void setMobilityGroup(String mobilityGroup) {
        this.mobilityGroup = mobilityGroup;
    }

    public String getMobilityCategory() {
        return mobilityCategory;
    }

    public void setMobilityCategory(String mobilityCategory) {
        this.mobilityCategory = mobilityCategory;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final MobilityType other = (MobilityType) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
