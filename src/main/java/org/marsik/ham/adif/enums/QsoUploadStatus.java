package org.marsik.ham.adif.enums;

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
}
