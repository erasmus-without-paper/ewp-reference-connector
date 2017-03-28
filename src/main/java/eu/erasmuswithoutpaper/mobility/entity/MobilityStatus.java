
package eu.erasmuswithoutpaper.mobility.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MobilityStatus {
    CANCELLED("cancelled"), LIVE("live"), NOMINATION("nomination"), RECOGNIZED("recognized"), REJECTED("rejected");
    
    private final String value;

    MobilityStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
    
    public static String[] names() {
        MobilityStatus[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
