package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.iias.Iias;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import java.math.BigInteger;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class IiaManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        Iias iias = new Iias();
        iias.setVersion(EwpConstants.IIAS_VERSION);
        iias.setIndexUrl(baseUri + "iias/index");
        iias.setGetUrl(baseUri + "iias/get");
        iias.setMaxIiaIds(BigInteger.valueOf(globalProperties.getMaxIiaIds()));
        iias.setMaxIiaCodes(BigInteger.valueOf(globalProperties.getMaxIiaIds()));
        //iias.setSendsNotifications();
        return iias;
    }
}
