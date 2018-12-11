package day11

import day06.Bounds

fun Bounds.Companion.fromWidthHeight(x: Int, y: Int, width: Int, height: Int): Bounds {
    return Bounds(x, y, x + width - 1, y + height - 1)
}
