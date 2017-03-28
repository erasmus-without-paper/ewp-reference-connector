package eu.erasmuswithoutpaper.error.control;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class EwpWebApplicationException extends WebApplicationException {

    public EwpWebApplicationException (String message, Response.Status status) {
        super(message, status);
    }
}
