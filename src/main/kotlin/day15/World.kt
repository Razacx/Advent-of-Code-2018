package day15

import day06.Coordinates
import day11.Grid2D
import day11.height
import day11.width
import java.lang.IllegalArgumentException

class World(val grid: Grid2D<TileType>) {

    val entities: MutableList<Entity> = mutableListOf()

    fun render(): List<String> {
        val lines = mutableListOf<String>()

        for (x in 0..grid.width()) {
            var line = ""
            for (y in 0..grid.height()) {

                val position = Coordinates(x, y)
                val entity = entities.find { it.position == position }

                if(entity != null) {
                    line += when(entity) {
                        is Goblin -> "G"
                        is Elf -> "E"
                        else -> throw IllegalArgumentException("Unknown Entity")
                    }
                } else {
                    line += when(grid[x][y]) {
                        TileType.Wall -> "#"
                        TileType.Floor -> "."
                        else -> throw IllegalArgumentException("Unknown TileType")
                    }
                }

            }
        }

        return lines
    }

}



