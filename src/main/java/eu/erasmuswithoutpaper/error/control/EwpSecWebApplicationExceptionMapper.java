package eu.erasmuswithoutpaper.error.control;

import eu.erasmuswithoutpaper.api.architecture.ErrorResponse;
import eu.erasmuswithoutpaper.api.architecture.MultilineString;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EwpSecWebApplicationExceptionMapper implements ExceptionMapper<EwpSecWebApplicationException> {
    @Override
    public Response toResponse(EwpSecWebApplicationException exception) 
    {
        ErrorResponse errorResponse = new ErrorResponse();
        MultilineString message = new MultilineString();
        message.setValue(exception.getMessage());
        errorResponse.setDeveloperMessage(message);
        ResponseBuilder responseBuilder = Response.status(exception.getResponse().getStatus()).type(MediaType.APPLICATION_XML).entity(errorResponse);
        if (exception.getAuthMethod() == EwpSecWebApplicationException.AuthMethod.HTTPSIG) {
            responseBuilder.header("X-EWP-Verification-Message", exception.getMessage());
        }
        return responseBuilder.build();  
    }
}
