package day17

import day06.Bounds
import day06.Coordinates
import day17.SoilType.CLAY
import day17.SoilType.SAND
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

    println(
            grid.grid.render {
                when (it) {
                    CLAY -> "\u001b[37m#"
                    SAND -> "\u001b[30m."
                }
            }
    )

}

fun createVeinsGrid(veins: List<Coordinates>): OffsetGrid2D<SoilType> {
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
    val grid = createOffsetGrid2D(gridBounds, SAND)

    for (x in grid.x until grid.x + grid.width()) {
        for (y in grid.y until grid.y + grid.height()) {
            val coordinates = Coordinates(x, y)
            if (veins.contains(coordinates)) {
                grid[x][y] = CLAY
            }
        }
    }

    return grid
}

enum class SoilType {

    CLAY,
    SAND

}