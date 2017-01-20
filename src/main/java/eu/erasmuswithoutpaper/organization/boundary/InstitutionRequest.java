package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import java.io.Serializable;
import java.util.List;

public class InstitutionRequest implements Serializable {
    private String url;
    private List<String> heiIds;
    private HttpMethodEnum method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getHeiIds() {
        return heiIds;
    }

    public void setHeiIds(List<String> heiIds) {
        this.heiIds = heiIds;
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }
}

