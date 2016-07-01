/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.echo.boundary;

import eu.erasmuswithoutpaper.api.echo.Response;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.*;
import org.junit.Test;

public class EchoResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(EchoResource.class);
    }

    @Test
    public void testEchoGet() {
        final Response echoResponse = target("echo")
                .queryParam("echo", "Test1")
                .queryParam("echo", "Test2")
                .request()
                .get(Response.class);

        assertNotNull(echoResponse);
        assertEquals(2, echoResponse.getEcho().size());
        assertTrue(echoResponse.getEcho().contains("Test1"));
        assertTrue(echoResponse.getEcho().contains("Test2"));
    }

    @Test
    public void testEchoPost() {
        MultivaluedMap<String, String> data = new MultivaluedHashMap<>();
        data.add("echo", "Test1");
        data.add("echo", "Test2");

        final Response echoResponse = target("echo")
                .request()
                .post(Entity.form(data), Response.class);

        assertNotNull(echoResponse);
        assertEquals(2, echoResponse.getEcho().size());
        assertTrue(echoResponse.getEcho().contains("Test1"));
        assertTrue(echoResponse.getEcho().contains("Test2"));
    }

}
