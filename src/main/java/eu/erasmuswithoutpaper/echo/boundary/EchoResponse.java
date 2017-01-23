package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.common.boundary.BaseClientResponse;
import java.io.Serializable;
import java.util.List;

public class EchoResponse extends BaseClientResponse implements Serializable {
    private List<String> heiId;
    private List<String> echo;

    public List<String> getHeiId() {
        return heiId;
    }

    public void setHeiId(List<String> heiId) {
        this.heiId = heiId;
    }

    public List<String> getEcho() {
        return echo;
    }

    public void setEcho(List<String> echo) {
        this.echo = echo;
    }
}
