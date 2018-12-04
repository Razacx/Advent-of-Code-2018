package day04

fun getGuardWithMostMinutesAsleep(guardPeriods: List<GuardPeriod>): Int {
    val minutesSleptPerGuard = guardPeriods
            .filter { it.state == GuardState.ASLEEP }
            .groupBy(GuardPeriod::guardId)
            .mapValues {
                // Reduces List<GuardPeriod> for each Guard
                it.value.sumBy { guardPeriod ->
                    guardPeriod.period.minuteDifference()
                }
            }
    return minutesSleptPerGuard.maxBy(Map.Entry<Int, Int>::value)!!.key
}

fun getMinuteSleptMostOn(guardPeriods: List<GuardPeriod>, guardId: Int): Int {
    val asleepGuardPeriodsForGuard = guardPeriods
            .filter { it.guardId == guardId }
            .filter { it.state == GuardState.ASLEEP }

    val timesSleptPerMinute = asleepGuardPeriodsForGuard
            .flatMap { it.period.getMinutes() }
            .groupingBy { it }
            .eachCount()

    return timesSleptPerMinute.maxBy(Map.Entry<Int, Int>::value)!!.key
}


/**
 * @return Pair of Guard id and Minute he slept most on
 */
fun getGuardThatWasMostAsleepOnSameMinuteTuple(): Pair<Int, Int> {
    return Pair(0, 0)
}