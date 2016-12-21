package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.api.echo.Response;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EchoResourceTest extends JerseyTest {

    private EchoResource resource;
    private HttpServletRequest request;
    
    @Override
    protected Application configure() {
        request = mock(HttpServletRequest.class);
        GlobalProperties properties = mock(GlobalProperties.class);
        RegistryClient registryClient = mock(RegistryClient.class);
        resource = new EchoResource();
        return new ResourceConfig()
                .register(resource)
                .register(new AbstractBinder() {
                            @Override
                            protected void configure() {
                                bind(request).to(HttpServletRequest.class);
                                bind(properties).to(GlobalProperties.class);
                                bind(registryClient).to(RegistryClient.class);
                            }
                        });
    }

    @Test
    public void testEchoGet() {
        mockRegistryClient();
        final Response echoResponse = target("echo")
                .queryParam("echo", "Test1")
                .queryParam("echo", "Test2")
                .request()
                .get(Response.class);

        assertNotNull(echoResponse);
        assertEquals(2, echoResponse.getEcho().size());
        assertTrue(echoResponse.getEcho().contains("Test1"));
        assertTrue(echoResponse.getEcho().contains("Test2"));
        assertTrue(echoResponse.getHeiId().contains("myInstId"));
    }

    @Test
    public void testEchoPost() {
        MultivaluedMap<String, String> data = new MultivaluedHashMap<>();
        data.add("echo", "Test1");
        data.add("echo", "Test2");
        
        mockRegistryClient();
                
        final Response echoResponse = target("echo")
                .request()
                .post(Entity.form(data), Response.class);

        assertNotNull(echoResponse);
        assertEquals(2, echoResponse.getEcho().size());
        assertTrue(echoResponse.getEcho().contains("Test1"));
        assertTrue(echoResponse.getEcho().contains("Test2"));
        assertTrue(echoResponse.getHeiId().contains("myInstId"));
    }

    void mockRegistryClient() {
        when(this.request.getAttribute("javax.servlet.request.X509Certificate")).thenReturn(new X509Certificate[]{});
        
        X509Certificate mockedX509Certificate = mock(X509Certificate.class);
        when(this.resource.registryClient.getCertificateKnownInEwpNetwork(Matchers.any(X509Certificate[].class))).thenReturn(mockedX509Certificate);
        
        List<String> heiIds = new ArrayList<>();
        heiIds.add("myInstId");
        when(this.resource.registryClient.getHeisCoveredByCertificate(mockedX509Certificate)).thenReturn(heiIds);
    }
}
