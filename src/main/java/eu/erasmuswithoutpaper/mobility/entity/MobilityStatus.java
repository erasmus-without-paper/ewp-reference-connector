
package eu.erasmuswithoutpaper.mobility.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MobilityStatus {
    NOMINATED, LIVE, REJECTED, CANCELLED, ACCEPTED, RECOGNIZED;
    
    public static String[] names() {
        MobilityStatus[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
