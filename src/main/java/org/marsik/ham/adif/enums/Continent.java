package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum Continent implements AdifEnumCode {
    NORTH_AMERICA("NA"),
    SOUTH_AMERICA("SA"),
    EUROPE("EU"),
    AFRICA("AF"),
    OCEANA("OC"),
    ASIA("AS"),
    ANTARCTICA("AN");

    private final String code;

    Continent(String code) {
        this.code = code;
    }

    @Override
    public String adifCode() {
        return code;
    }


    private final static Map<String, Continent> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static Continent findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
