package org.marsik.ham.adif.types;

import org.marsik.ham.adif.enums.AdifEnumCode;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Sota implements AdifEnumCode {
    String ituPrefix;
    String subdivision;
    Integer reference;

    @Override
    public String adifCode() {
        return ituPrefix + (subdivision != null ? "/" + subdivision : "") + "-" + String.format("%03d", reference);
    }
}
