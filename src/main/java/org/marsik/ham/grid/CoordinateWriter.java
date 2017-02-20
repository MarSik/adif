package org.marsik.ham.grid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoordinateWriter {
    private static final Pattern LAT_RE = Pattern.compile("([NS]) ?([0-9]{0,2}) +([0-9]{1,2}(\\.[0-9]+)?)", Pattern.CASE_INSENSITIVE);
    private static final Pattern LON_RE = Pattern.compile("([EW]) ?([01]?[0-9]{0,2}) +([0-9]{1,2}(\\.[0-9]+)?)", Pattern.CASE_INSENSITIVE);

    private static String getLatitudePrefix(Double lat) {
        return lat >= 0.0 ? "N" : "S";
    }

    private static String getLongitudePrefix(Double lat) {
        return lat >= 0.0 ? "E" : "W";
    }

    public static String lonToDM(double lon) {
        String prefix = getLongitudePrefix(lon);
        lon = Math.abs(lon);
        int deg = (int) lon;
        double min = 60.0 * (lon - (int)lon);

        return String.format("%s%03d %02.3f", prefix, deg, min);
    }

    public static String latToDM(double lat) {
        String prefix = getLatitudePrefix(lat);
        lat = Math.abs(lat);
        int deg = (int) lat;
        double min = 60.0 * (lat - (int)lat);

        return String.format("%s%02d %02.3f", prefix, deg, min);
    }

    public static double dmToLat(String string) {
        Matcher matcher = LAT_RE.matcher(string);
        if (matcher.matches()) {
            String prefix = matcher.group(1);
            int deg = Integer.parseInt(matcher.group(2));
            double min = Double.parseDouble(matcher.group(3));
            return (prefix.equalsIgnoreCase("N") ? 1 : -1) * (deg + (min / 60.0));
        } else {
            throw new IllegalArgumentException("Bad latitude format");
        }
    }

    public static double dmToLon(String string) {
        Matcher matcher = LON_RE.matcher(string);
        if (matcher.matches()) {
            String prefix = matcher.group(1);
            int deg = Integer.parseInt(matcher.group(2));
            double min = Double.parseDouble(matcher.group(3));
            return (prefix.equalsIgnoreCase("E") ? 1 : -1) * (deg + (min / 60.0));
        } else {
            throw new IllegalArgumentException("Bad longitude format");
        }
    }
}
