package day04

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class Period(val from: LocalDateTime, val to: LocalDateTime) {
    fun minuteDifference(): Int {
        return ChronoUnit.MINUTES.between(from, to).toInt()
    }

    fun getMinutes(): List<Int> {
        val timestamps = mutableListOf(from)
        while(timestamps.last() != to) {
            timestamps.add(timestamps.last().plusMinutes(1))
        }
        return timestamps.map(LocalDateTime::getMinute)
    }
}