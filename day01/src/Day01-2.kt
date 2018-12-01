//Can't find an elegant way to do this with streams :'(

fun main(args: Array<String>) {
    val reachedFrequencies = mutableMapOf<Int, Int>()
    var repeatingChanges = true
    var currentFrequency = 0

    while (repeatingChanges) {
        for (change in loadFrequencyChanges()) {
            currentFrequency += change

            if(reachedFrequencies[currentFrequency] != null) {
                println("Duplicate frequency found $currentFrequency")
                repeatingChanges = false
                break
            } else {
                reachedFrequencies[currentFrequency] = 1
            }
        }
    }
}