package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    private final static Map<String, QsoComplete> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static QsoComplete findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
