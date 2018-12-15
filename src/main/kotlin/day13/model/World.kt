package day13.model

import day11.Grid2D

class World(val tracks: Grid2D<Track>, initialCarts: List<Cart>) {

    private val _carts: MutableList<InterpolatedCart> = initialCarts.map { InterpolatedCart(it) }.toMutableList()
    val carts: List<InterpolatedCart> get() = _carts

    private val _crashes: MutableList<Crash> = mutableListOf()
    val crashes: List<Crash> get() = _crashes

    fun moveCarts() {
        // We are going to be removing carts when crashing, so we need to make a copy of the carts list here...
        for (cart in _carts.toList()) {

            tracks[cart.position.x][cart.position.y].move(cart)

            for (overlappingCartGroup in findOverlappingCarts()) {
                if (overlappingCartGroup.size > 2) throw IllegalStateException("This should not be possible") //Sanity check
                _crashes.add(Crash(overlappingCartGroup[0].position))
                _carts.remove(overlappingCartGroup[0])
                _carts.remove(overlappingCartGroup[1])
            }

        }
    }

    fun findOverlappingCarts(): List<List<InterpolatedCart>> {
        return _carts
                .groupBy { it.position }
                .filter { it.value.size > 1 }
                .map { it.value }
    }

}