/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.discovery.boundary;

import eu.erasmuswithoutpaper.api.discovery.Manifest;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManifestResourceTest extends JerseyTest {

    private ManifestResource resource;

    @Override
    protected Application configure() {
        resource = new ManifestResource();
        resource.em = mock(EntityManager.class);
        return new ResourceConfig().register(resource);
    }
    
    @Test
    @Ignore
    public void testManifest() {
        mockQuery(new ArrayList<>());
        final Manifest manifest = target("manifest")
                .request()
                .get(Manifest.class);

        assertNotNull(manifest);
        assertEquals("This is a EWP reference connector instance.", manifest.getAdminNotes().getValue());
    }
    
    void mockQuery(List<Institution> results) {
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(this.resource.em.createNamedQuery(Matchers.anyString())).thenReturn(mockedQuery);
    }
}
