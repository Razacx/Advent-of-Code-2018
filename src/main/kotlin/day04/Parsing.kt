package day04

import java.time.LocalDateTime

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
