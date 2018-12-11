package day11

import day06.Coordinates
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals

class Day11KtTest {

    @Test
    fun test_calculatePowerLevel() {
        assertEquals(4, calculatePowerLevel(3, 5, 8))
        assertEquals(-5, calculatePowerLevel(122, 79, 57))
        assertEquals(0, calculatePowerLevel(217, 196, 39))
        assertEquals(4, calculatePowerLevel(101, 153, 71))
    }

    @Test
    fun test_findMaxPowerCoordinatesForGivenRegion() {
        assertEquals(Pair(Coordinates(32, 44), 29), findMaxPowerCoordinatesForGivenRegion(createGrid(18, 300), 3))
        assertEquals(Pair(Coordinates(20, 60), 30), findMaxPowerCoordinatesForGivenRegion(createGrid(42, 300), 3))
    }

    //This test takes quite long, don't run this unless you want to
    @Test
    @Ignore
    fun test_findMaxPowerCoordinatesForAnyRegion() {
        assertEquals(Triple(16, Coordinates(89, 268), 113), findMaxPowerCoordinatesForAnyRegion(createGrid(18, 300)))
        assertEquals(Triple(12, Coordinates(231, 250), 119), findMaxPowerCoordinatesForAnyRegion(createGrid(42, 300)))
    }
}