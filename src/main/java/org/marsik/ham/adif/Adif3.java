package org.marsik.ham.adif;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Adif3 {
    AdifHeader header;
    List<Adif3Record> records = new ArrayList<>();
}
