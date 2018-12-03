package day03

import day03.Grid.CellState.*
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

data class Claim(val id: Int, val rectangle: Rectangle)

data class Rectangle(val x: Int, val y: Int, val width: Int, val height: Int)

class Grid(side: Int) {

    enum class CellState {
        UNCLAIMED,
        CLAIMED,
        OVERCLAIMED
    }

    private val grid: Array<Array<CellState>>

    init {
        grid = Array(side) { Array(side) { UNCLAIMED } }
    }

    fun claim(rectangle: Rectangle) {
        for (i in 0 until rectangle.width) {
            for (j in 0 until rectangle.height) {
                val x = rectangle.x + i
                val y = rectangle.y + j
                claimCell(x, y)
            }
        }
    }

    fun areaIsOnlyCellState(rectangle: Rectangle, cellState: CellState): Boolean {
        for (i in 0 until rectangle.width) {
            for (j in 0 until rectangle.height) {
                val x = rectangle.x + i
                val y = rectangle.y + j
                if (grid[x][y] != cellState) {
                    return false
                }
            }
        }
        return true
    }

    private fun claimCell(x: Int, y: Int) {
        when (grid[x][y]) {
            UNCLAIMED -> grid[x][y] = CLAIMED
            CLAIMED -> grid[x][y] = OVERCLAIMED
            else -> {
            }
        }
    }

    fun getAreaWithCellState(cellState: CellState): Int {
        return grid.flatMap { row -> row.asIterable() }
                .filter { cs -> cs == cellState }
                .count()
    }

}

fun main(vararg args: String) {
    val grid = Grid(1000)
    loadClaims().forEach { claim -> grid.claim(claim.rectangle) }

    println("Total overclaimed area: ${grid.getAreaWithCellState(OVERCLAIMED)}")

    println("Ids of claims that do not overlap:")
    loadClaims()
            .filter { claim -> grid.areaIsOnlyCellState(claim.rectangle, CLAIMED)}
            .forEach { claim -> println(claim.id) }
}

fun loadClaims(): List<Claim> {
    return BufferedReader(FileReader("input/day03/claims.txt"))
            .lines()
            .map { line -> parseRectangle(line) }
            .collect(Collectors.toList())
}

fun parseRectangle(line: String): Claim {
    val result = """#(\d*) @ (\d*),(\d*): (\d*)x(\d*)""".toRegex().matchEntire(line)
    return Claim(
            result!!.groups[1]!!.value.toInt(),
            Rectangle(
                    result.groups[2]!!.value.toInt(),
                    result.groups[3]!!.value.toInt(),
                    result.groups[4]!!.value.toInt(),
                    result.groups[5]!!.value.toInt()
            )
    )
}
