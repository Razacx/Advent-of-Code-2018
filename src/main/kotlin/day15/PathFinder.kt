package day15

import day06.Coordinates
import day11.Grid2D

fun findNextStep(collisionGrid: Grid2D<CollisionType>, from: Coordinates, predicate: (Coordinates) -> Boolean): Coordinates? {
    val path = findPath(collisionGrid, from, predicate)
    return if (path.isEmpty()) {
        null
    } else {
        path.first()
    }
}

fun findPath(collisionGrid: Grid2D<CollisionType>, from: Coordinates, predicate: (Coordinates) -> Boolean): List<Coordinates> {

    // Do a breadth first search for tiles where predicate matches. If multiple equally sized paths are found, return the one with end-destination at lowest y-x coordinate
    return listOf()

    // Might require a findNeighbours function

}