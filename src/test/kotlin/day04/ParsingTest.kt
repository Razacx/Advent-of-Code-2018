package day04

import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ParsingTest {

    @Test
    fun test_parseTimestamp() {
        assertEquals(LocalDateTime.of(1518, 7, 27, 0, 2), parseTimestamp("[1518-07-27 00:02] falls asleep"))
        assertEquals(LocalDateTime.of(1518, 7, 27, 0, 2), parseTimestamp("[1518-07-27 00:02]"))
        assertEquals(LocalDateTime.of(1518, 4, 6, 0, 29), parseTimestamp("[1518-04-06 00:29] wakes up"))
    }

    @Test
    fun test_parseEvent() {
        assertEquals(ShiftChangeEvent(1601, LocalDateTime.of(1518, 11, 18, 0, 4)), parseEvent("[1518-11-18 00:04] Guard #1601 begins shift"))
        assertEquals(SleepEvent(LocalDateTime.of(1518, 8, 20, 0, 23)), parseEvent("[1518-08-20 00:23] falls asleep"))
        assertEquals(WakeEvent(LocalDateTime.of(1518, 7, 23, 0, 52)), parseEvent("[1518-07-23 00:52] wakes up"))
    }

    @Test
    fun test_parseGuardId() {
        assertEquals(691, parseGuardId("[1518-05-21 23:58] Guard #691 begins shift"))
        assertEquals(2969, parseGuardId("[1518-11-15 00:04] Guard #2969 begins shift"))
    }

}