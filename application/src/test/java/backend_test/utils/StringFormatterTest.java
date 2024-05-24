package backend_test.utils;

import app.utils.StringFormatter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringFormatterTest {

    @Test
    public void testFormatString() {
        assertEquals("Abc Def", StringFormatter.formatString("ABC_DEF"));
        assertEquals("Hello World", StringFormatter.formatString("HELLO_WORLD"));
        assertEquals("Java Programming", StringFormatter.formatString("JAVA_PROGRAMMING"));
        assertEquals("Java", StringFormatter.formatString("JAVA"));
    }

    @Test
    public void testUnformatString() {
        assertEquals("ABC_DEF", StringFormatter.unformatString("Abc Def"));
        assertEquals("HELLO_WORLD", StringFormatter.unformatString("Hello World"));
        assertEquals("JAVA_PROGRAMMING", StringFormatter.unformatString("Java Programming"));
        assertEquals("JAVA", StringFormatter.unformatString("Java"));
    }

    @Test
    public void testFormatStringAlreadyFormatted() {
        assertEquals("Abc Def", StringFormatter.formatString("Abc Def"));
        assertEquals("Hello World", StringFormatter.formatString("Hello World"));
    }

    @Test
    public void testUnformatStringAlreadyFormatted() {
        assertEquals("ABC_DEF", StringFormatter.unformatString("ABC_DEF"));
        assertEquals("HELLO_WORLD", StringFormatter.unformatString("HELLO_WORLD"));
    }

    @Test
    public void testFormatStringEmptyOrNull() {
        assertEquals("", StringFormatter.formatString(""));
        assertNull(StringFormatter.formatString(null));
    }

    @Test
    public void testUnformatStringEmptyOrNull() {
        assertEquals("", StringFormatter.unformatString(""));
        assertNull(StringFormatter.unformatString(null));
    }

    @Test
    public void testFormatStringSingleWord() {
        assertEquals("Java", StringFormatter.formatString("JAVA"));
        assertEquals("Programming", StringFormatter.formatString("PROGRAMMING"));
    }

    @Test
    public void testUnformatStringSingleWord() {
        assertEquals("JAVA", StringFormatter.unformatString("Java"));
        assertEquals("PROGRAMMING", StringFormatter.unformatString("Programming"));
    }

    @Test
    public void testFormatStringWithMultipleUnderscores() {
        assertEquals("A B C D E", StringFormatter.formatString("A_B_C_D_E"));
        assertEquals("Java Programming Language", StringFormatter.formatString("JAVA_PROGRAMMING_LANGUAGE"));
    }

    @Test
    public void testUnformatStringWithMultipleSpaces() {
        assertEquals("A_B_C_D_E", StringFormatter.unformatString("A B C D E"));
        assertEquals("JAVA_PROGRAMMING_LANGUAGE", StringFormatter.unformatString("Java Programming Language"));
    }
}