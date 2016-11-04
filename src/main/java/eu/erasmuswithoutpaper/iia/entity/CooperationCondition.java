
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class CooperationCondition implements Serializable{
    
    @EmbeddedId
    private CooperationConditionId cooperationConditionId;
    private CooperationConditionMobilityNumberVariant mobilityNumberVariant;
    private String duration;
    private String eqfLevel;

    public CooperationConditionId getCooperationConditionId() {
        return cooperationConditionId;
    }

    public void setCooperationConditionId(CooperationConditionId cooperationConditionId) {
        this.cooperationConditionId = cooperationConditionId;
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
    
}
