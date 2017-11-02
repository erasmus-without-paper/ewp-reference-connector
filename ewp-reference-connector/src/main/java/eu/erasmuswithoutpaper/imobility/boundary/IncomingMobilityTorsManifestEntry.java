package eu.erasmuswithoutpaper.imobility.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.imobilities.tors.ImobilityTors;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import java.math.BigInteger;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class IncomingMobilityTorsManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        ImobilityTors mobilities = new ImobilityTors();
        mobilities.setVersion(EwpConstants.INCOMING_MOBILITIES_TORS_VERSION);
        mobilities.setGetUrl(baseUri + "imobilities/tors/get");
        mobilities.setIndexUrl(baseUri + "imobilities/tors/index");
        mobilities.setMaxOmobilityIds(BigInteger.valueOf(globalProperties.getMaxMobilityIds()));

        mobilities.setSendsNotifications(new Empty());
        return mobilities;
    }
}
