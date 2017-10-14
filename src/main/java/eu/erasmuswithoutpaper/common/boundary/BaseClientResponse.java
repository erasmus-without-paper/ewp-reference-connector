package eu.erasmuswithoutpaper.common.boundary;

import java.io.Serializable;
import java.util.List;

public class BaseClientResponse implements Serializable {
    private int statusCode;
    private String mediaType;
    private String errorMessage;
    private String rawResponse;
    private long duration;
    
    private List<String> headers;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }
    
    public void setRawResponse(String raw) {
        this.rawResponse = raw;
    }

    public long getDuration() {
        return this.duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public List<String> getHeaders() {
        return this.headers;
    }
    
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
