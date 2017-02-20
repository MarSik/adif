package org.marsik.ham.grid;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CoordinateWriterTest {
    @Test
    public void testNorthLatitude() throws Exception {
        assertThat(CoordinateWriter.latToDM(50.5))
                .isNotNull()
                .isEqualTo("N50 30.000");
    }

    @Test
    public void testEastLongitude() throws Exception {
        assertThat(CoordinateWriter.lonToDM(15.2))
                .isNotNull()
                .isEqualTo("E015 12.000");
    }

    @Test
    public void testParsingNorthLatitude() throws Exception {
        assertThat(CoordinateWriter.dmToLat("N50 30.000"))
                .isEqualTo(50.5);
    }

    @Test
    public void testParsingEastLongitude() throws Exception {
        assertThat(CoordinateWriter.dmToLon("E015 30.000"))
                .isEqualTo(15.5);
    }
}
