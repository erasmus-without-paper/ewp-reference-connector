
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Coordinator implements Serializable{
    
    @EmbeddedId
    private CoordinatorId coordinatorId;

    public CoordinatorId getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(CoordinatorId coordinatorId) {
        this.coordinatorId = coordinatorId;
    }
    
}
