package day15

import day06.Coordinates
import day11.Grid2D
import day11.createGrid2D
import day11.height
import day11.width
import day15.CollisionType.NotWalkable
import day15.CollisionType.Walkable
import day15.TileType.Floor
import java.lang.IllegalArgumentException

class World(val grid: Grid2D<TileType>) {

    val entities: MutableList<Entity> = mutableListOf()

}



