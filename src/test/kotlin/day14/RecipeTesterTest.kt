package day14

import org.junit.Test
import kotlin.test.assertEquals

class RecipeTesterTest {

    @Test
    fun getScoreAfter() {
        assertEquals("5158916779", RecipeTester().getScoreAfter(9, 10, false))
        assertEquals("0124515891", RecipeTester().getScoreAfter(5, 10, false))
        assertEquals("9251071085", RecipeTester().getScoreAfter(18, 10, false))
        assertEquals("5941429882", RecipeTester().getScoreAfter(2018, 10, false))
    }

    @Test
    fun getIterationsForScore() {
        assertEquals(9, RecipeTester().getIterationsForScore("51589", false))
        assertEquals(5, RecipeTester().getIterationsForScore("01245", false))
        assertEquals(18, RecipeTester().getIterationsForScore("92510", false))
        assertEquals(2018, RecipeTester().getIterationsForScore("59414", false))
    }
}