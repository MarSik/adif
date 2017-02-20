package org.marsik.ham.adif;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import org.marsik.ham.adif.enums.AdifEnumCode;
import org.marsik.ham.grid.CoordinateWriter;

public class AdiWriter {
    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
    static final DateTimeFormatter timeFormatterShort = DateTimeFormatter.ofPattern("HHmm");

    private StringBuilder builder = new StringBuilder();

    public void append(String name, Boolean value) {
        if (value == null) return;
        appendFieldHeader(name, 1);
        builder.append(value ? "Y" : "N");
    }

    public void append(String name, String value) {
        if (value == null) return;
        String trimmed = encode(value);
        appendFieldHeader(name, trimmed.length());
        builder.append(trimmed);
    }

    public void append(String name, Integer value) {
        if (value == null) return;
        String trimmed = encode(value);
        appendFieldHeader(name, trimmed.length());
        builder.append(trimmed);
    }

    public void append(String name, Double value) {
        if (value == null) return;
        String trimmed = encode(value);
        appendFieldHeader(name, trimmed.length());
        builder.append(trimmed);
    }

    public void append(String name, AdifEnumCode value) {
        if (value == null) return;
        String code = encode(value);
        appendFieldHeader(name, code.length(), "E");
        builder.append(code);
    }

    public void append(String name, LocalTime value) {
        if (value == null) return;
        String trimmed = encode(value);
        appendFieldHeader(name, trimmed.length());
        builder.append(trimmed);
    }

    public void append(String name, LocalDate value) {
        if (value == null) return;
        String trimmed = encode(value);
        appendFieldHeader(name, trimmed.length());
        builder.append(trimmed);
    }

    public void append(String name, ZonedDateTime value) {
        if (value == null) return;
        String trimmed = encode(value);
        appendFieldHeader(name, trimmed.length());
        builder.append(trimmed);
    }

    private String encode(String value) {
        return value.trim();
    }

    private String encode(Integer value) {
        return value.toString();
    }

    private String encode(Double value) {
        return String.format("%f", value);
    }

    private String encode(AdifEnumCode value) {
        return value.adifCode().toUpperCase();
    }

    private String encode(LocalDate value) {
        return value.format(dateFormatter);
    }

    private String encode(LocalTime value) {
        return value.format(timeFormatter);
    }

    private String encode(ZonedDateTime value) {
        return value.withZoneSameInstant(ZoneId.of("UTC")).format(dateFormatter);
    }

    private void appendFieldHeader(String name, Integer length) {
        builder.append("<");
        builder.append(name.toUpperCase());
        builder.append(":");
        builder.append(length);
        builder.append(">");
    }

    public void appendEndOfRecord() {
        builder.append("<EOR>");
    }

    public void appendEndOfHeader() {
        builder.append("<EOH>");
    }

    private void appendFieldHeader(String name, Integer length, String type) {
        builder.append("<");
        builder.append(name.toUpperCase());
        builder.append(":");
        builder.append(length);
        builder.append(":");
        builder.append(type.toUpperCase());
        builder.append(">");
    }

    public void appendIntegers(String name, Collection<Integer> values) {
        if (values == null) return;
        String value = values.stream()
                .map(this::encode)
                .collect(Collectors.joining(","));

        appendFieldHeader(name, value.length());
        builder.append(value);
    }

    public void appendStrings(String name, Collection<String> values) {
        if (values == null) return;
        String value = values.stream()
                .map(this::encode)
                .collect(Collectors.joining(","));

        appendFieldHeader(name, value.length());
        builder.append(value);
    }

    public void appendEnums(String name, Collection<AdifEnumCode> values) {
        if (values == null) return;
        String value = values.stream()
                .map(this::encode)
                .collect(Collectors.joining(","));

        appendFieldHeader(name, value.length());
        builder.append(value);
    }

    public void appendDoubles(String name, Collection<AdifEnumCode> values) {
        if (values == null) return;
        String value = values.stream()
                .map(this::encode)
                .collect(Collectors.joining(","));

        appendFieldHeader(name, value.length());
        builder.append(value);
    }

    public String toString() {
        return builder.toString();
    }

    public void append(Adif3Record rec) {
        append("ADDRESS", rec.getAddress());
        append("AGE", rec.getAge());
        append("A_INDEX", rec.getAIndex());
        append("ANT_AZ", rec.getAntAz());
        append("ANT_EL", rec.getAntEl());
        append("ANT_PATH", rec.getAntPath());
        append("ARRL_SECT", rec.getArrlSect());
        appendStrings("AWARD_SUBMITTED", rec.getAwardSubmitted());
        appendStrings("AWARD_GRANTED", rec.getAwardGranted());
        append("BAND", rec.getBand());
        append("BAND_RX", rec.getBandRx());
        append("CALL", rec.getCall());
        append("CHECK", rec.getCheck());
        append("CLASS", rec.getContestClass());
        append("CLUBLOG_QSO_UPLOAD_DATE", rec.getClublogQsoUploadDate());
        append("CLUBLOG_QSO_UPLOAD_STATUS", rec.getClublogQsoUploadStatus());
        append("CNTY", rec.getCnty());
        append("COMMENT", rec.getComment());
        append("CONT", rec.getCont());
        append("CONTACTED_OP", rec.getContactedOp());
        append("CONTEST_ID", rec.getContestId());
        append("COUNTRY", rec.getCountry());
        append("CQZ", rec.getCqz());
        appendStrings("CREDIT_SUBMITTED", rec.getCreditSubmitted());
        appendStrings("CREDIT_GRANTED", rec.getCreditGranted());
        append("DISTANCE", rec.getDistance());
        append("DXCC", rec.getDxcc());
        append("EMAIL", rec.getEmail());
        append("EQ_CALL", rec.getEqCall());
        append("EQSL_QSLRDATE", rec.getEqslQslRDate());
        append("EQSL_QSLSDATE", rec.getEqslQslSDate());
        append("EQSL_QSL_RCVD", rec.getEqslQslRcvd());
        append("EQSL_QSL_SENT", rec.getEqslQslSent());
        append("FISTS", rec.getFists());
        append("FISTS_CC", rec.getFistsCc());
        append("FORCE_INT", rec.getForceInt());
        append("FREQ", rec.getFreq());
        append("FREQ_RX", rec.getFreqRx());
        append("GRIDSQUARE", rec.getGridsquare());
        append("HRDLOG_QSO_UPLOAD_DATE", rec.getHrdlogQsoUploadDate());
        append("HRDLOG_QSO_UPLOAD_STATUS", rec.getHrdlogQsoUploadStatus());
        append("IOTA", rec.getIota());
        append("IOTA_ISLAND_ID", rec.getIotaIslandId());
        append("ITUZ", rec.getItuz());
        append("K_INDEX", rec.getKIndex());

        if (rec.getCoordinates() != null) {
            append("LAT", CoordinateWriter.latToDM(rec.getCoordinates().getLatitude()));
            append("LON", CoordinateWriter.lonToDM(rec.getCoordinates().getLongitude()));
        }

        append("LOTW_QSLRDATE", rec.getLotwQslRDate());
        append("LOTW_QSLSDATE", rec.getLotwQslSDate());
        append("LOTW_QSL_RCVD", rec.getLotwQslRcvd());
        append("LOTW_QSL_SENT", rec.getLotwQslSent());
        append("MAX_BURSTS", rec.getMaxBursts());
        append("MODE", rec.getMode());
        append("MS_SHOWER", rec.getMsShower());
        append("MY_CITY", rec.getMyCity());
        append("MY_CNTY", rec.getMyCnty());
        append("MY_COUNTRY", rec.getMyCountry());
        append("MY_CQ_ZONE", rec.getMyCqZone());
        append("MY_DXCC", rec.getMyDxcc());
        append("MY_FISTS", rec.getMyFists());
        append("MY_GRIDSQUARE", rec.getMyGridSquare());
        append("MY_IOTA", rec.getMyIota());
        append("MY_IOTA_ISLAND_ID", rec.getMyIotaIslandId());
        append("MY_ITU_ZONE", rec.getMyItuZone());

        if (rec.getMyCoordinates() != null) {
            append("MY_LAT", CoordinateWriter.latToDM(rec.getMyCoordinates().getLatitude()));
            append("MY_LON", CoordinateWriter.lonToDM(rec.getMyCoordinates().getLongitude()));
        }

        append("MY_NAME", rec.getMyName());
        append("MY_POSTAL_CODE", rec.getMyPostalCode());
        append("MY_RIG", rec.getMyRig());
        append("MY_SIG", rec.getMySig());
        append("MY_SIG_INFO", rec.getMySigInfo());
        append("MY_SOTA_REF", rec.getMySotaRef());
        append("MY_STATE", rec.getMyState());
        append("MY_STREET", rec.getMyStreet());
        appendStrings("MY_USACA_COUNTIES", rec.getMyUsaCaCounties());
        appendStrings("MY_VUCC_GRIDS", rec.getMyVuccGrids());
        append("NAME", rec.getName());
        append("NOTES", rec.getNotes());
        append("NR_BURSTS", rec.getNrBursts());
        append("NR_PINGS", rec.getNrPings());
        append("OPERATOR", rec.getOperator());
        append("OWNER_CALLSIGN", rec.getOwnerCallsign());
        append("PFX", rec.getPfx());
        append("PRECEDENCE", rec.getPrecedence());
        append("PROP_MODE", rec.getPropMode());
        append("PUBLIC_KEY", rec.getPublicKey());
        append("QRZCOM_QSO_UPLOAD_DATE", rec.getQrzcomQsoUploadDate());
        append("QRZCOM_QSO_UPLOAD_STATUS", rec.getQrzcomQsoUploadStatus());
        append("QSLMSG", rec.getQslMsg());
        append("QSLRDATE", rec.getQslRDate());
        append("QSLSDATE", rec.getQslSDate());
        append("QSL_RCVD", rec.getQslRcvd());
        append("QSL_RCVD_VIA", rec.getQslRcvdVia());
        append("QSL_SENT", rec.getQslSent());
        append("QSL_SENT_VIA", rec.getQslSentVia());
        append("QSL_VIA", rec.getQslVia());
        append("QSO_COMPLETE", rec.getQsoComplete());
        append("QSO_DATE", rec.getQsoDate());
        append("QSO_DATE_OFF", rec.getQsoDateOff());
        append("QSO_RANDOM", rec.getQsoRandom());
        append("QTH", rec.getQth());
        append("RIG", rec.getRig());
        append("RST_RCVD", rec.getRstRcvd());
        append("RST_SENT", rec.getRstSent());
        append("RX_PWR", rec.getRxPwr());
        append("SAT_MODE", rec.getSatMode());
        append("SAT_NAME", rec.getSatName());
        append("SFI", rec.getSfi());
        append("SIG", rec.getSig());
        append("SIG_INFO", rec.getSigInfo());
        append("SKCC", rec.getSkcc());
        append("SOTA_REF", rec.getSotaRef());
        append("SRX", rec.getSrx());
        append("SRX_STRING", rec.getSrxString());
        append("STATE", rec.getState());
        append("STATION_CALLSIGN", rec.getStationCallsign());
        append("STX", rec.getStx());
        append("STX_STRING", rec.getStxString());
        append("SUBMODE", rec.getSubmode());
        append("SWL", rec.getSwl());
        append("TEN_TEN", rec.getTenTen());
        append("TIME_OFF", rec.getTimeOff());
        append("TIME_ON", rec.getTimeOn());
        append("TX_PWR", rec.getTxPwr());
        appendStrings("USACA_COUNTIES", rec.getUsaCaCounties());
        appendStrings("VUCC_GRIDS", rec.getVuccGrids());
        append("WEB", rec.getWeb());
        appendEndOfRecord();
    }

    public void append(AdifHeader header) {
        append("adif_ver", header.getVersion());
        append("programid", getClass().getCanonicalName());
        appendEndOfHeader();
    }

    public String toDegrees(Double deg) {
        return null; // TODO
    }
}
