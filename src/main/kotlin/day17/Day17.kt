package day17

import day06.Bounds
import day06.Coordinates
import day17.SoilType.*
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

fun main(vararg args: String) {

    val veins = parseVeins(
            BufferedReader(FileReader("input/day17/veins.txt"))
                    .lines()
                    .collect(Collectors.toList())
    )

    val grid = createVeinsGrid(veins)
    grid[500][0] = WATER_FLOWING

//    println(
//            grid.grid.render {
//                when (it) {
//                    CLAY -> "\u001b[37m#"
//                    SAND -> "\u001b[30m."
//                    WATER_FLOWING -> "\u001b[34;1m|"
//                    WATER_STILL -> "\u001b[34;1m~"
//                }
//            }
//    )

    loop(grid)

}

fun loop(grid: TileUpdateOffsetGrid2D<SoilType>) {
    while (!grid.tilesToUpdate.isEmpty()) {
        tick(grid)
    }
}

fun tick(grid: TileUpdateOffsetGrid2D<SoilType>) {
    for(tile in grid.getAndResetTilesToUpdate()) {

        // Apply local rules

    }
}

fun createVeinsGrid(veins: List<Coordinates>): TileUpdateOffsetGrid2D<SoilType> {
    val minX = veins.minBy { it.x }!!.x
    val maxX = veins.maxBy { it.x }!!.x
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