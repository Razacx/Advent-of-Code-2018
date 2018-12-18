package day17

import day06.Coordinates
import org.junit.Assert.assertEquals
import org.junit.Test

class ParsingKtTest {

    @Test
    fun parseCoordinateRange() {
        assertEquals(listOf(
                Coordinates(1, 5),
                Coordinates(1, 6),
                Coordinates(1, 7), Coordinates(1, 8)
        ), parseCoordinateRange("x=1, y=5..8"))
        assertEquals(listOf(
                Coordinates(3, 2),
                Coordinates(4, 2),
                Coordinates(5, 2)
        ), parseCoordinateRange("y=2, x=3..5"))
    }

}