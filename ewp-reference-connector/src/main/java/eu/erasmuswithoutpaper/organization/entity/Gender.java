
package eu.erasmuswithoutpaper.organization.entity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Gender {
    NOT_KNOWN(BigInteger.ZERO), MALE(BigInteger.ONE), FEMALE(BigInteger.valueOf(2)), NOT_APPLICABLE(BigInteger.valueOf(9));
    
    private final BigInteger value;

    Gender(BigInteger v) {
        value = v;
    }

    public BigInteger value() {
        return value;
    }
    
    public static String[] names() {
        Gender[] genders = values();
        return Arrays.stream(genders).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[genders.length]);
    }
}
