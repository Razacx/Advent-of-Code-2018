package day04

import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class StatsTest {

    private val guardPeriodsStrategy1 = listOf(
            GuardPeriod(1, GuardState.ASLEEP, Period(LocalDateTime.of(0, 1, 1, 0, 0), LocalDateTime.of(0, 1, 1, 0, 30))),
            GuardPeriod(1, GuardState.ASLEEP, Period(LocalDateTime.of(0, 1, 1, 0, 30), LocalDateTime.of(0, 1, 1, 0, 59))),

            GuardPeriod(2, GuardState.AWAKE, Period(LocalDateTime.of(0, 1, 2, 0, 0), LocalDateTime.of(0, 1, 2, 0, 59))),
            GuardPeriod(2, GuardState.ASLEEP, Period(LocalDateTime.of(0, 1, 3, 0, 0), LocalDateTime.of(0, 1, 1, 1, 30)))
    )

    private val guardPeriodsStrategy2 = listOf(
            GuardPeriod(1, GuardState.ASLEEP, Period(LocalDateTime.of(0, 1, 1, 0, 0), LocalDateTime.of(0, 1, 1, 0, 30))),
            GuardPeriod(1, GuardState.ASLEEP, Period(LocalDateTime.of(0, 1, 1, 0, 30), LocalDateTime.of(0, 1, 1, 0, 59))),

            GuardPeriod(2, GuardState.ASLEEP, Period(LocalDateTime.of(0, 1, 2, 0, 0), LocalDateTime.of(0, 1, 2, 0, 30))),
            GuardPeriod(2, GuardState.ASLEEP, Period(LocalDateTime.of(0, 1, 3, 0, 30), LocalDateTime.of(0, 1, 3, 0, 59))),
            GuardPeriod(2, GuardState.ASLEEP, Period(LocalDateTime.of(0, 1, 4, 0, 30), LocalDateTime.of(0, 1, 4, 0, 30)))
    )

    @Test
    fun test_getGuardWithMostMinutesAsleep() {
        assertEquals(1, getGuardWithMostMinutesAsleep(guardPeriodsStrategy1))
    }

    @Test
    fun test_getMinuteSleptMostOn() {
        assertEquals(30, getMinuteSleptMostOn(guardPeriodsStrategy1, 1))
    }



}