package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse;
import eu.erasmuswithoutpaper.common.boundary.BaseClientResponse;
import java.io.Serializable;
import java.util.List;

public class InstitutionResponse extends BaseClientResponse implements Serializable {
    private List<InstitutionsResponse.Hei> heiList;

    public List<InstitutionsResponse.Hei> getHeis() {
        return this.heiList;
    }
    
    public void setHeis(List<InstitutionsResponse.Hei> hei) {
        this.heiList = hei;
    }
}
