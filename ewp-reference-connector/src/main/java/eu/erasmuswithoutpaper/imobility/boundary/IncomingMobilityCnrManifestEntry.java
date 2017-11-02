package eu.erasmuswithoutpaper.imobility.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.imobilities.cnr.ImobilityCnr;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;

public class IncomingMobilityCnrManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        ImobilityCnr imobilityCnr = new ImobilityCnr();
        imobilityCnr.setVersion(EwpConstants.INCOMING_MOBILITY_CNR_VERSION);
        imobilityCnr.setUrl(baseUri + "imobilities/cnr");
        return imobilityCnr;
    }
}
