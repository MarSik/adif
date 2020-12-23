package org.marsik.ham.adif;

import static org.assertj.core.api.Assertions.assertThat;
import static org.marsik.ham.adif.enums.Band.*;
import static org.marsik.ham.adif.enums.Mode.*;
import static org.marsik.ham.adif.enums.Submode.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

public class AdiReaderTest {
    @Test
    public void testReadField() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput("   <test:3>abcde");
        AdiReader.Field f = reader.readField(inputReader);

        assertThat(f.getName())
                .isEqualTo("test");

        assertThat(f.getValue())
                .isEqualTo("abc");
    }

   @Test
    public void testReadFieldLengthTooLarge() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput("   <test:6>abcde");
        AdiReader.Field f = reader.readField(inputReader);

        assertThat(f.getName())
                .isEqualTo("test");

        assertThat(f.getValue())
                .isEqualTo("abcde\u0000");
    }

    @Test
    public void testReadRecord() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput("   <test:3>abcde<lala:0>asd<lili:2>ab<eOr>");
        Map<String,String> fields = reader.readRecord(inputReader);

        assertThat(fields)
                .isNotNull()
                .hasSize(3)
                .containsKeys("TEST", "LALA", "LILI");

        assertThat(fields.get("TEST"))
                .isEqualTo("abc");
        assertThat(fields.get("LALA"))
                .isEqualTo("");
        assertThat(fields.get("LILI"))
                .isEqualTo("ab");
    }

    @Test
    public void testReadMultilineRecord() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput("   <test:7>abc\r\nde<lala:3>asd");
        Map<String,String> fields = reader.readRecord(inputReader);

        assertThat(fields)
                .isNotNull()
                .hasSize(2)
                .containsKeys("TEST", "LALA");

        assertThat(fields.get("TEST"))
                .isEqualTo("abc\r\nde");
        assertThat(fields.get("LALA"))
                .isEqualTo("asd");
    }

    @Test
    public void testReadRecordAfterHeader() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput(" #treafa<ehc:4>sfar<eoh>  <test:3>abcde<lala:0>asd<lili:2>ab<eOr>");
        reader.readHeader(inputReader);
        Map<String,String> fields = reader.readRecord(inputReader);

        assertThat(fields)
                .isNotNull()
                .hasSize(3)
                .containsKeys("TEST", "LALA", "LILI");
    }

    @Test
    public void testReadHeader() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput(" #treafa<created_timestamp:15>20170216 224815<eoH>");
        AdifHeader header = reader.readHeader(inputReader);

        assertThat(header)
                .isNotNull();

        assertThat(header.getTimestamp())
                .isEqualTo(ZonedDateTime.of(2017, 2, 16, 22, 48, 15, 0, ZoneId.of("UTC")));
    }

    @Test
    public void testMultipleRecords() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput("afad<eoh><eor><eor>");
        reader.read(inputReader);
    }

    @Test
    public void testAdifSample() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = resourceInput("adif/sample.adi");
        Optional<Adif3> adif = reader.read(inputReader);
        assertThat(adif.get().header.version).isEqualTo("3.0.4");
        assertThat(adif.get().header.programId).isEqualTo("monolog");
        assertThat(adif.get().records)
                .isNotNull()
                .hasSize(3);

        assertThat(adif.get().records.get(0).getQsoDate()).isEqualTo(LocalDate.of(1990, 06, 20));
        assertThat(adif.get().records.get(0).getTimeOn()).isEqualTo(LocalTime.of(15, 23));
        assertThat(adif.get().records.get(0).getCall()).isEqualTo("VK9NS");
        assertThat(adif.get().records.get(0).getBand()).isEqualTo(BAND_20m);
        assertThat(adif.get().records.get(0).getMode()).isEqualTo(RTTY);
        assertThat(adif.get().records.get(0).getTxPwr()).isEqualTo(10.0);

        assertThat(adif.get().records.get(1).getQsoDate()).isEqualTo(LocalDate.of(2010, 10, 22));
        assertThat(adif.get().records.get(1).getTimeOn()).isEqualTo(LocalTime.of(01, 11));
        assertThat(adif.get().records.get(1).getCall()).isEqualTo("ON4UN");
        assertThat(adif.get().records.get(1).getBand()).isEqualTo(BAND_40m);
        assertThat(adif.get().records.get(1).getMode()).isEqualTo(PSK);
        assertThat(adif.get().records.get(1).getAddress()).isEqualTo("John Doe\r\n100 Main Street\r\nCity, ST 12345");
        assertThat(adif.get().records.get(1).getSilentKey()).isEqualTo(true);
        assertThat(adif.get().records.get(1).getSubmode()).isEqualTo(PSK63.adifCode());
        assertThat(adif.get().records.get(1).getTxPwr()).isEqualTo(2.0);

        assertThat(adif.get().records.get(2).getQsoDate()).isEqualTo(LocalDate.of(2018, 10, 16));
        assertThat(adif.get().records.get(2).getTimeOn()).isEqualTo(LocalTime.of(23, 12));
        assertThat(adif.get().records.get(2).getCall()).isEqualTo("K0TET");
        assertThat(adif.get().records.get(2).getBand()).isEqualTo(BAND_70cm);
        assertThat(adif.get().records.get(2).getMode()).isEqualTo(JT9);
        assertThat(adif.get().records.get(2).getSubmode()).isEqualTo(JT9H_FAST.adifCode());
        assertThat(adif.get().records.get(2).getTxPwr()).isEqualTo(100.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLotwNormalMode() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput("<MODE:5>PSK31");
        reader.read(inputReader);
    }

    @Test
    public void testLotwQuirksMode() throws Exception {
        AdiReader reader = new AdiReader();
        reader.setQuirksMode(true);
        BufferedReader inputReader = mockInput("<MODE:5>PSK31");
        Adif3 result = reader.read(inputReader).get();

        assertThat(result.records)
                .hasSize(1);
        assertThat(result.records.get(0).getMode().adifCode())
                .isEqualTo("PSK");
    }

    private BufferedReader mockInput(String input) {
        return new BufferedReader(new StringReader(input));
    }

    private BufferedReader resourceInput(String path) {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path)));
    }
}
