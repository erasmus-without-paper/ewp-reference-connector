package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("echo")
public class GuiEchoResource {

    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response echoHeis() {
        List<HeiEntry> echoHeis = registryClient.getEchoHeis();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(echoHeis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response echo(EchoRequest echoRequest) {
        String heiUrl = registryClient.getEchoHeiUrl(echoRequest.getHeiId());

        EchoResponse echoResponse = new EchoResponse();
        try {
            WebTarget target = restClient.client().target(heiUrl);
            Response response;
            Instant start = Instant.now();
            switch (echoRequest.getMethod()) {
                case POST:
                    Form form = new Form();
                    echoRequest.getEcho().stream().forEach(e -> form.param("echo", e));
                    response = target.request().post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    break;
                case PUT:
                    response = target.request().put(null);
                    break;
                default:
                    for (String echo : echoRequest.getEcho()) {
                        target = target.queryParam("echo", echo);
                    }
                    response = target.request().get();
                    break;
            }
            
            echoResponse.setDuration(ChronoUnit.MILLIS.between(start,Instant.now()));
            
            echoResponse.setStatusCode(response.getStatus());
            echoResponse.setMediaType(response.getMediaType().toString());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                response.bufferEntity();
                
                String raw = response.readEntity(String.class);
                echoResponse.setRawResponse(raw);
                eu.erasmuswithoutpaper.api.echo.Response echo = response.readEntity(eu.erasmuswithoutpaper.api.echo.Response.class);

                echoResponse.setEcho(new ArrayList<>(echo.getEcho()));
                echoResponse.setHeiId(new ArrayList<>(echo.getHeiId()));
            } else {
                if (response.hasEntity()) {
                    response.bufferEntity();
                    String raw = response.readEntity(String.class);
                    echoResponse.setRawResponse(raw);
                    try {
                        eu.erasmuswithoutpaper.api.architecture.ErrorResponse error = response.readEntity(eu.erasmuswithoutpaper.api.architecture.ErrorResponse.class);
                        echoResponse.setErrorMessage(error.getDeveloperMessage().getValue());
                    } catch (Exception e) {
                        echoResponse.setErrorMessage(raw);
                    }
                }
            }
        } catch (Exception e) {
            echoResponse.setErrorMessage(e.getMessage());
        }
 
        return javax.ws.rs.core.Response.ok(echoResponse).build();
    }
}
