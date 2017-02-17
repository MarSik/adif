package org.marsik.ham.adif.enums;

public enum Mode implements AdifEnumCode {
    AM,
    ATV,
    CHIP,
    CLO,
    CONTESTI,
    CW,
    DIGITALVOICE,
    DOMINO,
    DSTAR,
    FAX,
    FM,
    FSK441,
    HELL,
    ISCAT,
    JT4,
    JT6M,
    JT9,
    JT44,
    JT65,
    MFSK,
    MT63,
    OLIVIA,
    OPERA,
    PAC,
    PAX,
    PKT,
    PSK,
    PSK2K,
    Q15,
    ROS,
    RTTY,
    RTTYM,
    SSB,
    SSTV,
    THOR,
    THRB,
    TOR,
    V4,
    VOI,
    WINMOR,
    WSPR;

    @Override
    public String adifCode() {
        return name();
    }
}
