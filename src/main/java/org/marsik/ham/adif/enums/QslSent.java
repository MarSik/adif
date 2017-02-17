package org.marsik.ham.adif.enums;

public enum QslSent implements AdifEnumCode {
    SENT("Y"),
    DO_NOT_SEND("N"),
    REQUESTED("R"),
    QUEUED("Q"),
    IGNORE("I");

    private final String code;

    QslSent(String code) {
        this.code = code;
    }

    @Override
    public String adifCode() {
        return code;
    }
}
