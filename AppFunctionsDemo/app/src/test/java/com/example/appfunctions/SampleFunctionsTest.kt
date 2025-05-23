package com.example.appfunctions

import org.junit.Test
import org.junit.Assert.*

class SampleFunctionsTest {

    @Test
    fun testConcatenateStrings() {
        // Basic case
        assertEquals("Test String", concatenateStrings("Test ", "String"))

        // Empty strings
        assertEquals("", concatenateStrings("", ""))

        // One empty string
        assertEquals("Hello", concatenateStrings("Hello", ""))
        assertEquals("World", concatenateStrings("", "World"))

        // Strings with numbers
        assertEquals("123456", concatenateStrings("123", "456"))

        // Strings with special characters
        assertEquals("!@#$%^", concatenateStrings("!@#", "$%^"))

        // Strings with leading/trailing spaces (concatenation should preserve them)
        assertEquals("  leading", concatenateStrings("  ", "leading"))
        assertEquals("trailing  ", concatenateStrings("trailing", "  "))
        assertEquals("  both  ", concatenateStrings("  ", "both  "))

        // Longer strings
        val longStr1 = "This is a fairly long string to test concatenation."
        val longStr2 = " And this is another one to append."
        val expectedLongStr = "This is a fairly long string to test concatenation. And this is another one to append."
        assertEquals(expectedLongStr, concatenateStrings(longStr1, longStr2))
    }
}
