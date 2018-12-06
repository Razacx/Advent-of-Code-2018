package day06

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day06Test {

    @Test
    fun test_coordinates_manhattanDistance() {
        assertEquals(5, Coordinates(1, 2).manhattanDistance(Coordinates(3, 5)))
        assertEquals(4, Coordinates(0, 0).manhattanDistance(Coordinates(2, 2)))
        assertEquals(3, Coordinates(0, 0).manhattanDistance(Coordinates(-1, -2)))
        assertEquals(2, Coordinates(0, 0).manhattanDistance(Coordinates(1, 1)))
        assertEquals(0, Coordinates(0, 0).manhattanDistance(Coordinates(0, 0)))
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_bounds_throwsException_ifMinXLargerThanMaxX() {
        Bounds(10, 0, 0, 10)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_bounds_throwsException_ifMinYLargerThanMaxY() {
        Bounds(0, 10, 10, 0)
    }

    @Test
    fun test_bounds_valid() {
        Bounds(0, 0, 10, 10)
        Bounds(0, 0, 0, 0)
    }

    @Test
    fun test_bounds_width_height() {
        val bounds = Bounds(0, 0, 10, 5)
        assertEquals(11, bounds.width())
        assertEquals(6, bounds.height())
    }

    @Test
    fun test_bounds_fromCoordinates() {
        val bounds = Bounds.fromCoordinates(listOf(
                Coordinates(-1, 0),
                Coordinates(0, -2),
                Coordinates(3, 0),
                Coordinates(0, 4)
        ))
        assertEquals(Bounds(-1, -2, 3, 4), bounds)
    }

    @Test
    fun test_parseCoordinates() {
        assertEquals(Coordinates(353, 77), parseCoordinates("353, 77"))
        assertEquals(Coordinates(81, 328), parseCoordinates("81, 328"))
        assertEquals(Coordinates(136, 316), parseCoordinates("136, 316"))
        assertEquals(Coordinates(136, 316), parseCoordinates("136, 316   "))
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

    @Test
    fun test_2DIntArray_transpose() {
        assertTrue(
                arrayOf(
                        arrayOf(1, 3, 5),
                        arrayOf(2, 4, 6)
                ) contentDeepEquals arrayOf(
                        arrayOf(1, 2),
                        arrayOf(3, 4),
                        arrayOf(5, 6)
                ).transpose()
        )
    }

    @Test
    fun test_grid_fillClosestToCoordinatesWithCoordinateId() {
        val coordinates = listOf(
                IdentifiedCoordinates(1, Coordinates(1, 1)),
                IdentifiedCoordinates(2, Coordinates(1, 6)),
                IdentifiedCoordinates(3, Coordinates(8, 3)),
                IdentifiedCoordinates(4, Coordinates(3, 4)),
                IdentifiedCoordinates(5, Coordinates(5, 5)),
                IdentifiedCoordinates(6, Coordinates(8, 9))
        )
        val bounds = Bounds.fromCoordinates(coordinates.map(IdentifiedCoordinates::coordinates))
        val grid = Grid(bounds)

        grid.fillClosestToCoordinatesWithCoordinateId(coordinates)

        val gridValues = grid.grid
        assertTrue {
            arrayOf(
                    arrayOf(1, 1, 1, 1, -1, 3, 3, 3),
                    arrayOf(1, 1, 4, 4, 5, 3, 3, 3),
                    arrayOf(1, 4, 4, 4, 5, 3, 3, 3),
                    arrayOf(-1, 4, 4, 4, 5, 5, 3, 3),
                    arrayOf(2, -1, 4, 5, 5, 5, 5, 3),
                    arrayOf(2, 2, -1, 5, 5, 5, 5, -1),
                    arrayOf(2, 2, -1, 5, 5, 5, 6, 6),
                    arrayOf(2, 2, -1, 5, 5, 6, 6, 6),
                    arrayOf(2, 2, -1, 6, 6, 6, 6, 6)
            ).transpose() contentDeepEquals gridValues
        }
    }

    @Test
    fun test_grid_getIdsAtEdge() {
        val coordinates = listOf(
                IdentifiedCoordinates(1, Coordinates(1, 1)),
                IdentifiedCoordinates(2, Coordinates(1, 6)),
                IdentifiedCoordinates(3, Coordinates(8, 3)),
                IdentifiedCoordinates(4, Coordinates(3, 4)),
                IdentifiedCoordinates(5, Coordinates(5, 5)),
                IdentifiedCoordinates(6, Coordinates(8, 9))
        )
        val bounds = Bounds.fromCoordinates(coordinates.map(IdentifiedCoordinates::coordinates))
        val grid = Grid(bounds)

        grid.fillClosestToCoordinatesWithCoordinateId(coordinates)

        assertEquals(
                listOf(1, 2, 3, 6),
                grid.getIdsAtEdge().sorted()
        )
    }

    @Test
    fun test_grid_getAreaOfId() {
        val coordinates = listOf(
                IdentifiedCoordinates(1, Coordinates(1, 1)),
                IdentifiedCoordinates(2, Coordinates(1, 6)),
                IdentifiedCoordinates(3, Coordinates(8, 3)),
                IdentifiedCoordinates(4, Coordinates(3, 4)),
                IdentifiedCoordinates(5, Coordinates(5, 5)),
                IdentifiedCoordinates(6, Coordinates(8, 9))
        )
        val bounds = Bounds.fromCoordinates(coordinates.map(IdentifiedCoordinates::coordinates))
        val grid = Grid(bounds)

        grid.fillClosestToCoordinatesWithCoordinateId(coordinates)

        assertEquals(9, grid.getAreaOfId(4))
        assertEquals(17, grid.getAreaOfId(5))
    }

    @Test
    fun test_grid_fillWithSumOfDistanceToAllCoordinates() {
        val coordinates = listOf(
                Coordinates(1, 1),
                Coordinates(4, 1),
                Coordinates(2, 2)
        )
        val bounds = Bounds(1, 1, 2, 2)
        val grid = Grid(bounds)

        grid.fillWithCumulativeDistanceToAllCoordinates(coordinates)

        assertTrue(
                arrayOf(
                        arrayOf(5, 4),
                        arrayOf(6, 5)
                ).transpose() contentDeepEquals grid.grid
        )
    }

    @Test
    fun test_grid_getAreaWithCumulativeDistanceSmallerThan() {
        val coordinates = listOf(
                Coordinates(1, 1),
                Coordinates(4, 1),
                Coordinates(2, 2)
        )
        val bounds = Bounds(1, 1, 2, 2)
        val grid = Grid(bounds)

        grid.fillWithCumulativeDistanceToAllCoordinates(coordinates)

        assertEquals(3, grid.getAreaWithCumulativeDistanceSmallerThan(6))
    }

}
