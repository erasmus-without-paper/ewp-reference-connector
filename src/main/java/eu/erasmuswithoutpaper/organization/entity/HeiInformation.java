
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class HeiInformation implements Serializable{

    @EmbeddedId
    private HeiInformationId heiInformationId;
    private String content;

    public HeiInformationId getHeiInformationId() {
        return heiInformationId;
    }

    public void setHeiInformationId(HeiInformationId heiInformationId) {
        this.heiInformationId = heiInformationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
