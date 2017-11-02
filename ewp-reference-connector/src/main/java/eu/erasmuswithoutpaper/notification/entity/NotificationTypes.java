package eu.erasmuswithoutpaper.notification.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum NotificationTypes {
    IIA, OMOBILITY, IMOBILITY, TOR;
    
    public static String[] names() {
        NotificationTypes[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
