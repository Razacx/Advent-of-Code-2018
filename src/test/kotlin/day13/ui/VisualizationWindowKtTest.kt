package day13.ui

import day06.Bounds
import day06.Coordinates
import day13.model.DoubleCoordinates
import org.junit.Test
import kotlin.test.assertEquals

class VisualizationWindowKtTest {

    @Test
    fun test_screenToWorldCoordinates() {
        val screenCoordinates = Coordinates(5, 10)
        val camera = Camera(2.0, 0.0, 0.0)

        val worldCoordinates = screenToWorldCoordinates(camera, screenCoordinates)

        assertEquals(DoubleCoordinates(2.5, 5.0), worldCoordinates)
    }

    @Test
    fun test_screenToWorldCoordinates_offset() {
        val screenCoordinates = Coordinates(10, 10)
        val camera = Camera(2.0, 1.0, 2.0)

        val worldCoordinates = screenToWorldCoordinates(camera, screenCoordinates)

        assertEquals(DoubleCoordinates(6.0, 7.0), worldCoordinates)
    }

    @Test
    fun test_getWorldBounds() {
        val camera = Camera(2.0, 0.0, 0.0)

        val worldBounds = getWorldBounds(camera, 10, 10)

        assertEquals(Bounds(0, 0, 5, 5), worldBounds)
    }
}