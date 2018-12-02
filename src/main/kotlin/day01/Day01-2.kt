//Can't find an elegant way to do this with streams :'(

fun main(args: Array<String>) {
    val duplicateReachedFrequency = findFirstDuplicateReachedFrequency(loadFrequencyChanges().toArray())
    println("Duplicate frequency found $duplicateReachedFrequency")
}

private fun findFirstDuplicateReachedFrequency(frequencyChanges: IntArray): Int {
    val reachedFrequencies = mutableSetOf(0)
    var currentFrequency = 0

    while (true) {
        for (change in frequencyChanges) {
            currentFrequency += change
            if(!reachedFrequencies.add(currentFrequency)) {
                return currentFrequency
            }
        }
    }
}