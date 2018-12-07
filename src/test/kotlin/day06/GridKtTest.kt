package day06

import org.junit.Test

class GridKtTest {

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

        grid.fillWithClosestCoordinatesId(coordinates)

        val gridValues = grid.grid
        kotlin.test.assertTrue {
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

        grid.fillWithClosestCoordinatesId(coordinates)

        kotlin.test.assertEquals(
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

        grid.fillWithClosestCoordinatesId(coordinates)

        kotlin.test.assertEquals(9, grid.getAreaForCellsMatchingCondition { it == 4 })
        kotlin.test.assertEquals(17, grid.getAreaForCellsMatchingCondition { it == 5 })
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

        kotlin.test.assertTrue(
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

        kotlin.test.assertEquals(3, grid.getAreaForCellsMatchingCondition { it < 6 })
    }

}