package org.marsik.ham.dxcc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DxccList {
    List<Dxcc> list = new ArrayList<>();
    int count;
    Map<Integer, String> notes = new HashMap<>();
}
