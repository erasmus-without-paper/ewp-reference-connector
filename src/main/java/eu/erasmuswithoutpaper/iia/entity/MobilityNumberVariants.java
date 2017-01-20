
package eu.erasmuswithoutpaper.iia.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MobilityNumberVariants {
    AVERAGE, TOTAL;
    
    public static String[] names() {
        MobilityNumberVariants[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
