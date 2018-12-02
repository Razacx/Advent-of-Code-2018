package day02

import org.junit.Test
import kotlin.test.assertEquals

class Day02_2Test {

    @Test
    fun test_getCommonLetters() {
        assertEquals("fgij", getCommonLetters("fghij", "fguij"))
        assertEquals("ace", getCommonLetters("abcde", "axcye"))
    }

    @Test
    fun test_getCorrectBoxesPair() {
        assertEquals(
                Pair("fghij", "fguij"),
                findCorrectBoxesPair(listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz"))
        )
    }

}