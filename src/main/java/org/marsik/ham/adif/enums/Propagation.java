package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum Propagation implements AdifEnumCode {
    AIRCRAFT_SCATTER("AS"),
    AURORA("AUR"),
    AURORA_E("AUE"),
    BACK_SCATTER("BS"),
    ECHOLINK("ECH"),
    EARTH_MOON_EARTH("EME"),
    SPORADIC_E("ES"),
    FIELD_ALIGNED_IRREGULARITIES("FAI"),
    F2_REFLECTION("F2"),
    INTERNET("INTERNET"),
    IONOSCATTER("ION"),
    IRLP("IRL"),
    METEOR_SCATTER("MS"),
    REPEATER("RPT"),
    RAIN_SCATTER("RS"),
    SATELLITE("SAT"),
    TRANSEQUATORIAL("TEP"),
    TROPOSPHERIC_DUCTING("TR");

    private final String code;

    Propagation(String code) {
        this.code = code;
    }

    @Override
    public String adifCode() {
        return code;
    }

    private final static Map<String, Propagation> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static Propagation findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
