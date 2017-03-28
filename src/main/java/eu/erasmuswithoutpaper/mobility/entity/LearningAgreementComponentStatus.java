
package eu.erasmuswithoutpaper.mobility.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum LearningAgreementComponentStatus {
    PLANNED, RECOMMENDED;
    
    public static String[] names() {
        LearningAgreementComponentStatus[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
