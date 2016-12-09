package eu.erasmuswithoutpaper.error.control;

import eu.erasmuswithoutpaper.api.architecture.ErrorResponse;
import eu.erasmuswithoutpaper.api.architecture.MultilineString;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotSupportedExceptionMapper implements ExceptionMapper<NotSupportedException> {
    @Override
    public Response toResponse(NotSupportedException exception) 
    {
        ErrorResponse errorResponse = new ErrorResponse();
        MultilineString message = new MultilineString();
        message.setValue(exception.getMessage());
        errorResponse.setDeveloperMessage(message);
        
        return Response.status(exception.getResponse().getStatus()).type(MediaType.APPLICATION_XML).entity(errorResponse).build();  
    }
}
