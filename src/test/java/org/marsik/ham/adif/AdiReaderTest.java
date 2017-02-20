package org.marsik.ham.adif;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

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
    public void testReadRecord() throws Exception {
        AdiReader reader = new AdiReader();
        BufferedReader inputReader = mockInput("   <test:3>abcde<lala:0>asd<lili:2>ab<eOr>");
        Map<String,String> fields = reader.readRecord(inputReader);

        assertThat(fields)
                .isNotNull()
                .hasSize(3)
                .containsKeys("test", "lala", "lili");

        assertThat(fields.get("test"))
                .isEqualTo("abc");
        assertThat(fields.get("lala"))
                .isEqualTo("");
        assertThat(fields.get("lili"))
                .isEqualTo("ab");
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
                .containsKeys("test", "lala", "lili");
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
        reader.read(inputReader);
    }

    private BufferedReader mockInput(String input) {
        return new BufferedReader(new StringReader(input));
    }

    private BufferedReader resourceInput(String path) {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path)));
    }
}
