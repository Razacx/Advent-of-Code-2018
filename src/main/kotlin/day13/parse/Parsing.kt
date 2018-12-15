package day13.parse

import day06.Coordinates
import day11.Grid2D
import day11.createGrid2D
import day13.model.Cart
import day13.model.Direction.*
import day13.model.JunctionState.Left
import day13.model.Track
import day13.model.Track.*

fun parseCarts(lines: List<String>): List<Cart> {
    val carts: MutableList<Cart> = mutableListOf()

    for (y in 0 until lines.size) {
        val chars = lines[y].toCharArray()
        for (x in 0 until chars.size) {
            val position = Coordinates(x, y)
            val cart = when (chars[x]) {
                '>' -> Cart(position, East, Left)
                '<' -> Cart(position, West, Left)
                '^' -> Cart(position, North, Left)
                'v' -> Cart(position, South, Left)
                else -> null
            }
            if(cart != null) {
                carts.add(cart)
            }
        }
    }

    return carts
}

fun parseTracks(lines: List<String>): Grid2D<Track> {
    val grid = createGrid2D(lines[0].length, lines.size, None)

    for (y in 0 until lines.size) {
        val chars = lines[y].toCharArray()
        for (x in 0 until chars.size) {
            grid[x][y] = when (chars[x]) {
                '-', '>', '<' -> Horizontal
                '|', '^', 'v' -> Vertical
                '\\' -> MainDiagonal
                '/' -> AntiDiagonal
                '+' -> Intersection
                else -> None
            }
        }
    }

    return grid
}
