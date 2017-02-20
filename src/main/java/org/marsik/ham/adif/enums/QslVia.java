package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    private final static Map<String, QslVia> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static QslVia findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
