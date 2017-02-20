package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    private final static Map<String, AntPath> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static AntPath findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
