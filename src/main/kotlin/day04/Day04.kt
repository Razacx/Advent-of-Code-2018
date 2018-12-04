package day04

import java.io.BufferedReader
import java.io.FileReader
import kotlin.streams.toList

fun main(vararg args: String) {

    val events = loadEvents()
    val guardPeriods = getGuardPeriods(events)

    // Part 1
    val (guardMostAsleep, minutesAsleep) = getGuardWithMostMinutesAsleep(guardPeriods)
    val (guardMinuteMostSleptOn, timesSleptOnMinute) = getMinuteSleptOnMost(guardPeriods, guardMostAsleep)

    println("Day 4, Part 1:")
    println("\tGuard that slept most: #$guardMostAsleep ($minutesAsleep min)")
    println("\tSlept most on minute: $guardMinuteMostSleptOn ($timesSleptOnMinute times)")
    println("\tAnswer: ${guardMostAsleep * guardMinuteMostSleptOn}")

    println()

    //Part 2
    val (guardMostFrequentlyAsleepOnSameMinute, mostFrequentMinute, timesSleptOnMostFrequentMinute)
            = getGuardThatWasMostAsleepOnSameMinuteTuple(guardPeriods)

    println("Day 4, Part 2:")
    println("\tGuard that slept on same minute most frequently: #$guardMostFrequentlyAsleepOnSameMinute")
    println("\tSlept most frequently on minute for guard: $mostFrequentMinute ($timesSleptOnMostFrequentMinute times)")
    println("\tAnswer: ${guardMostFrequentlyAsleepOnSameMinute * mostFrequentMinute}")


}

fun loadEvents(): List<Event> {
    return BufferedReader(FileReader("input/day04/observations.txt"))
            .lines()
            .map { parseEvent(it) }
            .toList()
}