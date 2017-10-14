package eu.erasmuswithoutpaper.mobility.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.mobilities.Omobilities;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import java.math.BigInteger;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class MobilityManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        Omobilities mobilities = new Omobilities();
        mobilities.setVersion(EwpConstants.MOBILITIES_VERSION);
        mobilities.setIndexUrl(baseUri + "mobilities/index");
        mobilities.setGetUrl(baseUri + "mobilities/get");
        mobilities.setMaxOmobilityIds(BigInteger.valueOf(globalProperties.getMaxMobilityIds()));
        mobilities.setUpdateUrl(baseUri + "mobilities/update");

        mobilities.setSendsNotifications(new Empty());
        mobilities.setSupportedUpdateTypes(new Omobilities.SupportedUpdateTypes());
        return mobilities;
    }
}
