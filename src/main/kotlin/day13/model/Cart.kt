package day13.model

import day06.Coordinates

open class Cart(open var position: Coordinates,
                var direction: Direction,
                var junctionState: JunctionState)

class InterpolatedCart(private val cart: Cart) : Cart(cart.position, cart.direction, cart.junctionState) {

    private var previousPosition: Coordinates = cart.position

    override var position: Coordinates
        get() = cart.position
        set(newCoords) {
            previousPosition = cart.position
            cart.position = newCoords
        }

    fun getInterpolatedCoordinates(t: Double): DoubleCoordinates {
        return DoubleCoordinates(
                (position.x - previousPosition.x) * t,
                (position.y - previousPosition.y) * t
        )
    }

}