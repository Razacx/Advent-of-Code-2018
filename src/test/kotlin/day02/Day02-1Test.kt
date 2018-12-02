package day02

import org.junit.Test
import kotlin.test.assertEquals

class Day02_1Test {

    @Test
    fun test_getLetterCount() {
        assertEquals(mapOf<String, Long>(Pair("a", 1), Pair("b", 1), Pair("c", 1), Pair("d", 1), Pair("e", 1), Pair("f", 1)), getLetterCount("abcdef"))
        assertEquals(mapOf<String, Long>(Pair("a", 2), Pair("b", 3), Pair("c", 1)), getLetterCount("bababc"))
        assertEquals(mapOf<String, Long>(Pair("a", 1), Pair("b", 2), Pair("c", 1), Pair("d", 1), Pair("e", 1)), getLetterCount("abbcde"))
        assertEquals(mapOf<String, Long>(Pair("a", 1), Pair("b", 1), Pair("c", 3), Pair("d", 1)), getLetterCount("abcccd"))
        assertEquals(mapOf<String, Long>(Pair("a", 2), Pair("b", 1), Pair("c", 1), Pair("d", 2)), getLetterCount("aabcdd"))
        assertEquals(mapOf<String, Long>(Pair("a", 1), Pair("b", 1), Pair("c", 1), Pair("d", 1), Pair("e", 2)), getLetterCount("abcdee"))
        assertEquals(mapOf<String, Long>(Pair("a", 3), Pair("b", 3)), getLetterCount("ababab"))
    }

    @Test
    fun test_containsLetterThatIsPresentNTimes() {
        assertEquals(true, containsLetterThatIsPresentNTimes("aab", 2))
        assertEquals(true, containsLetterThatIsPresentNTimes("aabbb", 3))
        assertEquals(false, containsLetterThatIsPresentNTimes("aabbb", 1))
    }

    @Test
    fun test_getBoxIdsChecksum() {
        assertEquals(12, getBoxIdsChecksum(listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")))
    }

}