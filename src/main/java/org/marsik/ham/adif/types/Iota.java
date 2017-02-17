package org.marsik.ham.adif.types;

import org.marsik.ham.adif.enums.AdifEnumCode;
import org.marsik.ham.adif.enums.Continent;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Iota implements AdifEnumCode {
    Continent continent;
    Integer island;

    @Override
    public String adifCode() {
        return continent + "-" + String.format("%03d", island);
    }
}
