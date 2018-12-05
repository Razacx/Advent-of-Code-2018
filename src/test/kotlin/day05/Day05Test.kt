package day05

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day05Test {

    @Test
    fun test_react_doesFullReaction() {
        assertEquals("ac", "aBcCbc".react())
    }

    @Test
    fun test_reactionCycle() {
        assertEquals("", "aA".reactionCycle())
        assertEquals("AA", "AA".reactionCycle())
        assertEquals("A", "AAa".reactionCycle())
        assertEquals("aa", "aa".reactionCycle())
        assertEquals("aA", "aBbA".reactionCycle())
        assertEquals("aBAb", "aBAb".reactionCycle())
        assertEquals("aBbc", "aBcCbc".reactionCycle())
        assertEquals("c", "cCc".reactionCycle())
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