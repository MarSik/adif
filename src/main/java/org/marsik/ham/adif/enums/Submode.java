package org.marsik.ham.adif.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum Submode implements AdifEnumCode {
    AMTORFEC("AMTORFEC", Mode.TOR),
    ASCI("ASCI", Mode.RTTY),
    CHIP64("CHIP64", Mode.CHIP),
    CHIP128("CHIP128", Mode.CHIP),
    DOMINOEX("DOMINOEX", Mode.DOMINO),
    DOMINOF("DOMINOF", Mode.DOMINO),
    FMHELL("FMHELL", Mode.HELL),
    FSK31("FSK31", Mode.PSK),
    FSKHELL("FSKHELL", Mode.HELL),
    FSQCALL("FSQCALL", Mode.MFSK),
    FT4("FT4", Mode.MFSK),
    GTOR("GTOR", Mode.TOR),
    HELL80("HELL80", Mode.HELL),
    HFSK("HFSK", Mode.HELL),
    ISCAT_A("ISCAT-A", Mode.ISCAT),
    ISCAT_B("ISCAT-B", Mode.ISCAT),
    JS8("JS8", MODE.MFSK),
    JT4A("JT4A", Mode.JT4),
    JT4B("JT4B", Mode.JT4),
    JT4C("JT4C", Mode.JT4),
    JT4D("JT4D", Mode.JT4),
    JT4E("JT4E", Mode.JT4),
    JT4F("JT4F", Mode.JT4),
    JT4G("JT4G", Mode.JT4),
    JT9_1("JT9-1", Mode.JT9),
    JT9_2("JT9-2", Mode.JT9),
    JT9_5("JT9-5", Mode.JT9),
    JT9_10("JT9-10", Mode.JT9),
    JT9_30("JT9-30", Mode.JT9),
    JT9A("JT9A", Mode.JT9),
    JT9B("JT9B", Mode.JT9),
    JT9C("JT9C", Mode.JT9),
    JT9D("JT9D", Mode.JT9),
    JT9E("JT9E", Mode.JT9),
    JT9E_FAST("JT9E FAST", Mode.JT9),
    JT9F("JT9F", Mode.JT9),
    JT9F_FAST("JT9F FAST", Mode.JT9),
    JT9G("JT9G", Mode.JT9),
    JT9G_FAST("JT9G FAST", Mode.JT9),
    JT9H("JT9H", Mode.JT9),
    JT9H_FAST("JT9H FAST", Mode.JT9),
    JT65A("JT65A", Mode.JT65),
    JT65B("JT65B", Mode.JT65),
    JT65B2("JT65B2", Mode.JT65),
    JT65C("JT65C", Mode.JT65),
    JT65C2("JT65C2", Mode.JT65),
    LSB("LSB", Mode.SSB),
    MFSK4("MFSK4", Mode.MFSK),
    MFSK8("MFSK8", Mode.MFSK),
    MFSK11("MFSK11", Mode.MFSK),
    MFSK16("MFSK16", Mode.MFSK),
    MFSK22("MFSK22", Mode.MFSK),
    MFSK31("MFSK31", Mode.MFSK),
    MFSK32("MFSK32", Mode.MFSK),
    MFSK64("MFSK64", Mode.MFSK),
    MFSK128("MFSK128", Mode.MFSK),
    OLIVIA_4_125("OLIVIA 4/125", Mode.OLIVIA),
    OLIVIA_4_250("OLIVIA 4/250", Mode.OLIVIA),
    OLIVIA_8_250("OLIVIA 8/250", Mode.OLIVIA),
    OLIVIA_8_500("OLIVIA 8/500", Mode.OLIVIA),
    OLIVIA_16_500("OLIVIA 16/500", Mode.OLIVIA),
    OLIVIA_16_1000("OLIVIA 16/1000", Mode.OLIVIA),
    OLIVIA_32_1000("OLIVIA 32/1000", Mode.OLIVIA),
    OPERA_BEACON("OPERA-BEACON", Mode.OPERA),
    OPERA_QSO("OPERA-QSO", Mode.OPERA),
    PAC2("PAC2", Mode.PAC),
    PAC3("PAC3", Mode.PAC),
    PAC4("PAC4", Mode.PAC),
    PAX2("PAX2", Mode.PAX),
    PCW("PCW", Mode.CW),
    PSK10("PSK10", Mode.PSK),
    PSK31("PSK31", Mode.PSK),
    PSK63("PSK63", Mode.PSK),
    PSK63F("PSK63F", Mode.PSK),
    PSK125("PSK125", Mode.PSK),
    PSK250("PSK250", Mode.PSK),
    PSK500("PSK500", Mode.PSK),
    PSK1000("PSK1000", Mode.PSK),
    PSKAM10("PSKAM10", Mode.PSK),
    PSKAM31("PSKAM31", Mode.PSK),
    PSKAM50("PSKAM50", Mode.PSK),
    PSKFEC31("PSKFEC31", Mode.PSK),
    PSKHELL("PSKHELL", Mode.HELL),
    QPSK31("QPSK31", Mode.PSK),
    QPSK63("QPSK63", Mode.PSK),
    QPSK125("QPSK125", Mode.PSK),
    QPSK250("QPSK250", Mode.PSK),
    QPSK500("QPSK500", Mode.PSK),
    ROS_EME("ROS-EME", Mode.ROS),
    ROS_HF("ROS-HF", Mode.ROS),
    ROS_MF("ROS-MF", Mode.ROS),
    THRBX("THRBX", Mode.THRB),
    USB("USB", Mode.SSB);

    private final String code;
    private final Mode mode;

    Submode(String code, Mode mode) {
        this.code = code;
        this.mode = mode;
    }

    @Override
    public String adifCode() {
        return code;
    }

    public Mode getMode() {
        return mode;
    }

    private final static Map<String, Submode> reverse = new HashMap<>();

    static {
        Stream.of(values()).forEach(v -> reverse.put(v.adifCode(), v));
    }

    public static Submode findByCode(String code) {
        return reverse.get(code.toUpperCase());
    }
}
