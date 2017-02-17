package org.marsik.ham.adif.enums;

public enum AntPath implements AdifEnumCode {
    GRAYLINE("G"),
    OTHER("O"),
    SHORT("S"),
    LONG("L");

    private final String code;

    AntPath(String code) {
        this.code = code;
    }

    public String adifCode() {
        return code;
    }
}
