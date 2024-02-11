package org.marsik.ham.grid;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateWriterTest {
    @Test
    public void testNorthLatitude() {
        assertThat(CoordinateWriter.latToDM(50.5))
                .isNotNull()
                .isEqualTo("N050 30.000");
    }

    @Test
    public void testEastLongitude() {
        assertThat(CoordinateWriter.lonToDM(15.2))
                .isNotNull()
                .isEqualTo("E015 12.000");
    }

    @Test
    public void testParsingNorthLatitude() {
        assertThat(CoordinateWriter.dmToLat("N050 30.000"))
                .isEqualTo(50.5);
    }

    @Test
    public void testParsingEastLongitude() {
        assertThat(CoordinateWriter.dmToLon("E015 30.000"))
                .isEqualTo(15.5);
    }

}
