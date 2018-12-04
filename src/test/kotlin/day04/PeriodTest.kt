package day04

import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class PeriodTest {

    @Test
    fun test_period_getMinutes() {
        val period = Period(
                LocalDateTime.of(0, 1, 1, 0, 10),
                LocalDateTime.of(0, 1, 1, 0, 14)
        )
        assertEquals(listOf(10, 11, 12, 13, 14), period.getMinutes())
    }

    @Test
    fun test_period_getMinutes_goesOverHour() {
        val period = Period(
                LocalDateTime.of(0, 1, 1, 0, 59),
                LocalDateTime.of(0, 1, 1, 1, 3)
        )
        assertEquals(listOf(59, 0, 1, 2, 3), period.getMinutes())
    }

}