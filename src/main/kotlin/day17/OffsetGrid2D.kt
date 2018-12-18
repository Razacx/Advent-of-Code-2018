package day17

import day06.Bounds
import day11.Grid2D
import day11.createGrid2D
import day11.height
import day11.width

class OffsetGrid2D<K>(val x: Int, val y: Int, val grid: Grid2D<K>) {

    operator fun get(x: Int): OffsetGrid2DColumn<K> {
        return OffsetGrid2DColumn(this, x)
    }

    fun width(): Int {
        return grid.width()
    }

    fun height(): Int {
        return grid.height()
    }

}

class OffsetGrid2DColumn<K>(private val offsetGrid2D: OffsetGrid2D<K>, private val x: Int) {

    operator fun get(y: Int): K {
        val grid = offsetGrid2D.grid
        val xOffset = offsetGrid2D.x
        val yOffset = offsetGrid2D.y

        return grid[x - xOffset][y - yOffset]
    }

    operator fun set(y: Int, value: K) {
        val grid = offsetGrid2D.grid
        val xOffset = offsetGrid2D.x
        val yOffset = offsetGrid2D.y

        grid[x - xOffset][y - yOffset] = value
    }

}

inline fun <reified K> createOffsetGrid2D(x: Int, y: Int, width: Int, height: Int, defaultValue: K): OffsetGrid2D<K> {
    val grid = createGrid2D(width, height, defaultValue)
    return OffsetGrid2D(x, y, grid)
}

inline fun <reified K> createOffsetGrid2D(bounds: Bounds, defaultValue: K): OffsetGrid2D<K> {
    return createOffsetGrid2D(bounds.minX, bounds.minY, bounds.width(), bounds.height(), defaultValue)
}

fun <K> Grid2D<K>.render(valueConverter: (K) -> String): List<String> {
    val lines = mutableListOf<String>()

    for(y in 0 until height()) {
        var line = ""
        for (x in 0 until width()) {
            line += valueConverter(this[x][y])
        }
        lines.add(line)
    }

    return lines
}