
package eu.erasmuswithoutpaper.organization.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ContactRoles {
    ADMISSION, COURSE, HOUSING, INSURANCE, VISAS;
    
    public static String[] names() {
        ContactRoles[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
