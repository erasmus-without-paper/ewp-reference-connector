package eu.erasmuswithoutpaper.omobility.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.omobilities.Omobilities;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import java.math.BigInteger;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class OutgoingMobilityManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        Omobilities mobilities = new Omobilities();
        mobilities.setVersion(EwpConstants.OUTGOING_MOBILITIES_VERSION);
        mobilities.setIndexUrl(baseUri + "omobilities/index");
        mobilities.setGetUrl(baseUri + "omobilities/get");
        mobilities.setMaxOmobilityIds(BigInteger.valueOf(globalProperties.getMaxMobilityIds()));
        mobilities.setUpdateUrl(baseUri + "omobilities/update");

        mobilities.setSendsNotifications(new Empty());
        Omobilities.SupportedUpdateTypes supportedUpdateTypes = new Omobilities.SupportedUpdateTypes();
        supportedUpdateTypes.setApproveComponentsStudiedDraftV1(new Empty());
        supportedUpdateTypes.setUpdateComponentsStudiedV1(new Empty());
        mobilities.setSupportedUpdateTypes(supportedUpdateTypes);
        return mobilities;
    }
}
