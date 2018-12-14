package day13.parse

import day11.Grid2D
import day11.createGrid2D
import day13.model.Cart
import day13.model.Track
import day13.model.Track.None

fun loadCarts(): List<Cart> {
    return listOf()
}

fun loadTracks(): Grid2D<Track> {
    return createGrid2D(1, 1, None)
}
