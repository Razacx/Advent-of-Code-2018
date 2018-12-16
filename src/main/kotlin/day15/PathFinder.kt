package day15

import day06.Bounds
import day06.Coordinates
import day11.Grid2D
import day11.createGrid2D
import day11.height
import day11.width
import day13.model.Direction
import day13.model.plus
import day15.CollisionType.Walkable

fun worldToCollisionGrid(grid: Grid2D<TileType>, entities: List<Entity>): Grid2D<CollisionType> {
    val collisionGrid = createGrid2D(grid.width(), grid.height(), CollisionType.NotWalkable)

    for (x in 0 until collisionGrid.width()) {
        for (y in 0 until collisionGrid.height()) {
            val coordinates = Coordinates(x, y)
            if (grid[x][y] == TileType.Floor && entities.find { it.position == coordinates } == null) {
                collisionGrid[x][y] = CollisionType.Walkable
            }
        }
    }

    return collisionGrid
}

fun findNextStep(collisionGrid: Grid2D<CollisionType>, from: Coordinates, predicate: (Coordinates) -> Boolean): Coordinates? {
    val path = findPath(collisionGrid, predicate, listOf(from))
    return if (path.isEmpty()) {
        null
    } else {
        path.first()
    }
}

// TODO Write test for this
// Do a breadth first search for tiles where predicate matches. If multiple equally sized paths are found, return the one with end-destination at lowest y-x coordinate
fun findPath(collisionGrid: Grid2D<CollisionType>, predicate: (Coordinates) -> Boolean, path: List<Coordinates>): List<Coordinates> {
    val currentCoordinates = path.last()

    // Return if we are currently on a tile that matches our rules
    if (predicate(currentCoordinates)) {
        // Might need to refine this if this is the first call (?)
        return path
    }

    val bounds = collisionGrid.bounds()
    val newWalkableNeighbors = getNeighbours(currentCoordinates)
            .filter { it !in path } // Remove tiles we've previously traversed
            .filter { bounds.isInBounds(it) } // Remove out of bounds neighbours
            .filter { collisionGrid[it.x][it.y] == Walkable } // Remove not walkable tiles

    // This path found nothing
    if(newWalkableNeighbors.isEmpty()) {
        return path
    }

    val paths = newWalkableNeighbors.map {
        // Recursively find path for each neighbour
        findPath(collisionGrid, predicate, path + listOf(it))
    }.sortedWith(Comparator { p1, p2 ->
        if (p1.size != p2.size) { // Get shortest path
            p1.size - p2.size
        } else { // Get first by reading order of final tile if duplicates
            ReadingOrderCoordinatesComparator().compare(p1.last(), p2.last())
        }
    })

    return paths.first()
}

fun getNeighbours(coordinates: Coordinates): List<Coordinates> {
    return listOf(
            coordinates + Direction.North.vector,
            coordinates + Direction.South.vector,
            coordinates + Direction.West.vector,
            coordinates + Direction.East.vector
    )
}

fun Bounds.isInBounds(coordinates: Coordinates): Boolean {
    // Not sure how good performance is with a in range check
    return coordinates.x in minX..maxX && coordinates.y in minY..maxY
}

fun <K> Grid2D<K>.bounds(): Bounds {
    return Bounds.invoke(0, 0, width() - 1, height() - 1)
}
