
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class MobilityId implements Serializable{
    
    private String mobilityId;
    private int mobilityRevision;
    
    public MobilityId(){
    }
    
    public MobilityId(String mobilityId, int mobilityRevision){
        this.mobilityId = mobilityId;
        this.mobilityRevision = mobilityRevision;
    }

    public String getMobilityId() {
        return mobilityId;
    }

    public void setMobilityId(String mobilityId) {
        this.mobilityId = mobilityId;
    }

    public int getMobilityRevision() {
        return mobilityRevision;
    }

    public void setMobilityRevision(int mobilityRevision) {
        this.mobilityRevision = mobilityRevision;
    }
    
}
