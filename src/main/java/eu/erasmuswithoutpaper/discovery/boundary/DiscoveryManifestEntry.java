package eu.erasmuswithoutpaper.discovery.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.discovery.Discovery;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class DiscoveryManifestEntry implements ManifestEntryStrategy {
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        Discovery discovery = new Discovery();
        discovery.setVersion(EwpConstants.DISCOVERY_VERSION);
        discovery.setUrl(baseUri + "manifest");
        return discovery;
    }
}
