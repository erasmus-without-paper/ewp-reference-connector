package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.api.ounits.OunitsResponse;
import eu.erasmuswithoutpaper.common.boundary.BaseClientResponse;
import java.io.Serializable;
import java.util.List;

public class OrganizationUnitResponse extends BaseClientResponse implements Serializable {
    private List<OunitsResponse.Ounit> organizationUnits;

    public List<OunitsResponse.Ounit> getOrganizationUnits() {
        return this.organizationUnits;
    }
    
    public void setOrganizationUnits(List<OunitsResponse.Ounit> ounits) {
        this.organizationUnits = ounits;
    }
}
