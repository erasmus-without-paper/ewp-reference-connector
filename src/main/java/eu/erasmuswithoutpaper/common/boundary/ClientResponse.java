package eu.erasmuswithoutpaper.common.boundary;

import java.io.Serializable;

public class ClientResponse extends BaseClientResponse implements Serializable {
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
