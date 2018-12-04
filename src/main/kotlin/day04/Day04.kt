package day04

import java.io.BufferedReader
import java.io.FileReader
import kotlin.streams.toList

fun main(vararg args: String) {

    val events = loadEvents()
    val guardPeriods = getGuardPeriods(events)

    // Part 1
    val guardMostAsleep = getGuardWithMostMinutesAsleep(guardPeriods)
    val guardMinuteMostSleptOn = getMinuteSleptMostOn(guardPeriods, guardMostAsleep)

    println("Day 4, Part 1:")
    println("\tGuard that slept most: #$guardMostAsleep")
    println("\tSlept most on minute: $guardMinuteMostSleptOn")
    println("\tAnswer:${guardMostAsleep * guardMinuteMostSleptOn}")
    println()

    //Part 2
    println("Day 4, Part 2:")

}

fun loadEvents(): List<Event> {
    return BufferedReader(FileReader("input/day04/observations.txt"))
            .lines()
            .map { parseEvent(it) }
            .toList()
}