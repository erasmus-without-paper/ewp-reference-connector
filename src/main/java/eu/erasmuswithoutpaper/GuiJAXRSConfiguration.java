package eu.erasmuswithoutpaper;

import eu.erasmuswithoutpaper.organization.boundary.GuiInstitutionResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("gui")
public class GuiJAXRSConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        //resources.add(GuiIiaResource.class);
        resources.add(GuiInstitutionResource.class);
        return resources;
    }
}
