package day11

import day06.Bounds

typealias Grid2D<K> = Array<Array<K>>

inline fun <reified K> createGrid2D(width: Int, height: Int, defaultValue: K): Grid2D<K> {
    return Array(width) { Array(height) { defaultValue } }
}

fun <K> Grid2D<K>.width(): Int {
    return size
}

fun <K> Grid2D<K>.height(): Int {
    return this[0].size
}

// Too slow for our purpose, but might come in handy in the future
inline fun <reified K> Grid2D<K>.subgrid(bounds: Bounds): Grid2D<K> {
    val subgrid = createGrid2D(bounds.width(), bounds.height(), this[0][0])

    for (x in bounds.minX..bounds.maxX) {
        for (y in bounds.minY..bounds.maxY) {
            subgrid[x - bounds.minX][y - bounds.minY] = this[x][y]
        }
    }

    return subgrid
}

fun Grid2D<Int>.sumCells(bounds: Bounds): Int {
    var result = 0
    for (x in bounds.minX .. bounds.maxX) {
        for (y in bounds.minY .. bounds.maxY) {
            result += this[x][y]
        }
    }
    return result
}
