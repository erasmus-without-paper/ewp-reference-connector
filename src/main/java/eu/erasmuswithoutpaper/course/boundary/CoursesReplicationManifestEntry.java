package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.courses.replication.SimpleCourseReplication;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class CoursesReplicationManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        SimpleCourseReplication courseReplication = new SimpleCourseReplication();
        courseReplication.setVersion(EwpConstants.COURSE_REPLICATION_VERSION);
        courseReplication.setUrl(baseUri + "courses/replication");
        courseReplication.setAllowsAnonymousAccess(true);
        courseReplication.setSupportsModifiedSince(false);
        
        return courseReplication;
    }
}
