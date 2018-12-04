package day04

import day04.GuardState.ASLEEP
import day04.GuardState.AWAKE
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class GuardPeriodTest {

    @Test
    fun test_getGuardPeriod_startShift_periodIsAwake() {
        val from = LocalDateTime.of(0, 1, 1, 0, 0)
        val to = LocalDateTime.of(0, 1, 1, 0, 5)
        assertEquals(
                GuardPeriod(1, AWAKE, Period(from, LocalDateTime.of(0, 1, 1, 0, 4))),
                getGuardPeriod(1, ShiftChangeEvent(1, from), SleepEvent(to))
        )
    }

    @Test
    fun test_getGuardPeriod_awake_periodIsAwake() {
        val from = LocalDateTime.of(0, 1, 1, 0, 0)
        val to = LocalDateTime.of(0, 1, 1, 0, 5)
        assertEquals(
                GuardPeriod(1, AWAKE, Period(from, LocalDateTime.of(0, 1, 1, 0, 4))),
                getGuardPeriod(1, WakeEvent(from), SleepEvent(to))
        )
    }

    @Test
    fun test_getGuardPeriod_sleep_periodIsAsleep() {
        val from = LocalDateTime.of(0, 1, 1, 0, 0)
        val to = LocalDateTime.of(0, 1, 1, 0, 5)
        assertEquals(
                GuardPeriod(1, GuardState.ASLEEP, Period(from, LocalDateTime.of(0, 1, 1, 0, 4))),
                getGuardPeriod(1, SleepEvent(from), WakeEvent(to))
        )
    }

    @Test
    fun test_getGuardPeriods() {
        val events = mutableListOf(
                ShiftChangeEvent(1, LocalDateTime.of(0, 1, 1, 0, 0)),
                SleepEvent(LocalDateTime.of(0, 1, 1, 0, 5)),
                WakeEvent(LocalDateTime.of(0, 1, 1, 0, 15)),
                ShiftChangeEvent(2, LocalDateTime.of(0, 1, 1, 0, 30)),
                SleepEvent(LocalDateTime.of(0, 1, 1, 0, 45)),
                WakeEvent(LocalDateTime.of(0, 1, 1, 0, 55))
        )
        events.shuffle()

        val periods = getGuardPeriods(events)

        assertEquals(periods, listOf(
                GuardPeriod(1, AWAKE, Period(LocalDateTime.of(0, 1, 1, 0, 0), LocalDateTime.of(0, 1, 1, 0, 4))),
                GuardPeriod(1, ASLEEP, Period(LocalDateTime.of(0, 1, 1, 0, 5), LocalDateTime.of(0, 1, 1, 0, 14))),
                GuardPeriod(1, AWAKE, Period(LocalDateTime.of(0, 1, 1, 0, 15), LocalDateTime.of(0, 1, 1, 0, 29))),
                GuardPeriod(2, AWAKE, Period(LocalDateTime.of(0, 1, 1, 0, 30), LocalDateTime.of(0, 1, 1, 0, 44))),
                GuardPeriod(2, ASLEEP, Period(LocalDateTime.of(0, 1, 1, 0, 45), LocalDateTime.of(0, 1, 1, 0, 54)))
        ))
    }

}