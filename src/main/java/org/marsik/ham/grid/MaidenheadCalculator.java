package org.marsik.ham.grid;

import org.gavaghan.geodesy.GlobalCoordinates;

public class MaidenheadCalculator {
    public static double[] MAIDENHEAD_LON_PRECISION = {20.0, 2.0, 0.0833333333, 0.00833333333};
    public static double[] MAIDENHEAD_LAT_PRECISION = {10.0, 1.0, 0.0416666665, 0.00416666665};
    public static int MAIDENHEAD_MAX_PRECISION = 4;
    public static int[] MAIDENHEAD_CHARS = {'A', '0', 'a', '0'};

    /**
     * Get coordinates as a Maidenhead locator string.
     * http://home.comcast.net/~lespeters/PROJECTS/How%20to%20calculate%20your%208-digit%20grid%20square.pdf
     */
    public static String toMaidenhead(GlobalCoordinates coordinates, int precision)
    {
        StringBuffer locator = new StringBuffer();

        // convert to all-positive coordinates
        // 0 is South pole and the antemeridian (opposite Greenwich)
        double locatorLatitude = coordinates.getLatitude() + 90;
        double locatorLongitude = coordinates.getLongitude() + 180;

        for (int i = 0; i<precision; i++) {
            int squareLatitude = (int)(locatorLatitude / MAIDENHEAD_LAT_PRECISION[i]);
            int squareLongitude = (int)(locatorLongitude / MAIDENHEAD_LON_PRECISION[i]);

            locatorLatitude = locatorLatitude - MAIDENHEAD_LAT_PRECISION[i]*squareLatitude;
            locatorLongitude = locatorLongitude - MAIDENHEAD_LON_PRECISION[i]*squareLongitude;

            locator.append((char)(squareLongitude + MAIDENHEAD_CHARS[i]));
            locator.append((char)(squareLatitude + MAIDENHEAD_CHARS[i]));
        }


        return locator.toString();
    }

    public static String toMaidenhead(GlobalCoordinates coordinates)
    {
        return toMaidenhead(coordinates, 3);
    }

    static public GlobalCoordinates fromMaidenhead(final String locator)
    {
        int precision = locator.length() / 2;
        if (precision > 4) precision = 4;

        double latitude = 0.0;
        double longitude = 0.0;

        for (int i = 0; i<precision; i++) {
            char lon = locator.charAt(i*2);
            char lat = locator.charAt(i*2 + 1);

            longitude += MAIDENHEAD_LON_PRECISION[i] * ((int)lon - MAIDENHEAD_CHARS[i]);
            latitude += MAIDENHEAD_LAT_PRECISION[i] * ((int)lat - MAIDENHEAD_CHARS[i]);
        }

        return new GlobalCoordinates(latitude - 90.0, longitude - 180.0);
    }
}
