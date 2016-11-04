
package eu.erasmuswithoutpaper.mobility.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ResultDistribution implements Serializable{

    @EmbeddedId
    private ResultDistributionId resultDistributionId;
    private int count;

    public ResultDistributionId getResultDistributionId() {
        return resultDistributionId;
    }

    public void setResultDistributionId(ResultDistributionId resultDistributionId) {
        this.resultDistributionId = resultDistributionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
