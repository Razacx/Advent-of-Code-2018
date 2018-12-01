//Can't find an elegant way to do this with streams :'(

fun main(args: Array<String>) {
    println("Duplicate frequency found ${findFirstDuplicateReachedFrequency()}")
}

private fun findFirstDuplicateReachedFrequency(): Int {
    val reachedFrequencies = mutableSetOf(0)
    var currentFrequency = 0

    while (true) {
        for (change in loadFrequencyChanges()) {
            currentFrequency += change
            if(!reachedFrequencies.add(currentFrequency)) {
                return currentFrequency
            }
        }
    }
}