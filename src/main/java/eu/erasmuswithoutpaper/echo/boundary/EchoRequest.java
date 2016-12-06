package eu.erasmuswithoutpaper.echo.boundary;

import java.util.List;

public class EchoRequest {
    private String heiId;
    private List<String> echo;

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

}
