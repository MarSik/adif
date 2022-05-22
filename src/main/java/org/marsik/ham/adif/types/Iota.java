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
        return continent.adifCode() + "-" + String.format("%03d", island);
    }

    public static Iota findByCode(String code) {
        String[] pieces = code.split("-");
        return new Iota(Continent.findByCode(pieces[0]), Integer.parseInt(pieces[1]));
    }
}
