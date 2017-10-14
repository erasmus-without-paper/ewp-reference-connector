package eu.erasmuswithoutpaper.common.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;

public interface ManifestEntryStrategy {
    ManifestApiEntryBase getManifestEntry(String baseUri);
}
