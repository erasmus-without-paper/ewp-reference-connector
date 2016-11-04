package eu.erasmuswithoutpaper;

import eu.erasmuswithoutpaper.discovery.boundary.ManifestResource;
import eu.erasmuswithoutpaper.echo.boundary.EchoResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class RestJAXRSConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(EchoResource.class);
        resources.add(ManifestResource.class);
        return resources;
    }
}
