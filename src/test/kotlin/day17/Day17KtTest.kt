package day17

import day06.Coordinates
import day17.SoilType.*
import org.junit.Test
import kotlin.test.assertTrue

class Day17KtTest {

    @Test
    fun test_flowDown() {
        val grid = createTestGrid(listOf(
                "|",
                "."
        ))

        grid.addTilesToUpdate(listOf(Coordinates(0, 1)))
        tick(grid)

        val expected = createTestGrid(listOf(
                "|",
                "|"
        ))

        assertTrue(grid.grid contentDeepEquals expected.grid)
    }

    @Test
    fun test_flowSideWays() {
        val grid = createTestGrid(listOf(
                ".|.",
                "###"
        ))

        grid.addTilesToUpdate(listOf(
                Coordinates(0, 0),
                Coordinates(2, 0)
        ))
    }

    fun createTestGrid(lines: List<String>): TileUpdateOffsetGrid2D<SoilType> {
        val width = lines[0].length
        val height = lines.size

        val grid = createTileUpdateOffsetGrid2D(0, 0, width, height, SAND)
        for (y in 0 until height) {
            val characters = lines[y].toCharArray()
            for (x in 0 until width) {
                grid[x][y] = when (characters[x]) {
                    '.' -> SAND
                    '#' -> CLAY
                    '|' -> WATER_FLOWING
                    '~' -> WATER_STILL
                    else -> throw IllegalArgumentException()
                }
            }
        }

        grid.resetTilesToUpdate()
        return grid
    }

}