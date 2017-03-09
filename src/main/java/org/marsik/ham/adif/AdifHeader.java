package org.marsik.ham.adif;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AdifHeader {
    String version = "3.0.5";
    String programId;
    String programVersion;
    ZonedDateTime timestamp;
}
