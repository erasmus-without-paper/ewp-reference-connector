package eu.erasmuswithoutpaper.omobility.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.omobilities.cnr.OmobilityCnr;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import java.math.BigInteger;

public class OutgoingMobilityCnrManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        OmobilityCnr omobilityCnr = new OmobilityCnr();
        omobilityCnr.setVersion(EwpConstants.OUTGOING_MOBILITY_CNR_VERSION);
        omobilityCnr.setMaxOmobilityIds(BigInteger.valueOf(globalProperties.getMaxMobilityIds()));
        omobilityCnr.setUrl(baseUri + "omobilities/cnr");
        return omobilityCnr;
    }
}
