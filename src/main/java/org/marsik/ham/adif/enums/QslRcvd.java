package org.marsik.ham.adif.enums;

public enum QslRcvd implements AdifEnumCode {
    RECEIVED("Y"),
    NOT_RECEIVED("N"),
    REQUESTED("R"),
    IGNORE("I"),
    VERIFIED("V");

    private final String code;

    QslRcvd(String code) {
        this.code = code;
    }

    @Override
    public String adifCode() {
        return code;
    }
}
