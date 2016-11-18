package eu.erasmuswithoutpaper;

import eu.erasmuswithoutpaper.course.boundary.GuiAcademicTermResource;
import eu.erasmuswithoutpaper.internal.boundary.GuiHomeResource;
import eu.erasmuswithoutpaper.organization.boundary.GuiCoordinatorResource;
import eu.erasmuswithoutpaper.organization.boundary.GuiInstitutionResource;
import eu.erasmuswithoutpaper.organization.boundary.GuiPersonResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("gui")
public class GuiJAXRSConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(GuiHomeResource.class);
        resources.add(GuiInstitutionResource.class);
        resources.add(GuiPersonResource.class);
        resources.add(GuiCoordinatorResource.class);
        resources.add(GuiAcademicTermResource.class);
        return resources;
    }
}
