package eu.erasmuswithoutpaper.omobility.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MobilityUpdateRequestType {
    APPROVE_COMPONENTS_STUDIED_DRAFT_V1, UPDATE_COMPONENTS_STUDIED_V1, UPDATE_STATUSES_V1;
    
    public static String[] names() {
        MobilityUpdateRequestType[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
