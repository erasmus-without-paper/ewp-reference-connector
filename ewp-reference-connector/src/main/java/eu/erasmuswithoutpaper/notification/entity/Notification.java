
package eu.erasmuswithoutpaper.notification.entity;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.johnzon.mapper.JohnzonConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = Notification.findAll, query = "SELECT n FROM Notification n")
})
public class Notification implements Serializable {
    private static final String PREFIX = "eu.erasmuswithoutpaper.notification.entity.Notification.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private NotificationTypes type;
    private String heiId;
    private String changedElementIds;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date notificationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NotificationTypes getType() {
        return type;
    }

    public void setType(NotificationTypes type) {
        this.type = type;
    }

    public String getHeiId() {
        return heiId;
    }

    public void setHeiId(String heiId) {
        this.heiId = heiId;
    }

    public String getChangedElementIds() {
        return changedElementIds;
    }

    public void setChangedElementIds(String changedElementIds) {
        this.changedElementIds = changedElementIds;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
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
        final Notification other = (Notification) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
