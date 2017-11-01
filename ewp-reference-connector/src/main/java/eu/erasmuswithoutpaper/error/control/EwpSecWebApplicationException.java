package eu.erasmuswithoutpaper.error.control;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class EwpSecWebApplicationException extends WebApplicationException {
    public static enum AuthMethod {
        TLSCERT, HTTPSIG
    }
    private AuthMethod authMethod;
    public EwpSecWebApplicationException (String message, Response.Status status) {
        this(message, status, AuthMethod.TLSCERT);
    }
    
    public EwpSecWebApplicationException (String message, Response.Status status, AuthMethod authMethod) {
        super(message, status);
        this.authMethod = authMethod;
    }
    
    public AuthMethod getAuthMethod() {
        return authMethod;
    }
}
