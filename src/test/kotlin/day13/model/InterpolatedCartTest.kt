package day13.model

import day06.Coordinates
import org.junit.Test
import kotlin.test.assertEquals

class InterpolatedCartTest {

    @Test
    fun getInterpolatedCoordinates() {
        val cart = InterpolatedCart(Cart(Coordinates(0, 0), Direction.North, JunctionState.Straight))
        cart.position = Coordinates(1, 1)

        val interpolatedCoordinates = cart.getInterpolatedCoordinates(0.7)

        assertEquals(DoubleCoordinates(0.7, 0.7), interpolatedCoordinates)
    }
}