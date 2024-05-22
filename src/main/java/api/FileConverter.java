package api;

/**
 * Interface for file converters that can convert game state files between different formats
 * and clean up specific files within a given directory.
 */
public interface FileConverter {

    /**
     * Converts game state files from the supported extension format to TXT format.
     *
     * The TXT format for gamestate.txt:
     * <pre>
     * turn
     * number_of_items_in_store
     * item_name1 item_quantity1
     * item_name2 item_quantity2
     * ...
     * </pre>
     *
     * The TXT format for player1.txt and player2.txt:
     * <pre>
     * gulden
     * deck
     * number_of_active_deck_cards
     * location1 card_type1
     * location2 card_type2
     * ...
     * number_of_field_cards
     * location1 card_type1 age_or_weight1 number_of_effects1 effect1 effect2 ...
     * location2 card_type2 age_or_weight2 number_of_effects2 effect1 effect2 ...
     * ...
     * </pre>
     *
     * @param folderPath the directory containing the files to be converted.
     */
    void convertToTxt(String folderPath);

    /**
     * Converts game state files from TXT format to the supported extension format.
     *
     * The TXT format for gamestate.txt:
     * <pre>
     * turn
     * number_of_items_in_store
     * item_name1 item_quantity1
     * item_name2 item_quantity2
     * ...
     * </pre>
     *
     * The TXT format for player1.txt and player2.txt:
     * <pre>
     * gulden
     * deck
     * number_of_active_deck_cards
     * location1 card_type1
     * location2 card_type2
     * ...
     * number_of_field_cards
     * location1 card_type1 age_or_weight1 number_of_effects1 effect1 effect2 ...
     * location2 card_type2 age_or_weight2 number_of_effects2 effect1 effect2 ...
     * ...
     * </pre>
     *
     * @param folderPath the directory containing the files to be converted.
     */
    void convertFromTxt(String folderPath);

    /**
     * Cleans up game state files with the supported extension in the specified directory.
     * This will delete `gamestate`, `player1`, and `player2` files with the supported extension.
     *
     * @param folderPath the directory containing the files to be cleaned.
     */
    void cleanSupportedExtensionFiles(String folderPath);

    /**
     * Cleans up game state files in TXT format in the specified directory.
     * This will delete `gamestate`, `player1`, and `player2` files with the TXT extension.
     *
     * @param folderPath the directory containing the files to be cleaned.
     */
    void cleanTxtFiles(String folderPath);

    /**
     * Returns the supported file extension used by the converter (e.g., "yaml", "json").
     *
     * @return the supported file extension as a string.
     */
    String supportedExtension();
}