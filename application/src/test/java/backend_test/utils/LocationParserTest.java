package backend_test.utils;

import app.utils.LocationParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationParserTest {

    @Test
    public void testParseForActiveDeck() {
        assertEquals(0, LocationParser.parseForActiveDeck("A01"));
        assertEquals(5, LocationParser.parseForActiveDeck("F01"));
        assertEquals(6, LocationParser.parseForActiveDeck("A02"));
    }

    @Test
    public void testParseForField() {
        assertEquals(0, LocationParser.parseForField("A01"));
        assertEquals(4, LocationParser.parseForField("E01"));
        assertEquals(5, LocationParser.parseForField("A02"));
    }

    @Test
    public void testConvertForActiveDeck() {
        assertEquals("A01", LocationParser.convertForActiveDeck(0));
        assertEquals("F01", LocationParser.convertForActiveDeck(5));
        assertEquals("A02", LocationParser.convertForActiveDeck(6));
    }

    @Test
    public void testConvertForField() {
        assertEquals("A01", LocationParser.convertForField(0));
        assertEquals("E01", LocationParser.convertForField(4));
        assertEquals("A02", LocationParser.convertForField(5));
    }

    @Test
    public void testParseLocationInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> LocationParser.parseForActiveDeck(null));
        assertThrows(IllegalArgumentException.class, () -> LocationParser.parseForActiveDeck(""));
        assertThrows(IllegalArgumentException.class, () -> LocationParser.parseForActiveDeck("A"));
        assertThrows(IllegalArgumentException.class, () -> LocationParser.parseForActiveDeck("Z99"));
    }

    @Test
    public void testConvertLocationInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> LocationParser.convertForActiveDeck(-1));
        assertThrows(IllegalArgumentException.class, () -> LocationParser.convertForField(-1));
    }
}