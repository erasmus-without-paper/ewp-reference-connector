package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import java.io.Serializable;
import java.util.List;

public class OrganizationUnitRequest implements Serializable {
    private String url;
    private String heiId;
    private List<String> organizationUnitIds;
    private HttpMethodEnum method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }

    public String getHeiId() {
        return heiId;
    }

    public void setHeiId(String heiId) {
        this.heiId = heiId;
    }

    public List<String> getOrganizationUnitIds() {
        return organizationUnitIds;
    }

    public void setOrganizationUnitIds(List<String> organizationUnitIds) {
        this.organizationUnitIds = organizationUnitIds;
    }
    
    
}

