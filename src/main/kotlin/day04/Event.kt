package day04

import java.time.LocalDateTime

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