package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.api.echo.Response;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EchoResourceTest extends JerseyTest {

    private EchoResource resource;
    
    @Override
    protected Application configure() {
        resource = new EchoResource();
        resource.em = mock(EntityManager.class);
        return new ResourceConfig().register(resource);
    }

    @Test
    public void testEchoGet() {
        mockInstitutions();
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
        
        mockInstitutions();
                
        final Response echoResponse = target("echo")
                .request()
                .post(Entity.form(data), Response.class);

        assertNotNull(echoResponse);
        assertEquals(2, echoResponse.getEcho().size());
        assertTrue(echoResponse.getEcho().contains("Test1"));
        assertTrue(echoResponse.getEcho().contains("Test2"));
        assertTrue(echoResponse.getHeiId().contains("myInstId"));
    }

    void mockInstitutions() {
        List<Institution> institutions = new ArrayList<>();
        
        Institution institution = new Institution();
        institution.setInstitutionId("myInstId");
        
        institutions.add(institution);
        mockQuery(institutions);
    }
    void mockQuery(List<Institution> results) {
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(this.resource.em.createNamedQuery(Matchers.anyString())).thenReturn(mockedQuery);
    }
}
