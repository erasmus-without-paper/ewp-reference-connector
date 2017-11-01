
package eu.erasmuswithoutpaper.iia.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum DurationUnitVariants {
    HOURS, DAYS, WEEKS, MONTHS, YEARS;

    public static String[] names() {
        DurationUnitVariants[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
