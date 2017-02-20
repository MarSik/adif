package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum Band implements AdifEnumCode {
    BAND_2190m("2190m", .136, .137),
    BAND_630m("630m", .472, .479),
    BAND_560m("560m", .501, .504),
    BAND_160m("160m", 1.8, 2.0),
    BAND_80m("80m", 3.5, 4.0),
    BAND_60m("60m", 5.102, 5.4065),
    BAND_40m("40m", 7.0, 7.3),
    BAND_30m("30m", 10.0, 10.15),
    BAND_20m("20m", 14.0, 14.35),
    BAND_17m("17m", 18.068, 18.168),
    BAND_15m("15m", 21.0, 21.45),
    BAND_12m("12m", 24.890, 24.99),
    BAND_10m("10m", 28.0, 29.7),
    BAND_6m("6m", 50, 54),
    BAND_4m("4m", 70, 71),
    BAND_2m("2m", 144, 148),
    BAND_1_25m("1.25m", 222, 225),
    BAND_70cm("70cm", 420, 450),
    BAND_33cm("33cm", 902, 928),
    BAND_23cm("23cm", 1240, 1300),
    BAND_13cm("13cm", 2300, 2450),
    BAND_9cm("9cm", 3300, 3500),
    BAND_6cm("6cm", 5650, 5925),
    BAND_3cm("3cm", 10000, 10500),
    BAND_1_25cm("1.25cm", 24000, 24250),
    BAND_6mm("6mm", 47000, 47200),
    BAND_4mm("4mm", 75500, 81000),
    BAND_2_5mm("2.5mm", 119980, 120020),
    BAND_2mm("2mm", 142000, 149000),
    BAND_1mm("1mm", 241000, 250000);

    private final String code;
    private final double lowerFrequency;
    private final double upperFrequency;

    Band(String code, double lowerFrequency, double upperFrequency) {
        this.code = code;
        this.lowerFrequency = lowerFrequency;
        this.upperFrequency = upperFrequency;
    }

    public String adifCode() {
        return code;
    }

    public double getLowerFrequency() {
        return lowerFrequency;
    }

    public double getUpperFrequency() {
        return upperFrequency;
    }

    private final static Map<String, Band> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static Band findByCode(String code) {
        return reverse.get(code.toLowerCase());
    }
}
