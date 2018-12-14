package day13.model

import day06.Coordinates
import day11.Grid2D
import day11.createGrid2D
import day11.height
import day11.width
import day13.model.Track.*
import org.junit.Test
import kotlin.test.assertEquals

// These are kinda 'integration' tests
class WorldTest {

    @Test
    fun loop_clockwise() {
        val grid2D: Grid2D<Track> = arrayOf(
                arrayOf(AntiDiagonal, Horizontal, MainDiagonal),
                arrayOf(Vertical, None, Vertical),
                arrayOf(MainDiagonal, Horizontal, AntiDiagonal)
        ).transpose()
        val carts = listOf(Cart(Coordinates(1, 0), Direction.East, JunctionState.Left))

        val world = World(grid2D, carts)

        world.moveCarts()
        assertEquals(Coordinates(2, 0), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(2, 1), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(2, 2), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(1, 2), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(0, 2), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(0, 1), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(0, 0), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(1, 0), world.carts[0].position)
    }

    @Test
    fun loop_counterClockwise() {
        val grid2D: Grid2D<Track> = arrayOf(
                arrayOf(AntiDiagonal, Horizontal, MainDiagonal),
                arrayOf(Vertical, None, Vertical),
                arrayOf(MainDiagonal, Horizontal, AntiDiagonal)
        ).transpose()
        val carts = listOf(Cart(Coordinates(1, 0), Direction.West, JunctionState.Left))

        val world = World(grid2D, carts)

        world.moveCarts()
        assertEquals(Coordinates(0, 0), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(0, 1), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(0, 2), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(1, 2), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(2, 2), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(2, 1), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(2, 0), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(1, 0), world.carts[0].position)
    }

    @Test
    fun junctions() {
        val grid2D: Grid2D<Track> = arrayOf(
                arrayOf(Intersection,   Intersection,   Intersection,   None),
                arrayOf(None,           None,           Intersection,   None)
        ).transpose()
        val carts = listOf(Cart(Coordinates(0, 0), Direction.South, JunctionState.Left))

        val world = World(grid2D, carts)

        world.moveCarts()
        assertEquals(Coordinates(1, 0), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(2, 0), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(2, 1), world.carts[0].position)
        world.moveCarts()
        assertEquals(Coordinates(3, 1), world.carts[0].position)
        assertEquals(Direction.East, world.carts[0].direction)
        assertEquals(JunctionState.Straight, world.carts[0].junctionState)
    }
}

inline fun <reified K> Grid2D<K>.transpose(): Grid2D<K> {
    val newGrid = createGrid2D(height(), width(), this[0][0])

    for (x in 0 until width()) {
        for (y in 0 until height()) {
            newGrid[y][x] = this[x][y]
        }
    }

    return newGrid
}