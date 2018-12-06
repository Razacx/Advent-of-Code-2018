package day06

class Grid(private val bounds: Bounds) {

    val grid = Array(bounds.width()) { Array(bounds.height()) { -1 } }

    fun fillWithClosestCoordinatesId(identifiedCoordinates: List<IdentifiedCoordinates>) {
        for (x in 0 until bounds.width()) {
            for (y in 0 until bounds.height()) {
                grid[x][y] = findClosestIdentifiedCoordinatesId(identifiedCoordinates, Coordinates(x + bounds.minX, y + bounds.minY))
            }
        }
    }

    fun fillWithCumulativeDistanceToAllCoordinates(coordinates: List<Coordinates>) {
        for (x in 0 until bounds.width()) {
            for (y in 0 until bounds.height()) {
                val currentCoords = Coordinates(x + bounds.minX, y + bounds.minY)
                val sumOfDistances = coordinates.sumBy { it.manhattanDistance(currentCoords) }
                grid[x][y] = sumOfDistances
            }
        }
    }

    fun getIdsAtEdge(): List<Int> {
        val topBottom = grid[0] + grid[grid.size - 1]
        val leftRight = (0 until grid.size).flatMap {
            listOf(grid[it][0], grid[it][grid[it].size - 1])
        }
        return setOf(*topBottom + leftRight.toTypedArray()).toList()
                .filter { it != -1 }
    }

    fun getAreaForCellsMatchingCondition(condition: (Int) -> Boolean): Int {
        return grid
                .flatMap { it.toList() }
                .filter(condition)
                .count()
    }

}