package day06

import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

typealias _2DIntArray = Array<Array<Int>>

data class IdentifiedCoordinates(val id: Int, val coordinates: Coordinates)

data class Coordinates(val x: Int, val y: Int) {

    fun manhattanDistance(other: Coordinates): Int {
        return Math.abs(x - other.x) + Math.abs(y - other.y)
    }

}

data class Bounds private constructor(val minX: Int, val minY: Int, val maxX: Int, val maxY: Int) {

    companion object {
        operator fun invoke(minX: Int, minY: Int, maxX: Int, maxY: Int): Bounds {
            if (minX > maxX || minY > maxY) {
                throw IllegalArgumentException("Invalid bounds")
            }
            return Bounds(minX, minY, maxX, maxY)
        }

        fun fromCoordinates(coordinates: List<Coordinates>): Bounds {
            val minX = coordinates.minBy(Coordinates::x)!!.x
            val maxX = coordinates.maxBy(Coordinates::x)!!.x
            val minY = coordinates.minBy(Coordinates::y)!!.y
            val maxY = coordinates.maxBy(Coordinates::y)!!.y

            return invoke(minX, minY, maxX, maxY)
        }
    }

    fun width(): Int {
        return maxX - minX + 1
    }

    fun height(): Int {
        return maxY - minY + 1
    }

}

fun parseCoordinates(line: String): Coordinates {
    val result = """(\d*), (\d*)""".toRegex().matchEntire(line.trim())!!
    return Coordinates(
            result.groups[1]!!.value.toInt(),
            result.groups[2]!!.value.toInt()
    )
}

class Grid(private val bounds: Bounds) {

    val grid = Array(bounds.width()) { Array(bounds.height()) { -1 } }

    fun fillClosestToCoordinatesWithCoordinateId(identifiedCoordinates: List<IdentifiedCoordinates>) {
        for (x in 0 until bounds.width()) {
            for (y in 0 until bounds.height()) {
                grid[x][y] = findClosestIdentifiedCoordinatesId(identifiedCoordinates, Coordinates(x + bounds.minX, y + bounds.minY))
            }
        }
    }

    fun fillWithCumulativeDistanceToAllCoordinates(coordinates: List<Coordinates>) {
        for (x in 0 until bounds.width()) {
            for (y in 0 until bounds.height()) {
                val currentCoords = Coordinates(x + bounds.minX, y + bounds.minY)
                val sumOfDistances = coordinates.sumBy { it.manhattanDistance(currentCoords) }
                grid[x][y] = sumOfDistances
            }
        }
    }

    fun getAreaWithCumulativeDistanceSmallerThan(distance: Int): Int {
        return grid
                .flatMap { it.toList() }
                .filter { it < distance }
                .count()
    }

    fun getIdsAtEdge(): List<Int> {
        val topBottom = grid[0] + grid[grid.size - 1]
        val leftRight = (0 until grid.size).flatMap {
            listOf(grid[it][0], grid[it][grid[it].size - 1])
        }
        return setOf(*topBottom + leftRight.toTypedArray()).toList()
                .filter { it != -1 }
    }

    fun getAreaOfId(id: Int): Int {
        return grid
                .flatMap { it.toList() }
                .filter { it == id }
                .count()
    }

}

fun findClosestIdentifiedCoordinatesId(identifiedCoordinates: List<IdentifiedCoordinates>, coordinates: Coordinates): Int {
    val closestIdentifiedCoordinates = identifiedCoordinates
            .minBy { it.coordinates.manhattanDistance(coordinates) }!!

    val coordinatesWithSameDistance = identifiedCoordinates.filter {
        it.coordinates.manhattanDistance(coordinates) == closestIdentifiedCoordinates.coordinates.manhattanDistance(coordinates)
    }.count()

    return if (coordinatesWithSameDistance > 1) {
        -1
    } else {
        closestIdentifiedCoordinates.id
    }
}

fun _2DIntArray.transpose(): _2DIntArray {
    val width = this.size
    val height = this[0].size
    val newArray = Array(height) { Array(width) { 0 } }

    for (x in 0 until width) {
        for (y in 0 until height) {
            newArray[y][x] = this[x][y]
        }
    }

    return newArray
}

fun main(vararg args: String) {

    val coordinates = BufferedReader(FileReader("input/day06/coordinates.txt"))
            .lines()
            .map { parseCoordinates(it) }
            .collect(Collectors.toList())

    val identifiedCoordinates = mutableListOf<IdentifiedCoordinates>()
    for(i in 0 until coordinates.size) {
        identifiedCoordinates.add(IdentifiedCoordinates(i, coordinates[i]))
    }

    val gridBounds = Bounds.fromCoordinates(coordinates)
    val grid = Grid(gridBounds)
    grid.fillClosestToCoordinatesWithCoordinateId(identifiedCoordinates)

    val infiniteAreaIds = grid.getIdsAtEdge()
    val finiteAreaIds = identifiedCoordinates.map { it.id } - infiniteAreaIds

    val maxFiniteArea = finiteAreaIds.map { grid.getAreaOfId(it) }.max()

    println("Largest finite area: $maxFiniteArea")

    grid.fillWithCumulativeDistanceToAllCoordinates(coordinates)
    val areaWithCumulativeDistancesBelow10K = grid.getAreaWithCumulativeDistanceSmallerThan(10_000)

    println("Area with cumulative distances smaller than 10.000: $areaWithCumulativeDistancesBelow10K")

}