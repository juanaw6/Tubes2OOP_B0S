package app.utils;

/**
 * Utility class for parsing locations in the format "A01", "B02", etc.,
 * and converting integer indices back into location strings.
 */
public class LocationParser {

    /**
     * Parses the location string for the active deck.
     *
     * @param location the location string in the format "A01", "B02", etc.
     * @return the parsed integer index.
     */
    public static int parseForActiveDeck(String location) {
        int ROW_ELEMENT_COUNT = 6;
        return parseLocation(location, ROW_ELEMENT_COUNT);
    }

    /**
     * Parses the location string for the field.
     *
     * @param location the location string in the format "A01", "B02", etc.
     * @return the parsed integer index.
     */
    public static int parseForField(String location) {
        int ROW_ELEMENT_COUNT = 5;
        return parseLocation(location, ROW_ELEMENT_COUNT);
    }

    /**
     * Converts an integer index to a location string for the active deck.
     *
     * @param index the integer index.
     * @return the location string in the format "A01", "B02", etc.
     */
    public static String convertForActiveDeck(int index) {
        int ROW_ELEMENT_COUNT = 6;
        return convertLocation(index, ROW_ELEMENT_COUNT);
    }

    /**
     * Converts an integer index to a location string for the field.
     *
     * @param index the integer index.
     * @return the location string in the format "A01", "B02", etc.
     */
    public static String convertForField(int index) {
        int ROW_ELEMENT_COUNT = 5;
        return convertLocation(index, ROW_ELEMENT_COUNT);
    }

    /**
     * Parses the location string into an integer index.
     *
     * @param location the location string.
     * @param rowElementCount the number of elements per row.
     * @return the parsed integer index.
     */
    private static int parseLocation(String location, int rowElementCount) {
        if (location == null || location.length() < 2) {
            throw new IllegalArgumentException("Invalid location format");
        }

        char columnChar = location.charAt(0);
        int column = columnChar - 'A';
        int row = Integer.parseInt(location.substring(1)) - 1;

        return row * rowElementCount + column;
    }

    /**
     * Converts an integer index to a location string.
     *
     * @param index the integer index.
     * @param rowElementCount the number of elements per row.
     * @return the location string.
     */
    private static String convertLocation(int index, int rowElementCount) {
        int row = index / rowElementCount;
        int column = index % rowElementCount;

        char columnChar = (char) ('A' + column);
        int rowNum = row + 1;

        return String.format("%c%02d", columnChar, rowNum);
    }
}