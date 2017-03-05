package org.marsik.ham.dxcc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class DxccListLoaderTest {
    @Test
    public void loadFromActiveArrlDxccList() throws Exception {
        InputStream activeInputStream = ClassLoader.getSystemResourceAsStream("dxcc-active.txt");
        loadArrlDxccList(activeInputStream);
    }

    @Test
    public void loadFromDeletedArrlDxccList() throws Exception {
        InputStream deletedInputStream = ClassLoader.getSystemResourceAsStream("dxcc-deleted.txt");
        loadArrlDxccList(deletedInputStream);
    }

    private void loadArrlDxccList(InputStream activeInputStream) throws IOException {
        DxccListLoader loader = new DxccListLoader();
        DxccList dxccList = loader.loadFromArrlDxccList(activeInputStream);
        assertThat(dxccList)
                .isNotNull();
        assertThat(dxccList.getCount())
                .isNotNull()
                .isPositive();
        assertThat(dxccList.getList())
                .isNotNull()
                .hasSize(dxccList.getCount());
        assertThat(dxccList.getNotes())
                .isNotNull();

        dxccList.getList().stream()
                .map(Dxcc::getNotes)
                .flatMapToInt(l -> l.stream().mapToInt(Integer::intValue))
                .forEach(id -> assertThat(dxccList.getNotes()).containsKey(id));
    }
}
