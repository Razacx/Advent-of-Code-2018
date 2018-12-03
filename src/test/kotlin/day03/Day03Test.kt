package day03

import day03.Grid.CellState.*
import org.junit.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun test_gridIsInitializedWithUnclaimedCells() {
        val grid = Grid(10)
        assertEquals(100, grid.getAreaWithCellState(UNCLAIMED))
    }

    @Test
    fun test_claimClaimsAreas() {
        val grid = Grid(10)
        grid.claim(Rectangle(0, 0, 5, 5))
        grid.claim(Rectangle(5, 5, 5, 5))
        assertEquals(50, grid.getAreaWithCellState(UNCLAIMED))
        assertEquals(50, grid.getAreaWithCellState(CLAIMED))
    }

    @Test
    fun test_claimOverClaimsAreas() {
        val grid = Grid(10)
        grid.claim(Rectangle(0, 0, 5, 5))
        grid.claim(Rectangle(1, 1, 2, 2))
        assertEquals(75, grid.getAreaWithCellState(UNCLAIMED))
        assertEquals(25 - 4, grid.getAreaWithCellState(CLAIMED))
        assertEquals(4, grid.getAreaWithCellState(OVERCLAIMED))
    }

    @Test
    fun test_parseClaim() {
        assertEquals(Claim(1075, Rectangle(366, 10, 24, 16)), parseClaim("#1075 @ 366,10: 24x16"))
        assertEquals(Claim(1093, Rectangle(36, 828, 20, 24)), parseClaim("#1093 @ 36,828: 20x24"))
    }

    @Test
    fun test_areaIsFullyCellState() {
        val grid = Grid(10)

        grid.claim(Rectangle(0, 0, 5, 5))
        assertEquals(true, grid.areaIsOnlyCellState(Rectangle(0, 0, 5, 5), CLAIMED))

        grid.claim(Rectangle(1, 1, 2, 2))
        assertEquals(false, grid.areaIsOnlyCellState(Rectangle(0, 0, 5, 5), CLAIMED))
    }

}