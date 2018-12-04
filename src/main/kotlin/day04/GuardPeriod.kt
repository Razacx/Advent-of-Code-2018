package day04

import day04.GuardState.ASLEEP
import day04.GuardState.AWAKE

enum class GuardState {
    ASLEEP,
    AWAKE
}

data class GuardPeriod(val guardId: Int, val state: GuardState, val period: Period)

fun getGuardPeriods(events: List<Event>): List<GuardPeriod> {
    val sortedEvents = events.sortedBy(Event::timestamp)
    val periods = mutableListOf<GuardPeriod>()

    var currentGuard = (sortedEvents[0] as ShiftChangeEvent).guardId
    var lastEvent = sortedEvents[0]

    for (event in sortedEvents.drop(1)) {
        periods.add(getGuardPeriod(currentGuard, lastEvent, event))
        if (event is ShiftChangeEvent) {
            currentGuard = event.guardId
        }
        lastEvent = event
    }

    return periods
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
