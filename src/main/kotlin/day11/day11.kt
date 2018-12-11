package day11

import day06.Bounds
import day06.Coordinates


fun main(vararg args: String) {
    val serialNumber = 9221
    val grid = createGrid(serialNumber, 300)

    val (coordinates, power) = findMaxPowerCoordinatesForGivenRegion(grid, 3)

    println("Max power region coordinates: ")
    println("X: ${coordinates.x + 1}, Y: ${coordinates.y + 1} (Power: $power)")
    println()

    val (regionSize, coordinates2, power2) = findMaxPowerCoordinatesForAnyRegion(grid)

    println("Max power region for any area: ")
    println("X: ${coordinates2.x + 1}, Y: ${coordinates2.y + 1}, R: $regionSize (Power: $power2)")

}

fun createGrid(serialNumber: Int, gridSide: Int): Grid2D<Int> {
    val grid = createGrid2D(gridSide, gridSide, 0)

    for (x in 0 until grid.width()) {
        for (y in 0 until grid.height()) {
            grid[x][y] = calculatePowerLevel(x + 1, y + 1, serialNumber)
        }
    }

    return grid
}

fun findMaxPowerCoordinatesForAnyRegion(grid: Grid2D<Int>): Triple<Int, Coordinates, Int> {
    val maxPowerCoordinates = mutableListOf<Triple<Int, Coordinates, Int>>()

    for(i in 1 .. grid.width()) {
        val (coordinates, power) = findMaxPowerCoordinatesForGivenRegion(grid, i)
        maxPowerCoordinates.add(Triple(i, coordinates, power))
    }

    return maxPowerCoordinates.maxBy { it.third }!!
}

fun findMaxPowerCoordinatesForGivenRegion(grid: Grid2D<Int>, regionSize: Int): Pair<Coordinates, Int> {
    val powerMap = mutableMapOf<Coordinates, Int>()
    for (x in 0 .. grid.width() - regionSize) {
        for (y in 0 .. grid.height() - regionSize) {
            val powerSum = grid.sumCells(Bounds.fromWidthHeight(x, y, regionSize, regionSize))
            powerMap[Coordinates(x, y)] = powerSum
        }
    }

    return powerMap.maxBy { it.value }!!.toPair()
}

fun calculatePowerLevel(x: Int, y: Int, serialNumber: Int): Int {
    val rackId = x + 10
    var power = rackId * y

    power += serialNumber
    power *= rackId
    if (power < 100) {
        power = 0
    } else {
        val powerChars = power.toString().toCharArray()
        power = powerChars[powerChars.size - 3].toString().toInt()
    }
    power -= 5

    return power
}