package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    private final static Map<String, QslSent> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static QslSent findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
