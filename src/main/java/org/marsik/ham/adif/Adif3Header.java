package org.marsik.ham.adif;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Adif3Header {
    String version = "3.0.4";
}
