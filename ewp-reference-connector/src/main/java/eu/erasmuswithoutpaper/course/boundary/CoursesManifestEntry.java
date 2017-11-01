package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.courses.Courses;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import java.math.BigInteger;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class CoursesManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        Courses courses = new Courses();
        courses.setVersion(EwpConstants.COURSES_VERSION);
        courses.setUrl(baseUri + "courses");
        courses.setMaxLosIds(BigInteger.valueOf(globalProperties.getMaxLosIds()));
        courses.setMaxLosCodes(BigInteger.valueOf(globalProperties.getMaxLosIds()));
        return courses;
    }
}
