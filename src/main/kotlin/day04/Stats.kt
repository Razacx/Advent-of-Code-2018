package day04

//TODO: Replace unnamed tuples with named data classes?

/**
 * @return Pair<Int, Int> -> left: guardId, right: minutesSlept
 */
fun getGuardWithMostMinutesAsleep(guardPeriods: List<GuardPeriod>): Pair<Int, Int> {
    val minutesSleptPerGuard = guardPeriods
            .filter { it.state == GuardState.ASLEEP }
            .groupBy(GuardPeriod::guardId)
            .mapValues {
                // Reduces List<GuardPeriod> for each Guard
                it.value.sumBy { guardPeriod ->
                    guardPeriod.period.minuteDifference()
                }
            }
    return minutesSleptPerGuard.maxBy { it.value }!!.toPair()
}

/**
 * @return Pair<Int, Int> -> left: minute, right: timesSleptOn
 */
fun getMinuteSleptOnMost(guardPeriods: List<GuardPeriod>, guardId: Int): Pair<Int, Int> {
    val asleepGuardPeriodsForGuard = guardPeriods
            .filter { it.guardId == guardId }
            .filter { it.state == GuardState.ASLEEP }

    val timesSleptPerMinute = asleepGuardPeriodsForGuard
            .flatMap { it.period.getMinutes() }
            .groupingBy { it }
            .eachCount()

    return timesSleptPerMinute.maxBy { it.value }!!.toPair()
}


/**
 * @return Triple<Int, Int, Int> -> first: guardId, second: minute, third: timesSleptOn
 */
//TODO: Might be possible to re-use some logic from getMinuteSleptOnMost (extract to separate function?)
fun getGuardThatWasMostAsleepOnSameMinuteTuple(guardPeriods: List<GuardPeriod>): Triple<Int, Int, Int> {
    val periodsPerGuard = guardPeriods
            .filter { it.state == GuardState.ASLEEP }
            .groupBy(GuardPeriod::guardId)

    return periodsPerGuard
            .map {
                val (mostSleptOnMinute, times) = it.value
                        .flatMap { it.period.getMinutes() }
                        .groupingBy { it }
                        .eachCount()
                        .maxBy { it.value }!!.toPair()
                Triple(it.key, mostSleptOnMinute, times)
            }
            .maxBy { it.third }!!
}