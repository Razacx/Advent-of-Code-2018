package day15

import day06.Bounds
import day06.Coordinates
import day11.Grid2D
import day11.createGrid2D
import day11.height
import day11.width
import day13.model.Direction
import day13.model.plus
import java.util.*

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

//    if(predicate(from)) return null // Small optimization

    val shortestPath = startFindPath(collisionGrid, from, predicate) ?: return null

    return if (shortestPath.size > 1) {
        shortestPath[1]
    } else {
        shortestPath.first()
    }

}

fun startFindPath(collisionGrid: Grid2D<CollisionType>, from: Coordinates, predicate: (Coordinates) -> Boolean): List<Coordinates>? {

    var possibleEndTiles = getPossibleEndTiles(collisionGrid, predicate)
    if(predicate(from)) possibleEndTiles += listOf(from)

    // Find all possible paths to 'a' end tile
    val paths = mutableListOf<List<Coordinates>?>()
    for (tile in possibleEndTiles) {
        paths.add(findPath(collisionGrid, from, tile))
    }


    // Remove unresolved paths
    val resolvedPaths = paths.filter { it != null }.map { it!! }

    // If no paths left, return
    if (resolvedPaths.isEmpty()) {
        return null
    }

    // Get shortest path(s)
    val shortestPathsGroup = resolvedPaths.groupBy { it.size }.minBy { it.key }!!

    // If multiple paths remain, sort them by 'reading order of end position', then 'reading order of next step' and return the first one
    val shortestPath = shortestPathsGroup.value
            .sortedWith(compareBy(
                    { it.last().y },
                    { it.last().x },
                    { it[1].y },
                    { it[1].x }
            )).first()

    return shortestPath

}

//Find all tiles that match predicate
private fun getPossibleEndTiles(collisionGrid: Grid2D<CollisionType>, predicate: (Coordinates) -> Boolean): List<Coordinates> {
    val possibleEndTiles = mutableListOf<Coordinates>()
    for (x in 0 until collisionGrid.width()) {
        for (y in 0 until collisionGrid.width()) {
            val position = Coordinates(x, y)
            if (collisionGrid[x][y] == CollisionType.Walkable && predicate(position)) {
                possibleEndTiles.add(position)
            }
        }
    }
    return possibleEndTiles
}

fun findPath(collisionGrid: Grid2D<CollisionType>, from: Coordinates, end: Coordinates): List<Coordinates>? {

    // First param is visited node, second is previous node
    val visitedFrom = mutableMapOf<Coordinates, Coordinates>()

    val visitNextQueue = LinkedList<Coordinates>(listOf(from))

    while (!visitNextQueue.isEmpty()) {

        val tile = visitNextQueue.removeAt(0)

        if (tile == end) {
            return buildMapFromVisitedMap(visitedFrom, end)
        }

        val tileNeighbours = getNeighbours(tile)
        for (neighbour in tileNeighbours) {
            if (collisionGrid[neighbour.x][neighbour.y] == CollisionType.Walkable && !visitedFrom.containsKey(neighbour)) {
                visitedFrom[neighbour] = tile
                visitNextQueue.add(neighbour)
            }
        }

    }

    // No path found
    return null

}

fun buildMapFromVisitedMap(visitedFrom: Map<Coordinates, Coordinates>, end: Coordinates): List<Coordinates> {

    val reversedPath = mutableListOf(end)

    var currentPosition = end
    while (visitedFrom.containsKey(currentPosition)) {
        val from = visitedFrom[currentPosition]!!
        reversedPath.add(from)
        currentPosition = from
    }

    return reversedPath.reversed()

}

// The order here is very important...
fun getNeighbours(coordinates: Coordinates): List<Coordinates> {
    return listOf(
            coordinates + Direction.North.vector,
            coordinates + Direction.West.vector,
            coordinates + Direction.East.vector,
            coordinates + Direction.South.vector
    )
}

fun Bounds.isInBounds(coordinates: Coordinates): Boolean {
    // Not sure how good performance is with a in range check
    return coordinates.x in minX..maxX && coordinates.y in minY..maxY
}

fun <K> Grid2D<K>.bounds(): Bounds {
    return Bounds.invoke(0, 0, width() - 1, height() - 1)
}
