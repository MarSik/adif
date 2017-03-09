package org.marsik.ham.adif;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.marsik.ham.adif.enums.AntPath;
import org.marsik.ham.adif.enums.Band;
import org.marsik.ham.adif.enums.Continent;
import org.marsik.ham.adif.enums.Mode;
import org.marsik.ham.adif.enums.Propagation;
import org.marsik.ham.adif.enums.QslRcvd;
import org.marsik.ham.adif.enums.QslSent;
import org.marsik.ham.adif.enums.QslVia;
import org.marsik.ham.adif.enums.QsoComplete;
import org.marsik.ham.adif.enums.QsoUploadStatus;
import org.marsik.ham.adif.types.Iota;
import org.marsik.ham.adif.types.Sota;
import org.marsik.ham.grid.CoordinateWriter;
import org.marsik.ham.util.MultiOptional;

public class AdiReader {
    private static final Pattern NUMERIC_RE = Pattern.compile("-?\\d+(\\.\\d+)?");

    public Optional<Adif3> read(BufferedReader reader) throws IOException {
        Adif3 document = new Adif3();

        reader.mark(1);
        int c = reader.read();
        if (c == -1) {
            // EOF
            return Optional.empty();
        } else if (c != '<') {
            AdifHeader header = readHeader(reader);
            document.setHeader(header);
        } else {
            // No header
            reader.reset();
        }

        while (true) {
            Map<String, String> recordFields = readRecord(reader);
            if (recordFields == null) {
                break;
            }
            document.getRecords().add(parseRecord(recordFields));
        }

        return Optional.of(document);
    }

    private Adif3Record parseRecord(Map<String, String> recordFields) {
        Adif3Record record = new Adif3Record();

        maybeGet(recordFields, "ADDRESS").map(Function.identity()).ifPresent(record::setAddress);
        maybeGet(recordFields, "AGE").map(Integer::parseInt).ifPresent(record::setAge);
        maybeGet(recordFields, "A_INDEX").map(Double::parseDouble).ifPresent(record::setAIndex);
        maybeGet(recordFields, "ANT_AZ").map(Double::parseDouble).ifPresent(record::setAntAz);
        maybeGet(recordFields, "ANT_EL").map(Double::parseDouble).ifPresent(record::setAntEl);
        maybeGet(recordFields, "ANT_PATH").map(AntPath::findByCode).ifPresent(record::setAntPath);
        maybeGet(recordFields, "ARRL_SECT").map(Function.identity()).ifPresent(record::setArrlSect);
        maybeGet(recordFields, "AWARD_SUBMITTED")
                .map(s -> parseCommaArray(s, String::valueOf))
                .ifPresent(record::setAwardSubmitted);
        maybeGet(recordFields, "AWARD_GRANTED")
                .map(s -> parseCommaArray(s, String::valueOf))
                .ifPresent(record::setAwardGranted);
        maybeGet(recordFields, "BAND").map(Band::findByCode).ifPresent(record::setBand);
        maybeGet(recordFields, "BAND_RX").map(Band::findByCode).ifPresent(record::setBandRx);
        maybeGet(recordFields, "CALL").map(Function.identity()).ifPresent(record::setCall);
        maybeGet(recordFields, "CHECK").map(Function.identity()).ifPresent(record::setCheck);
        maybeGet(recordFields, "CLASS").map(Function.identity()).ifPresent(record::setContestClass);
        maybeGet(recordFields, "CLUBLOG_QSO_UPLOAD_DATE")
                .map(this::parseDate)
                .ifPresent(record::setClublogQsoUploadDate);
        maybeGet(recordFields, "CLUBLOG_QSO_UPLOAD_STATUS")
                .map(QsoUploadStatus::findByCode)
                .ifPresent(record::setClublogQsoUploadStatus);
        maybeGet(recordFields, "CNTY").map(Function.identity()).ifPresent(record::setCnty);
        maybeGet(recordFields, "COMMENT").map(Function.identity()).ifPresent(record::setComment);
        maybeGet(recordFields, "CONT").map(Continent::findByCode).ifPresent(record::setCont);
        maybeGet(recordFields, "CONTACTED_OP").map(Function.identity()).ifPresent(record::setContactedOp);
        maybeGet(recordFields, "CONTEST_ID").map(Function.identity()).ifPresent(record::setContestId);
        maybeGet(recordFields, "COUNTRY").map(Function.identity()).ifPresent(record::setCountry);
        maybeGet(recordFields, "CQZ").map(Integer::parseInt).ifPresent(record::setCqz);
        maybeGet(recordFields, "CREDIT_SUBMITTED")
                .map(s -> parseCommaArray(s, String::valueOf))
                .ifPresent(record::setCreditSubmitted);
        maybeGet(recordFields, "CREDIT_GRANTED")
                .map(s -> parseCommaArray(s, String::valueOf))
                .ifPresent(record::setCreditGranted);
        maybeGet(recordFields, "DISTANCE").map(Double::parseDouble).ifPresent(record::setDistance);
        maybeGet(recordFields, "DXCC").map(Integer::parseInt).ifPresent(record::setDxcc);
        maybeGet(recordFields, "EMAIL").map(Function.identity()).ifPresent(record::setEmail);
        maybeGet(recordFields, "EQ_CALL").map(Function.identity()).ifPresent(record::setEqCall);
        maybeGet(recordFields, "EQSL_QSLRDATE").map(this::parseDate).ifPresent(record::setEqslQslRDate);
        maybeGet(recordFields, "EQSL_QSLSDATE").map(this::parseDate).ifPresent(record::setEqslQslSDate);
        maybeGet(recordFields, "EQSL_QSL_RCVD").map(QslRcvd::findByCode).ifPresent(record::setEqslQslRcvd);
        maybeGet(recordFields, "EQSL_QSL_SENT").map(QslSent::findByCode).ifPresent(record::setEqslQslSent);
        maybeGet(recordFields, "FISTS").map(Function.identity()).ifPresent(record::setFists);
        maybeGet(recordFields, "FISTS_CC").map(Function.identity()).ifPresent(record::setFistsCc);
        maybeGet(recordFields, "FORCE_INT").map(this::parseBool).ifPresent(record::setForceInt);
        maybeGet(recordFields, "FREQ").map(Double::parseDouble).ifPresent(record::setFreq);
        maybeGet(recordFields, "FREQ_RX").map(Double::parseDouble).ifPresent(record::setFreqRx);
        maybeGet(recordFields, "GRIDSQUARE").map(Function.identity()).ifPresent(record::setGridsquare);
        maybeGet(recordFields, "HRDLOG_QSO_UPLOAD_DATE")
                .map(this::parseDate)
                .ifPresent(record::setHrdlogQsoUploadDate);
        maybeGet(recordFields, "HRDLOG_QSO_UPLOAD_STATUS")
                .map(QsoUploadStatus::findByCode)
                .ifPresent(record::setHrdlogQsoUploadStatus);
        maybeGet(recordFields, "IOTA").map(Iota::findByCode).ifPresent(record::setIota);
        maybeGet(recordFields, "IOTA_ISLAND_ID").map(Function.identity()).ifPresent(record::setIotaIslandId);
        maybeGet(recordFields, "ITUZ").map(Integer::parseInt).ifPresent(record::setItuz);
        maybeGet(recordFields, "K_INDEX").map(Double::parseDouble).ifPresent(record::setKIndex);

        Optional<Double> lat = maybeGet(recordFields, "LAT").map(CoordinateWriter::dmToLat);
        Optional<Double> lon = maybeGet(recordFields, "LON").map(CoordinateWriter::dmToLon);
        MultiOptional.two(lat, lon, GlobalCoordinates::new).ifPresent(record::setCoordinates);

        maybeGet(recordFields, "LOTW_QSLRDATE").map(this::parseDate).ifPresent(record::setLotwQslRDate);
        maybeGet(recordFields, "LOTW_QSLSDATE").map(this::parseDate).ifPresent(record::setLotwQslSDate);
        maybeGet(recordFields, "LOTW_QSL_RCVD").map(QslRcvd::findByCode).ifPresent(record::setLotwQslRcvd);
        maybeGet(recordFields, "LOTW_QSL_SENT").map(QslSent::findByCode).ifPresent(record::setLotwQslSent);
        maybeGet(recordFields, "MAX_BURSTS").map(Integer::parseInt).ifPresent(record::setMaxBursts);
        maybeGet(recordFields, "MODE").map(Mode::findByCode).ifPresent(record::setMode);
        maybeGet(recordFields, "MS_SHOWER").map(Function.identity()).ifPresent(record::setMsShower);
        maybeGet(recordFields, "MY_CITY").map(Function.identity()).ifPresent(record::setMyCity);
        maybeGet(recordFields, "MY_CNTY").map(Function.identity()).ifPresent(record::setMyCnty);
        maybeGet(recordFields, "MY_COUNTRY").map(Function.identity()).ifPresent(record::setMyCountry);
        maybeGet(recordFields, "MY_CQ_ZONE").map(Integer::parseInt).ifPresent(record::setMyCqZone);
        maybeGet(recordFields, "MY_DXCC").map(Integer::parseInt).ifPresent(record::setMyDxcc);
        maybeGet(recordFields, "MY_FISTS").map(Function.identity()).ifPresent(record::setMyFists);
        maybeGet(recordFields, "MY_GRIDSQUARE").map(Function.identity()).ifPresent(record::setMyGridSquare);
        maybeGet(recordFields, "MY_IOTA").map(Iota::findByCode).ifPresent(record::setMyIota);
        maybeGet(recordFields, "MY_IOTA_ISLAND_ID").map(Function.identity()).ifPresent(record::setMyIotaIslandId);
        maybeGet(recordFields, "MY_ITU_ZONE").map(Integer::parseInt).ifPresent(record::setMyItuZone);

        Optional<Double> myLat = maybeGet(recordFields, "MY_LAT").map(CoordinateWriter::dmToLat);
        Optional<Double> myLon = maybeGet(recordFields, "MY_LON").map(CoordinateWriter::dmToLon);
        MultiOptional.two(myLat, myLon, GlobalCoordinates::new).ifPresent(record::setMyCoordinates);

        maybeGet(recordFields, "MY_NAME").map(Function.identity()).ifPresent(record::setMyName);
        maybeGet(recordFields, "MY_POSTAL_CODE").map(Function.identity()).ifPresent(record::setMyPostalCode);
        maybeGet(recordFields, "MY_RIG").map(Function.identity()).ifPresent(record::setMyRig);
        maybeGet(recordFields, "MY_SIG").map(Function.identity()).ifPresent(record::setMySig);
        maybeGet(recordFields, "MY_SIG_INFO").map(Function.identity()).ifPresent(record::setMySigInfo);
        maybeGet(recordFields, "MY_SOTA_REF").map(Sota::valueOf).ifPresent(record::setMySotaRef);
        maybeGet(recordFields, "MY_STATE").map(Function.identity()).ifPresent(record::setMyState);
        maybeGet(recordFields, "MY_STREET").map(Function.identity()).ifPresent(record::setMyStreet);
        maybeGet(recordFields, "MY_USACA_COUNTIES")
                .map(s -> parseColonArray(s, String::valueOf))
                .ifPresent(record::setMyUsaCaCounties);
        maybeGet(recordFields, "MY_VUCC_GRIDS")
                .map(s -> parseCommaArray(s, String::valueOf))
                .ifPresent(record::setMyVuccGrids);
        maybeGet(recordFields, "NAME").map(Function.identity()).ifPresent(record::setName);
        maybeGet(recordFields, "NOTES").map(Function.identity()).ifPresent(record::setNotes);
        maybeGet(recordFields, "NR_BURSTS").map(Integer::parseInt).ifPresent(record::setNrBursts);
        maybeGet(recordFields, "NR_PINGS").map(Integer::parseInt).ifPresent(record::setNrPings);
        maybeGet(recordFields, "OPERATOR").map(Function.identity()).ifPresent(record::setOperator);
        maybeGet(recordFields, "OWNER_CALLSIGN").map(Function.identity()).ifPresent(record::setOwnerCallsign);
        maybeGet(recordFields, "PFX").map(Function.identity()).ifPresent(record::setPfx);
        maybeGet(recordFields, "PRECEDENCE").map(Function.identity()).ifPresent(record::setPrecedence);
        maybeGet(recordFields, "PROP_MODE").map(Propagation::findByCode).ifPresent(record::setPropMode);
        maybeGet(recordFields, "PUBLIC_KEY").map(Function.identity()).ifPresent(record::setPublicKey);
        maybeGet(recordFields, "QRZCOM_QSO_UPLOAD_DATE")
                .map(this::parseDate)
                .ifPresent(record::setQrzcomQsoUploadDate);
        maybeGet(recordFields, "QRZCOM_QSO_UPLOAD_STATUS")
                .map(QsoUploadStatus::findByCode)
                .ifPresent(record::setQrzcomQsoUploadStatus);
        maybeGet(recordFields, "QSLMSG").map(Function.identity()).ifPresent(record::setQslMsg);
        maybeGet(recordFields, "QSLRDATE").map(this::parseLocalDate).ifPresent(record::setQslRDate);
        maybeGet(recordFields, "QSLSDATE").map(this::parseLocalDate).ifPresent(record::setQslSDate);
        maybeGet(recordFields, "QSL_RCVD").map(QslRcvd::findByCode).ifPresent(record::setQslRcvd);
        maybeGet(recordFields, "QSL_RCVD_VIA").map(QslVia::findByCode).ifPresent(record::setQslRcvdVia);
        maybeGet(recordFields, "QSL_SENT").map(QslSent::findByCode).ifPresent(record::setQslSent);
        maybeGet(recordFields, "QSL_SENT_VIA").map(QslVia::findByCode).ifPresent(record::setQslSentVia);
        maybeGet(recordFields, "QSL_VIA").map(Function.identity()).ifPresent(record::setQslVia);
        maybeGet(recordFields, "QSO_COMPLETE").map(QsoComplete::findByCode).ifPresent(record::setQsoComplete);
        maybeGet(recordFields, "QSO_DATE").map(s -> LocalDate.parse(s, AdiWriter.dateFormatter)).ifPresent(record::setQsoDate);
        maybeGet(recordFields, "QSO_DATE_OFF").map(s -> LocalDate.parse(s, AdiWriter.dateFormatter)).ifPresent(record::setQsoDateOff);
        maybeGet(recordFields, "QSO_RANDOM").map(this::parseBool).ifPresent(record::setQsoRandom);
        maybeGet(recordFields, "QTH").map(Function.identity()).ifPresent(record::setQth);
        maybeGet(recordFields, "RIG").map(Function.identity()).ifPresent(record::setRig);
        maybeGet(recordFields, "RST_RCVD").map(Function.identity()).ifPresent(record::setRstRcvd);
        maybeGet(recordFields, "RST_SENT").map(Function.identity()).ifPresent(record::setRstSent);
        maybeGet(recordFields, "RX_PWR")
                .map(s -> s.replaceAll("[wW]$", ""))
                .filter(AdiReader::isNumeric)
                .map(Double::parseDouble)
                .ifPresent(record::setRxPwr);
        maybeGet(recordFields, "SAT_MODE").map(Function.identity()).ifPresent(record::setSatMode);
        maybeGet(recordFields, "SAT_NAME").map(Function.identity()).ifPresent(record::setSatName);
        maybeGet(recordFields, "SFI").map(Double::parseDouble).ifPresent(record::setSfi);
        maybeGet(recordFields, "SIG").map(Function.identity()).ifPresent(record::setSig);
        maybeGet(recordFields, "SIG_INFO").map(Function.identity()).ifPresent(record::setSigInfo);
        maybeGet(recordFields, "SILENT_KEY").map(this::parseBool).ifPresent(record::setSilentKey);
        maybeGet(recordFields, "SKCC").map(Function.identity()).ifPresent(record::setSkcc);
        maybeGet(recordFields, "SOTA_REF").map(Sota::valueOf).ifPresent(record::setSotaRef);
        maybeGet(recordFields, "SRX").map(Integer::parseInt).ifPresent(record::setSrx);
        maybeGet(recordFields, "SRX_STRING").map(Function.identity()).ifPresent(record::setSrxString);
        maybeGet(recordFields, "STATE").map(Function.identity()).ifPresent(record::setState);
        maybeGet(recordFields, "STATION_CALLSIGN").map(Function.identity()).ifPresent(record::setStationCallsign);
        maybeGet(recordFields, "STX").map(Integer::parseInt).ifPresent(record::setStx);
        maybeGet(recordFields, "STX_STRING").map(Function.identity()).ifPresent(record::setStxString);
        maybeGet(recordFields, "SUBMODE").map(Function.identity()).ifPresent(record::setSubmode);
        maybeGet(recordFields, "SWL").map(this::parseBool).ifPresent(record::setSwl);
        maybeGet(recordFields, "TEN_TEN").map(Integer::parseInt).ifPresent(record::setTenTen);
        maybeGet(recordFields, "TIME_OFF").map(this::parseTime).ifPresent(record::setTimeOff);
        maybeGet(recordFields, "TIME_ON").map(this::parseTime).ifPresent(record::setTimeOn);
        maybeGet(recordFields, "TX_PWR")
                .map(s -> s.replaceAll("[wW]$", ""))
                .filter(AdiReader::isNumeric)
                .map(Double::parseDouble)
                .ifPresent(record::setTxPwr);
        maybeGet(recordFields, "UKSMG").map(Integer::parseInt).ifPresent(record::setUksmg);
        maybeGet(recordFields, "USACA_COUNTIES")
                .map(s -> parseColonArray(s, String::valueOf))
                .ifPresent(record::setUsaCaCounties);
        maybeGet(recordFields, "VUCC_GRIDS")
                .map(s -> parseCommaArray(s, String::valueOf))
                .ifPresent(record::setVuccGrids);
        maybeGet(recordFields, "WEB").map(Function.identity()).ifPresent(record::setWeb);

        return record;
    }

    private <K, V> Optional<V> maybeGet(Map<K, V> map, K key) {
        V res = map.get(key);
        return res == null ? Optional.empty() : Optional.of(res);
    }

    Map<String, String> readRecord(BufferedReader reader) throws IOException, EOFException {
        Map<String, String> fields = new HashMap<>();
        Field field = readField(reader);
        if (field == null) {
            return null;
        }

        while (field != null && !"eor".equalsIgnoreCase(field.getName())) {
            fields.put(field.getName().toUpperCase(), field.getValue());
            field = readField(reader);
        }

        return fields;
    }

    @Data
    @AllArgsConstructor
    static class Field {
        String name;
        String value;
    }

    @Data
    @AllArgsConstructor
    static class Tag {
        String name;
        int length;
        String type;
    }

    private Tag parseTag(String tag) {
        String[] pieces = tag.substring(1, tag.length() - 1).split(":");
        return new Tag(pieces.length > 0 ? pieces[0] : null,
                pieces.length > 1 ? Integer.parseInt(pieces[1]) : 0,
                pieces.length > 2 ? pieces[2] : null);
    }

    Field readField(BufferedReader reader) throws EOFException, IOException {
        readUntil(reader, '<', false);
        final String tag = readUntil(reader, '>', true);
        if (tag.isEmpty()) {
            return null;
        }

        final Tag parsedTag = parseTag(tag);
        if (parsedTag.getLength() == 0) {
            return new Field(parsedTag.getName(), "");
        }

        final int len = parsedTag.getLength();
        final String value = readLength(reader, len);

        return new Field(parsedTag.getName(), value);
    }

    private String readLength(BufferedReader reader, int len) throws IOException {
        char[] content = new char[len]; // todo limit length to avoid DOS attack
        int res;
        int read = 0;
        while (read < len) {
            res = reader.read(content, read, len - read);
            if (res >= 0) {
                read += res;
            }
        }
        return String.copyValueOf(content);
    }

    AdifHeader readHeader(BufferedReader reader) throws IOException {
        AdifHeader header = new AdifHeader();

        // read all content until <eoh> (case insensitive)
        while (true) {
            readUntil(reader, '<', false);
            String tag = readUntil(reader, '>', true);
            Tag parsedTag = parseTag(tag);
            final String value = readLength(reader, parsedTag.getLength());

            if ("eoh".equalsIgnoreCase(parsedTag.getName())) {
                break;
            } else if ("adif_ver".equalsIgnoreCase(parsedTag.getName())) {
                header.setVersion(value);
            } else if ("programid".equalsIgnoreCase(parsedTag.getName())) {
                header.setProgramId(value);
            } else if ("programversion".equalsIgnoreCase(parsedTag.getName())) {
                header.setProgramVersion(value);
            } else if ("created_timestamp".equalsIgnoreCase(parsedTag.getName())) {
                header.setTimestamp(
                        LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyyMMdd HHmmss")).atZone(ZoneId.of("UTC")));
            }
        }

        return header;
    }

    private String readUntil(Reader reader, char stop, boolean inclusive) throws IOException {
        reader.mark(1);
        int c = reader.read();
        StringBuilder builder = new StringBuilder();
        while (c != -1 && c != stop) {
            builder.append((char) c);
            reader.mark(1);
            c = reader.read();
        }

        if (c != -1 && inclusive) {
            builder.append((char) c);
        } else {
            reader.reset();
        }

        return builder.toString();
    }

    private ZonedDateTime parseDate(String s) {
        return LocalDate.parse(s, AdiWriter.dateFormatter).atStartOfDay(ZoneId.of("UTC"));
    }

    private LocalDate parseLocalDate(String s) {
        return LocalDate.parse(s, AdiWriter.dateFormatter);
    }

    private LocalTime parseTime(String s) {
        return LocalTime.parse(s,
                s.length() > 4 ? AdiWriter.timeFormatter : AdiWriter.timeFormatterShort);
    }

    private boolean parseBool(String s) {
        return s.equalsIgnoreCase("Y");
    }

    private <T> List<T> parseCommaArray(String s, Function<String, T> fieldConverter) {
        return Stream.of(s.split(","))
                .map(fieldConverter)
                .collect(Collectors.toList());
    }

    private static boolean isNumeric(String s)
    {
        return NUMERIC_RE.matcher(s).matches();
    }

    private <T> List<T> parseColonArray(String s, Function<String, T> fieldConverter) {
        return Stream.of(s.split(":"))
                .map(fieldConverter)
                .collect(Collectors.toList());
    }
}
