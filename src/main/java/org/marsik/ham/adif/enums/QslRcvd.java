package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    private final static Map<String, QslRcvd> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static QslRcvd findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
