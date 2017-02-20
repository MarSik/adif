package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum QsoUploadStatus implements AdifEnumCode {
    UPLOADED("Y"),
    DO_NOT_UPLOAD("N"),
    MODIFIED("M");

    private final String code;

    QsoUploadStatus(String code) {
        this.code = code;
    }

    @Override
    public String adifCode() {
        return code;
    }

    private final static Map<String, QsoUploadStatus> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static QsoUploadStatus findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
