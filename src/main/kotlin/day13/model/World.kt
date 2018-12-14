package day13.model

import day11.Grid2D

class World(val tracks: Grid2D<Track>, initialCarts: List<Cart>) {

    val carts: List<InterpolatedCart> = initialCarts.map { InterpolatedCart(it) }

    fun moveCarts() {
        for (cart in carts) {
            tracks[cart.position.x][cart.position.y].move(cart)
        }
    }

}