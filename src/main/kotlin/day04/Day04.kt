package day04

import day04.GuardState.ASLEEP
import day04.GuardState.AWAKE
import java.io.BufferedReader
import java.io.FileReader
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors

abstract class Event(val timestamp: LocalDateTime) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        return timestamp.hashCode()
    }
}

class ShiftChangeEvent(val guardId: Int, timestamp: LocalDateTime) : Event(timestamp) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShiftChangeEvent

        if (guardId != other.guardId) return false

        return true
    }

    override fun hashCode(): Int {
        return guardId
    }
}

class SleepEvent(timestamp: LocalDateTime) : Event(timestamp)

class WakeEvent(timestamp: LocalDateTime) : Event(timestamp)

data class Period(val from: LocalDateTime, val to: LocalDateTime) {
    fun minuteDifference(): Int {
        return ChronoUnit.MINUTES.between(from, to).toInt()

    }

    fun getMinutes(): List<Int> {
        return (from.minute..to.minute).toList() //Kind of a hack...
    }
}

enum class GuardState {
    ASLEEP,
    AWAKE
}

data class GuardPeriod(val guardId: Int, val state: GuardState, val period: Period)

fun parseEvent(line: String): Event {
    val timestamp = parseTimestamp(line)
    return when {
        line.contains("Guard") -> ShiftChangeEvent(parseGuardId(line), timestamp)
        line.contains("asleep") -> SleepEvent(timestamp)
        line.contains("wakes") -> WakeEvent(timestamp)
        else -> throw IllegalArgumentException("Unable to parse event: $line")
    }
}

fun parseGuardId(line: String): Int {
    return """.*Guard #(\d+).*""".toRegex().matchEntire(line)!!.groups[1]!!.value.toInt()
}

fun parseTimestamp(timestamp: String): LocalDateTime {
    val result = """^\[(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})].*""".toRegex().matchEntire(timestamp)
    return LocalDateTime.of(
            result!!.groups[1]!!.value.toInt(),
            result.groups[2]!!.value.toInt(),
            result.groups[3]!!.value.toInt(),
            result.groups[4]!!.value.toInt(),
            result.groups[5]!!.value.toInt()
    )
}

fun getGuardPeriod(guardId: Int, fromEvent: Event, toEvent: Event): GuardPeriod {
    val state = when (fromEvent) {
        is ShiftChangeEvent -> AWAKE
        is WakeEvent -> AWAKE
        is SleepEvent -> ASLEEP
        else -> throw IllegalArgumentException("Unknown state for GuardPeriod")
    }
    return GuardPeriod(guardId, state, Period(fromEvent.timestamp, toEvent.timestamp.minusMinutes(1)))
}

fun getGuardPeriods(events: List<Event>): List<GuardPeriod> {
    val sortedEvents = events.sortedBy(Event::timestamp)
    val periods = mutableListOf<GuardPeriod>()

    var currentGuard = (sortedEvents[0] as ShiftChangeEvent).guardId
    var lastEvent = sortedEvents[0]

    for (event in sortedEvents.drop(1)) {
        periods.add(getGuardPeriod(currentGuard, lastEvent, event))
        if (event is ShiftChangeEvent) {
            currentGuard = (event as ShiftChangeEvent).guardId
        }
        lastEvent = event
    }

    return periods
}

fun main(vararg args: String) {
    val events = BufferedReader(FileReader("input/day04/observations.txt"))
            .lines()
            .map { line -> parseEvent(line) }
            .collect(Collectors.toList())
    val guardPeriods = getGuardPeriods(events)

    val (guardId, minutesSlept) = guardPeriods
            .stream()
            .filter { period -> period.state == ASLEEP }
            .collect(Collectors.groupingBy(GuardPeriod::guardId))
            .entries
            .map { entry -> Pair(entry.key as Int, entry.value.sumBy { period -> period.period.minuteDifference() }) }
            .maxBy { entry -> entry.second }!!
    println("The laziest guard is #$guardId with $minutesSlept minutes slept")

    val (minute, times) = guardPeriods
            .stream()
            .filter { period -> period.guardId == guardId }
            .filter { period -> period.state == ASLEEP }
            .flatMap { period -> period.period.getMinutes().stream() }
            .collect(Collectors.groupingBy({ minute -> minute }, Collectors.counting()))
            .entries
            .maxBy { entry -> entry.value }!!

    println("The guard slept $times times during the ${minute}th minute")

    println("Final answer (id * minute) = ${guardId * (minute as Int)}")

    val periodsPerGuard = guardPeriods
            .stream()
            .collect(Collectors.groupingBy(GuardPeriod::guardId))

    val mostFrequentMinutePerGuard = mutableMapOf<Int, Pair<Int, Int>>()

    for (entry in periodsPerGuard.entries) {
        val (mostFrequentMinute, frequency) = entry.value
                .stream()
                .flatMap { period -> period.period.getMinutes().stream() }
                .collect(Collectors.groupingBy({ m -> m }, Collectors.counting()))
                .entries
                .maxBy { e -> e.value }!!
        mostFrequentMinutePerGuard[entry.key] = Pair(mostFrequentMinute as Int, frequency.toInt())
    }

//    val (mostConsistentGuard, mostFrequentMinute) =

    val max: MutableMap.MutableEntry<Int, Pair<Int, Int>>? = mostFrequentMinutePerGuard.entries
            .maxBy { entry -> entry.value.second }
    val mostConsistentGuard = max!!.key
    val mostFrequentMinute = max.value.second

    println()
    println("The most consistent guard is #$mostConsistentGuard with minute $mostFrequentMinute")
    println("Answer: ${mostConsistentGuard * mostFrequentMinute}")

}