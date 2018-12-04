package day04

import day04.GuardState.ASLEEP
import day04.GuardState.AWAKE
import org.junit.Test
import kotlin.test.assertEquals

class Day04Test {

    @Test
    fun test_parseTimestamp() {
        assertEquals(TimeStamp(1518, 7, 27, 0, 2), parseTimestamp("[1518-07-27 00:02] falls asleep"))
        assertEquals(TimeStamp(1518, 7, 27, 0, 2), parseTimestamp("[1518-07-27 00:02]"))
        assertEquals(TimeStamp(1518, 4, 6, 0, 29), parseTimestamp("[1518-04-06 00:29] wakes up"))
    }

    @Test
    fun test_parseEvent() {
        assertEquals(ShiftChangeEvent(1601, TimeStamp(1518, 11, 18, 0, 4)), parseEvent("[1518-11-18 00:04] Guard #1601 begins shift"))
        assertEquals(SleepEvent(TimeStamp(1518, 8, 20, 0, 23)), parseEvent("[1518-08-20 00:23] falls asleep"))
        assertEquals(WakeEvent(TimeStamp(1518, 7, 23, 0, 52)), parseEvent("[1518-07-23 00:52] wakes up"))
    }

    @Test
    fun test_parseGuardId() {
        assertEquals(691, parseGuardId("[1518-05-21 23:58] Guard #691 begins shift"))
        assertEquals(2969, parseGuardId("[1518-11-15 00:04] Guard #2969 begins shift"))
    }

    @Test
    fun test_getGuardPeriod_startShift_periodIsAwake() {
        val from = TimeStamp(0, 0, 0, 0, 0)
        val to = TimeStamp(0, 0, 0, 0, 5)
        assertEquals(
                GuardPeriod(1, AWAKE, Period(from, TimeStamp(0, 0, 0, 0, 4))),
                getGuardPeriod(1, ShiftChangeEvent(1, from), SleepEvent(to))
        )
    }

    @Test
    fun test_getGuardPeriod_awake_periodIsAwake() {
        val from = TimeStamp(0, 0, 0, 0, 0)
        val to = TimeStamp(0, 0, 0, 0, 5)
        assertEquals(
                GuardPeriod(1, AWAKE, Period(from, TimeStamp(0, 0, 0, 0, 4))),
                getGuardPeriod(1, WakeEvent(from), SleepEvent(to))
        )
    }

    @Test
    fun test_getGuardPeriod_sleep_periodIsAsleep() {
        val from = TimeStamp(0, 0, 0, 0, 0)
        val to = TimeStamp(0, 0, 0, 0, 5)
        assertEquals(
                GuardPeriod(1, GuardState.ASLEEP, Period(from, TimeStamp(0, 0, 0, 0, 4))),
                getGuardPeriod(1, SleepEvent(from), WakeEvent(to))
        )
    }

    @Test
    fun test_getGuardPeriods() {
        val events = mutableListOf(
                ShiftChangeEvent(1, TimeStamp(0, 0, 0, 0, 0)),
                SleepEvent(TimeStamp(0, 0, 0, 0, 5)),
                WakeEvent(TimeStamp(0, 0, 0, 0, 15)),
                ShiftChangeEvent(2, TimeStamp(0, 0, 0, 0, 30)),
                SleepEvent(TimeStamp(0, 0, 0, 0, 45)),
                WakeEvent(TimeStamp(0, 0, 0, 0, 55))
        )
        events.shuffle()

        val periods = getGuardPeriods(events)

        assertEquals(periods, listOf(
                GuardPeriod(1, AWAKE, Period(TimeStamp(0, 0, 0, 0, 0), TimeStamp(0, 0, 0, 0, 4))),
                GuardPeriod(1, ASLEEP, Period(TimeStamp(0, 0, 0, 0, 5), TimeStamp(0, 0, 0, 0, 14))),
                GuardPeriod(1, AWAKE, Period(TimeStamp(0, 0, 0, 0, 15), TimeStamp(0, 0, 0, 0, 29))),
                GuardPeriod(2, AWAKE, Period(TimeStamp(0, 0, 0, 0, 30), TimeStamp(0, 0, 0, 0, 44))),
                GuardPeriod(2, ASLEEP, Period(TimeStamp(0, 0, 0, 0, 45), TimeStamp(0, 0, 0, 0, 54)))
        ))

    }

}