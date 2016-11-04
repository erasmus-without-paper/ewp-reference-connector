/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.manifest.boundary;

import eu.erasmuswithoutpaper.discovery.boundary.ManifestResource;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hali0031
 */
public class ManifestResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(ManifestResource.class);
    }

    @Test
    public void testManifest() {
        final String manifest = target("manifest")
                .request()
                .get(String.class);

        assertNotNull(manifest);
        assertTrue(manifest.contains("<apis-implemented>"));
    }
}
