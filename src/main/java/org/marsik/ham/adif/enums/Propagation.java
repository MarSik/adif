package org.marsik.ham.adif.enums;

public enum Propagation implements AdifEnumCode {
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
}
