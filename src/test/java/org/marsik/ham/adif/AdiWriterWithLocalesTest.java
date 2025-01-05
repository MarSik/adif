package org.marsik.ham.adif;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class AdiWriterWithLocalesTest {

    private Locale originalLocale;
    private final Locale testLocale;
    private final String expected;

    @Before
    public void setUp() {
        originalLocale = Locale.getDefault();
    }

    @After
    public void tearDown() {
        Locale.setDefault(originalLocale);
    }

    public AdiWriterWithLocalesTest(Locale testLocale, String expected) {
        this.testLocale = testLocale;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        String expectedDouble = "<DOUBLE:8>3.700000";

        return Arrays.asList(new Object[][]{
                {Locale.US, expectedDouble},
                {new Locale("pl", "PL"), expectedDouble}
        });
    }

    @Test
    public void testDoubleInDifferentLocales()  {
        Locale.setDefault(testLocale);

        AdiWriter writer = new AdiWriter();
        writer.append("double", 3.7d);

        assertThat(writer.toString())
                .isEqualTo(expected);
    }

}
