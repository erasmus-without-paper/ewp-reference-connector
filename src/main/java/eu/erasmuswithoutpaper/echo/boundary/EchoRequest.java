package eu.erasmuswithoutpaper.echo.boundary;

import java.io.Serializable;
import java.util.List;

public class EchoRequest implements Serializable {
    public static enum Method {
        GET, POST, PUT
    }
    private String heiId;
    private List<String> echo;
    private Method method;

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

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    
}
