package org.marsik.ham.adif;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.Test;

public class AdiWriterTest {
    @Test
    public void testBool() throws Exception {
        AdiWriter writer = new AdiWriter();
        writer.append("TRUE", true);
        writer.append("FALSE", false);

        assertThat(writer.toString())
                .isEqualTo("<TRUE:1>Y<FALSE:1>N");
    }

    @Test
    public void testSimpleQso() throws Exception {
        AdiWriter writer = new AdiWriter();

        Adif3Record record = new Adif3Record();
        record.setCall("OK7MS/p");
        writer.append(record);

        assertThat(writer.toString())
                .isEqualTo("<CALL:7>OK7MS/p<EOR>");
    }

    @Test
    public void testSimpleQsoWithTime() throws Exception {
        AdiWriter writer = new AdiWriter();

        Adif3Record record = new Adif3Record();
        record.setCall("OK7MS/p");
        record.setTimeOn(LocalTime.of(15, 30));
        writer.append(record);

        assertThat(writer.toString())
                .isEqualTo("<CALL:7>OK7MS/p<TIME_ON:6>153000<EOR>");
    }
}
