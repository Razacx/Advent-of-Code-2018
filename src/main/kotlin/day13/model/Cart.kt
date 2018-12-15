package day13.model

import day06.Coordinates

open class Cart(val id: Int,
                open var position: Coordinates,
                var direction: Direction,
                var junctionState: JunctionState) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cart

        if (id != other.id) return false
        if (position != other.position) return false
        if (direction != other.direction) return false
        if (junctionState != other.junctionState) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + position.hashCode()
        result = 31 * result + direction.hashCode()
        result = 31 * result + junctionState.hashCode()
        return result
    }

}

class InterpolatedCart(private val cart: Cart) : Cart(cart.id, cart.position, cart.direction, cart.junctionState) {

    private var previousPosition: Coordinates = cart.position

    override var position: Coordinates
        get() = cart.position
        set(newCoords) {
            previousPosition = cart.position
            cart.position = newCoords
        }

    fun getInterpolatedCoordinates(t: Double): DoubleCoordinates {
        return DoubleCoordinates(
                previousPosition.x + (position.x - previousPosition.x) * t,
                previousPosition.y + (position.y - previousPosition.y) * t
        )
    }

}