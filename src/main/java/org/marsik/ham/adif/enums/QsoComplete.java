package org.marsik.ham.adif.enums;

public enum QsoComplete implements AdifEnumCode {
    YES("Y"),
    NO("N"),
    NOT_HEARD("NIL"),
    UNCERTAIN("?");

    private final String code;

    QsoComplete(String code) {
        this.code = code;
    }

    @Override
    public String adifCode() {
        return code;
    }
}
