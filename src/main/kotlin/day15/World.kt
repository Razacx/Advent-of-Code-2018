package day15

import day06.Coordinates
import day11.Grid2D
import day11.createGrid2D
import day11.height
import day11.width
import day15.CollisionType.NotWalkable
import day15.CollisionType.Walkable
import day15.TileType.Floor

class World(val grid: Grid2D<TileType>, initialEntities: List<Entity>) {

    private val _entities: MutableList<Entity> = initialEntities.toMutableList()
    val entities get() = _entities

}

fun mapToCollisionGrid(grid: Grid2D<TileType>, entities: List<Entity>): Grid2D<CollisionType> {
    val collisionGrid = createGrid2D(grid.width(), grid.height(), NotWalkable)

    for (x in 0 until collisionGrid.width()) {
        for (y in 0 until collisionGrid.height()) {
            val coordinates = Coordinates(x, y)
            if (grid[x][y] == Floor && entities.find { it.position == coordinates } == null) {
                collisionGrid[x][y] = Walkable
            }
        }
    }

    return collisionGrid
}