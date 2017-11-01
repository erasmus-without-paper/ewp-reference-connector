package eu.erasmuswithoutpaper.imobility.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.imobilities.Imobilities;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import java.math.BigInteger;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class IncomingMobilityManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        Imobilities mobilities = new Imobilities();
        mobilities.setVersion(EwpConstants.INCOMING_MOBILITIES_VERSION);
        mobilities.setGetUrl(baseUri + "imobilities/get");
        mobilities.setMaxOmobilityIds(BigInteger.valueOf(globalProperties.getMaxMobilityIds()));

        mobilities.setSendsNotifications(new Empty());
        return mobilities;
    }
}
