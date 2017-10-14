package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.ounits.OrganizationalUnits;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import java.math.BigInteger;
import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class OrganizationUnitsManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        OrganizationalUnits organizationalUnits = new OrganizationalUnits();
        organizationalUnits.setVersion(EwpConstants.ORGANIZATION_UNIT_VERSION);
        organizationalUnits.setUrl(baseUri + "ounits");
        organizationalUnits.setMaxOunitIds(BigInteger.valueOf(globalProperties.getMaxOunitsIds()));
        organizationalUnits.setMaxOunitCodes(BigInteger.valueOf(globalProperties.getMaxOunitsIds()));
        return organizationalUnits;
    }
}
