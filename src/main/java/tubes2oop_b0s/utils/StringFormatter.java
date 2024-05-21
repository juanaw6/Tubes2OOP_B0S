package tubes2oop_b0s.utils;

public class StringFormatter {

    /**
     * Converts a string to a more readable format:
     * - Underscores are replaced with spaces.
     * - Each word starts with a capital letter followed by lower case letters.
     * @param input The string to be formatted.
     * @return The formatted string.
     * @implNote Converts "ABC_DEF" to "Abc Def".
     */
    public static String formatString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("_");
        StringBuilder formattedString = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) continue;
            if (!formattedString.isEmpty()) {
                formattedString.append(" ");
            }
            formattedString.append(word.substring(0, 1).toUpperCase());
            if (word.length() > 1) {
                formattedString.append(word.substring(1).toLowerCase());
            }
        }

        return formattedString.toString().trim();
    }

    /**
     * Converts a formatted string back to an all-uppercase string with underscores.
     * - Spaces are replaced with underscores.
     * - All characters are converted to uppercase.
     * @param input The formatted string to be converted back.
     * @return The original uppercased underscored string.
     * @implNote Converts "Abc Def" back to "ABC_DEF".
     */
    public static String unformatString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.toUpperCase().replace(" ", "_");
    }
}
