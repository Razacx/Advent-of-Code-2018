package day17

import day06.Bounds
import day06.Coordinates
import day13.model.Direction
import day13.model.plus
import day17.SoilType.*
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

fun main(vararg args: String) {

    val veins = parseVeins(
            BufferedReader(FileReader("input/day17/veins-test.txt"))
                    .lines()
                    .collect(Collectors.toList())
    )

    val grid = createVeinsGrid(veins)
    grid[500][0] = WATER_FLOWING

    try {
        loop(grid)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    println(
            grid.grid.render {
                when (it) {
                    CLAY -> "\u001b[37m#"
                    SAND -> "\u001b[30m."
                    WATER_FLOWING -> "\u001b[34;1m|"
                    WATER_STILL -> "\u001b[34;1m~"
                }
            }
    )


}

fun loop(grid: TileUpdateOffsetGrid2D<SoilType>) {
    for (i in 0 until 100) {
        tick(grid)
        println()
        println("Iteration $i")
        println(
                grid.grid.render {
                    when (it) {
                        CLAY -> "\u001b[37m#"
                        SAND -> "\u001b[30m."
                        WATER_FLOWING -> "\u001b[34;1m|"
                        WATER_STILL -> "\u001b[34;1m~"
                    }
                }
        )
    }

//    while (!grid.tilesToUpdate.isEmpty()) {
//        tick(grid)
//    }
}

fun tick(grid: TileUpdateOffsetGrid2D<SoilType>) {
    for (tile in grid.getAndResetTilesToUpdate()) {

        val northNeighbour = tile + Direction.North.vector
        val southNeighbour = tile + Direction.South.vector
        val leftNeighbour = tile + Direction.West.vector
        val rightNeighbour = tile + Direction.East.vector

        val southLeftNeighbour = tile + Direction.South.vector + Direction.West.vector
        val southRightNeighbour = tile + Direction.South.vector + Direction.East.vector

        if(grid[tile] == SAND) {
            if(grid.bounds().contains(northNeighbour) && grid[northNeighbour] == WATER_FLOWING) {
                grid[tile.x][tile.y] = WATER_FLOWING
            }
        }

    }
}

fun createVeinsGrid(veins: List<Coordinates>): TileUpdateOffsetGrid2D<SoilType> {
    val minX = veins.minBy { it.x }!!.x - 1
    val maxX = veins.maxBy { it.x }!!.x + 1
    val minY = 0
    val maxY = veins.maxBy { it.y }!!.y

    val gridBounds = Bounds(
            if (minX > 500) 500 else minX,
            minY,
            if (maxX < 500) 500 else maxX,
            maxY
    )
    val grid = createTileUpdateOffsetGrid2D(gridBounds, SAND)

    for (x in grid.x until grid.x + grid.width()) {
        for (y in grid.y until grid.y + grid.height()) {
            val coordinates = Coordinates(x, y)
            if (veins.contains(coordinates)) {
                grid[x][y] = CLAY
            }
        }
    }
    grid.resetTilesToUpdate()

    return grid
}

enum class SoilType {

    CLAY,
    SAND,
    WATER_FLOWING,
    WATER_STILL

}