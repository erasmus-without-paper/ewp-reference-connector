package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.iias.cnr.IiaCnr;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;

public class IiaCnrManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        IiaCnr iiaCnr = new IiaCnr();
        iiaCnr.setVersion(EwpConstants.IIA_CNR_VERSION);
        iiaCnr.setUrl(baseUri + "iias/cnr");
        return iiaCnr;
    }
}
