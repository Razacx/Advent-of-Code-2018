package day06

import org.junit.Test

class ParsingKtTest {

    @Test
    fun test_parseCoordinates() {
        kotlin.test.assertEquals(Coordinates(353, 77), parseCoordinates("353, 77"))
        kotlin.test.assertEquals(Coordinates(81, 328), parseCoordinates("81, 328"))
        kotlin.test.assertEquals(Coordinates(136, 316), parseCoordinates("136, 316"))
        kotlin.test.assertEquals(Coordinates(136, 316), parseCoordinates("136, 316   "))
    }

}