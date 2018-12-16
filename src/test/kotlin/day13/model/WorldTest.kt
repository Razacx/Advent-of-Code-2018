package day13.model

import day06.Coordinates
import day11.Grid2D
import day11.createGrid2D
import day11.height
import day11.width
import day13.model.Direction.North
import day13.model.JunctionState.Left
import day13.model.Track.*
import day13.parse.parseCarts
import day13.parse.parseTracks
import org.junit.Test
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors
import kotlin.test.assertEquals
import kotlin.test.expect

// These are kinda 'integration' tests
class WorldTest {

    @Test
    fun loop_clockwise() {
        val grid2D: Grid2D<Track> = arrayOf(
                arrayOf(AntiDiagonal, Horizontal, MainDiagonal),
                arrayOf(Vertical, None, Vertical),
                arrayOf(MainDiagonal, Horizontal, AntiDiagonal)
        ).transpose()
        val carts = listOf(Cart(0, Coordinates(1, 0), Direction.East, Left))

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
        val carts = listOf(Cart(0, Coordinates(1, 0), Direction.West, Left))

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
                arrayOf(Intersection, Intersection, Intersection, None),
                arrayOf(None, None, Intersection, None)
        ).transpose()
        val carts = listOf(Cart(0, Coordinates(0, 0), Direction.South, Left))

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

    @Test
    fun crashes() {
        val lines = BufferedReader(FileReader("input/day13/test-tracks.txt"))
                .lines()
                .collect(Collectors.toList())
        val world = World(parseTracks(lines), parseCarts(lines))

        var iterations = 0
        while (world.crashes.isEmpty()) {
            world.moveCarts()
            iterations++
            if (iterations == 500) {
                throw IllegalStateException("Test failed, too many iterations required")
            }
        }

        assertEquals(Crash(Coordinates(7, 3)), world.crashes[0])
    }

    @Test
    fun cartExecutionOrder() {
        val carts = listOf(
                Cart(0, Coordinates(2, 2), North, Left),
                Cart(1, Coordinates(2, 1), North, Left),
                Cart(2, Coordinates(1, 1), North, Left)
        )
        val world = World(createGrid2D(5, 5, None), carts)

        val sortedCarts = world.getCartsInExecutionOrder()
        assertEquals(carts[2].id, sortedCarts[0].id)
        assertEquals(carts[1].id, sortedCarts[1].id)
        assertEquals(carts[0].id, sortedCarts[2].id)
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