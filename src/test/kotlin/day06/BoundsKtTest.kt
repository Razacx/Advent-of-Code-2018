package day06

import org.junit.Test

class BoundsKtTest {

    @Test(expected = IllegalArgumentException::class)
    fun test_bounds_throwsException_ifMinXLargerThanMaxX() {
        Bounds(10, 0, 0, 10)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_bounds_throwsException_ifMinYLargerThanMaxY() {
        Bounds(0, 10, 10, 0)
    }

    @Test
    fun test_bounds_valid() {
        Bounds(0, 0, 10, 10)
        Bounds(0, 0, 0, 0)
    }

    @Test
    fun test_bounds_width_height() {
        val bounds = Bounds(0, 0, 10, 5)
        kotlin.test.assertEquals(11, bounds.width())
        kotlin.test.assertEquals(6, bounds.height())
    }

    @Test
    fun test_bounds_fromCoordinates() {
        val bounds = Bounds.fromCoordinates(listOf(
                Coordinates(-1, 0),
                Coordinates(0, -2),
                Coordinates(3, 0),
                Coordinates(0, 4)
        ))
        kotlin.test.assertEquals(Bounds(-1, -2, 3, 4), bounds)
    }

}
