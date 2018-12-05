package day05

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day05Test {

    @Test
    fun test_reactPolymer_removesPolarOppositePairs() {
        assertEquals("", react("aA"))
        assertEquals("AA", react("AA"))
        assertEquals("A", react("AAa"))
        assertEquals("aa", react("aa"))
        assertEquals("aA", react("aBbA"))
        assertEquals("aBAb", react("aBAb"))
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