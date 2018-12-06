package day06

import org.junit.Test
import kotlin.test.assertEquals

class CoordinatesKtTest {

    @Test
    fun test_coordinates_manhattanDistance() {
        kotlin.test.assertEquals(5, Coordinates(1, 2).manhattanDistance(Coordinates(3, 5)))
        kotlin.test.assertEquals(4, Coordinates(0, 0).manhattanDistance(Coordinates(2, 2)))
        kotlin.test.assertEquals(3, Coordinates(0, 0).manhattanDistance(Coordinates(-1, -2)))
        kotlin.test.assertEquals(2, Coordinates(0, 0).manhattanDistance(Coordinates(1, 1)))
        kotlin.test.assertEquals(0, Coordinates(0, 0).manhattanDistance(Coordinates(0, 0)))
    }

    @Test
    fun test_findClosestIdentifiedCoordinatesId() {
        assertEquals(
                2,
                findClosestIdentifiedCoordinatesId(
                        listOf(
                                IdentifiedCoordinates(1, Coordinates(60, 0)),
                                IdentifiedCoordinates(2, Coordinates(0, 0))
                        ),
                        Coordinates(5, 0)
                )
        )
        assertEquals(
                -1,
                findClosestIdentifiedCoordinatesId(
                        listOf(
                                IdentifiedCoordinates(1, Coordinates(1, 0)),
                                IdentifiedCoordinates(2, Coordinates(5, 0))
                        ),
                        Coordinates(3, 0)
                )
        )
    }

}