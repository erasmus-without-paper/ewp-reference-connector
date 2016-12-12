package eu.erasmuswithoutpaper.discovery.boundary;

import eu.erasmuswithoutpaper.api.discovery.Manifest;
import eu.erasmuswithoutpaper.internal.control.EwpKeyStore;
import eu.erasmuswithoutpaper.internal.control.GlobalProperties;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Application;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
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
    private EntityManager em;
    private EwpKeyStore ewpKeyStore;
    private GlobalProperties properties;

    @Override
    protected Application configure() {
        resource = new ManifestResource();
        properties = mock(GlobalProperties.class);
        em = mock(EntityManager.class);
        ewpKeyStore = mock(EwpKeyStore.class);
        resource.em = em;
        return new ResourceConfig()
                .register(resource)
                .register(new AbstractBinder() {
                            @Override
                            protected void configure() {
                                bind(properties).to(GlobalProperties.class);
                                bind(ewpKeyStore).to(EwpKeyStore.class);
                            }
                        });
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
        when(ewpKeyStore.getCertificate()).thenReturn("the certificate");
        
        when(properties.getBaseUri()).thenReturn("https://localhost");
        
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(em.createNamedQuery(Matchers.anyString())).thenReturn(mockedQuery);
    }
}
