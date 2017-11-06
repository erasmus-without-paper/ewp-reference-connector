package eu.erasmuswithoutpaper.imobility.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.imobilities.tors.cnr.ImobilityTorCnr;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import java.math.BigInteger;

public class IncomingMobilityTorsCnrManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        ImobilityTorCnr imobilityTorCnr = new ImobilityTorCnr();
        imobilityTorCnr.setVersion(EwpConstants.INCOMING_MOBILITY_TORS_CNR_VERSION);
        imobilityTorCnr.setMaxOmobilityIds(BigInteger.valueOf(globalProperties.getMaxMobilityIds()));
        imobilityTorCnr.setUrl(baseUri + "imobilities/tors/cnr");
        return imobilityTorCnr;
    }
}
