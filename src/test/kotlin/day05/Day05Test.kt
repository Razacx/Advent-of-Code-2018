package day05

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day05Test {

    @Test
    fun test_react_givesFinalPolymer() {
        assertEquals("", "aA".react())
        assertEquals("AA", "AA".react())
        assertEquals("A", "AAa".react())
        assertEquals("aa", "aa".react())
        assertEquals("", "aBbA".react())
        assertEquals("aBAb", "aBAb".react())
        assertEquals("ac", "aBcCbc".react())
        assertEquals("c", "cCc".react())
    }

    @Test
    fun test_isPolarPair() {
        assertTrue("aA".isPolarPair())
        assertTrue("Aa".isPolarPair())

        assertFalse("aa".isPolarPair())
        assertFalse("AA".isPolarPair())

        assertFalse("Ab".isPolarPair())
        assertFalse("AB".isPolarPair())
        assertFalse("aB".isPolarPair())
        assertFalse("ab".isPolarPair())
    }
}