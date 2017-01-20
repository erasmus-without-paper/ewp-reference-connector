package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import java.io.Serializable;
import java.util.List;

public class EchoRequest implements Serializable {
    private String heiId;
    private List<String> echo;
    private HttpMethodEnum method;

    public String getHeiId() {
        return heiId;
    }

    public void setHeiId(String heiId) {
        this.heiId = heiId;
    }

    public List<String> getEcho() {
        return echo;
    }

    public void setEcho(List<String> echo) {
        this.echo = echo;
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }

    
}
