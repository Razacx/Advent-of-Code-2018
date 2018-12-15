package day13.parse

import day06.Coordinates
import day11.width
import day13.model.Cart
import day13.model.Direction.*
import day13.model.JunctionState.Left
import day13.model.Track.*
import day13.model.transpose
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParsingKtTest {

    @Test
    fun test_parseTracks_horizontal() {
        val input = listOf("""-""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(Horizontal)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_horizontal_withEastCart() {
        val input = listOf(""">""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(Horizontal)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_horizontal_withWestCart() {
        val input = listOf("""<""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(Horizontal)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_vertical() {
        val input = listOf("""|""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(Vertical)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_vertical_withNorthCart() {
        val input = listOf("""^""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(Vertical)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_vertical_withSouthCart() {
        val input = listOf("""v""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(Vertical)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_diagonal_main() {
        val input = listOf("""\""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(MainDiagonal)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_diagonal_anti() {
        val input = listOf("""/""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(AntiDiagonal)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_intersection() {
        val input = listOf("""+""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(Intersection)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_empty() {
        val input = listOf(""" """)
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(None)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_randomChar() {
        val input = listOf("""g""")
        val grid = parseTracks(input)
        assertTrue(arrayOf(arrayOf(None)).transpose() contentDeepEquals grid)
    }

    @Test
    fun test_parseTracks_usesLargestLineAsWidth() {
        val input = listOf(
                """/---\""",
                """|   \--\""",
                """\------/"""
        )
        val grid = parseTracks(input)
        assertEquals(8, grid.width())
    }

    @Test
    fun test_parseTracks_full() {
        val input = listOf(
                """/->-\        """,
                """|   |  /----\""",
                """| /-+--+-\  |""",
                """| | |  | v  |""",
                """\-+-/  \-+--/""",
                """  \------/   """
        )
        // This sucked ...
        val expectedGrid = arrayOf(
                arrayOf(AntiDiagonal, Horizontal, Horizontal, Horizontal, MainDiagonal, None, None, None, None, None, None, None, None),
                arrayOf(Vertical, None, None, None, Vertical, None, None, AntiDiagonal, Horizontal, Horizontal, Horizontal, Horizontal, MainDiagonal),
                arrayOf(Vertical, None, AntiDiagonal, Horizontal, Intersection, Horizontal, Horizontal, Intersection, Horizontal, MainDiagonal, None, None, Vertical),
                arrayOf(Vertical, None, Vertical, None, Vertical, None, None, Vertical, None, Vertical, None, None, Vertical),
                arrayOf(MainDiagonal, Horizontal, Intersection, Horizontal, AntiDiagonal, None, None, MainDiagonal, Horizontal, Intersection, Horizontal, Horizontal, AntiDiagonal),
                arrayOf(None, None, MainDiagonal, Horizontal, Horizontal, Horizontal, Horizontal, Horizontal, Horizontal, AntiDiagonal, None, None, None)
        ).transpose()

        val grid = parseTracks(input)

        assertTrue(expectedGrid contentDeepEquals grid)
    }

    @Test
    fun test_parseCarts() {
        val input = listOf(
                """/->-\        """,
                """|   |  /----\""",
                """| /-+<-+-\  ^""",
                """| | |  | v  |""",
                """\-+-/  \-+--/""",
                """  \------/   """
        )
        val expectedCarts = listOf(
                Cart(0, Coordinates(2, 0), East, Left),
                Cart(1, Coordinates(5, 2), West, Left),
                Cart(2, Coordinates(12, 2), North, Left),
                Cart(3, Coordinates(9, 3), South, Left)
        )

        val carts = parseCarts(input)

        assertEquals(expectedCarts, carts)
    }

}