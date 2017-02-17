package org.marsik.ham.adif.enums;

public enum  QslVia implements AdifEnumCode {
    BUREAU("B"),
    DIRECT("D"),
    ELECTRONIC("E"),
    MANAGER("M");

    private final String code;

    QslVia(String code) {
        this.code = code;
    }

    @Override
    public String adifCode() {
        return code;
    }
}
