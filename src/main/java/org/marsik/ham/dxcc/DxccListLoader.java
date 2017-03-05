package org.marsik.ham.dxcc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.marsik.ham.adif.enums.Continent;

public class DxccListLoader {
    private static final Pattern ENTITY_RE = Pattern.compile("^\\s*" +
            "(?<prefixes>[A-Z0-9/,_-]+)?" +
            "((?<notes>(\\(\\d+\\),?)+)?|(?<arrl>[*#^]+)?){0,2}" +
            "\\s+" +
            "(?<name>[A-Z0-9,&()'. /-]*[A-Z).])" +
            "\\s+" +
            "(?<continent>((AS|EU|AF|OC|AN|NA|SA),?)+)" +
            "\\s+" +
            "(?<itu>(\\d{2}(,|-\\d{2})?)+|\\([A-Z]\\))" +
            "\\s+" +
            "(?<cq>(\\d{2}(,|-\\d{2})?)+|\\([A-Z]\\))" +
            "\\s+" +
            "(?<id>\\d{3})" +
            "\\s*" +
            "$", Pattern.CASE_INSENSITIVE);

    private static final Pattern NOTE_ID_RE = Pattern.compile("\\((?<id>\\d+)\\)", Pattern.CASE_INSENSITIVE);
    private static final Pattern TOTAL_ENTITY_RE = Pattern.compile("entities total:\\s+(?<count>\\d+)", Pattern.CASE_INSENSITIVE);

    private static final Pattern NOTE_RE = Pattern.compile("\\s+(?<id>\\d+)\\s+(?<text>.*)");

    public DxccList loadFromArrlDxccList(InputStream is) throws IOException {
        DxccList dxccList = new DxccList();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        boolean deleted = false;

        while((line = reader.readLine()) != null) {
            Matcher matcher = ENTITY_RE.matcher(line);
            if (matcher.matches()) {
                dxccList.getList().add(new Dxcc(
                        Integer.parseInt(matcher.group("id")),
                        matcher.group("prefixes"),
                        matcher.group("arrl"),
                        matcher.group("name"),
                        Stream.of(matcher.group("cq").split(",")).collect(Collectors.toList()),
                        Stream.of(matcher.group("itu").split(",")).collect(Collectors.toList()),
                        Stream.of(matcher.group("continent").split(",")).map(String::toUpperCase).map(Continent::findByCode).collect(Collectors.toList()),
                        deleted,
                        null,
                        null,
                        findNotes(matcher.group("notes"))
                ));
                continue;
            }

            matcher = TOTAL_ENTITY_RE.matcher(line);
            if (matcher.find()) {
                dxccList.setCount(Integer.parseInt(matcher.group("count")));
                continue;
            }

            matcher = NOTE_RE.matcher(line);
            if (matcher.matches()) {
                dxccList.getNotes().put(Integer.parseInt(matcher.group("id")), matcher.group("text"));
            }

            if ("CURRENT ENTITIES".equalsIgnoreCase(line)) {
                deleted = false;
            } else if ("DELETED ENTITIES".equalsIgnoreCase(line)) {
                deleted = true;
            }
        }

        return dxccList;
    }

    private List<Integer> findNotes(String notes) {
        List<Integer> notesList = new ArrayList<>();
        if (notes == null) {
            return notesList;
        }

        Matcher m = NOTE_ID_RE.matcher(notes);
        while (m.find()) {
            notesList.add(Integer.parseInt(m.group("id")));
        }
        return notesList;
    }
}
