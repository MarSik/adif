package org.marsik.ham.adif.enums;

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
}
