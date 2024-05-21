package tubes2oop_b0s.utils;

public class LocationParser {
    public static int parseForActiveDeck(String location) {
        int ROW_ELEMENT_COUNT = 6;
        return parseLocation(location, ROW_ELEMENT_COUNT);
    }

    public static int parseForField(String location) {
        int ROW_ELEMENT_COUNT = 5;
        return parseLocation(location, ROW_ELEMENT_COUNT);
    }

    private static int parseLocation(String location, int rowElementCount) {
        if (location == null || location.length() < 3) {
            throw new IllegalArgumentException("Invalid location format");
        }

        char columnChar = location.charAt(0);
        int column = columnChar - 'A';
        int row = Integer.parseInt(location.substring(1)) - 1;

        return row * rowElementCount + column;
    }
}
