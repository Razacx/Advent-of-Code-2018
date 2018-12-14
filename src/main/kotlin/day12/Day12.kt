package day12

data class RuleSet(val rules: List<ReproductionRule>) {

    fun isAliveInNextGeneration(coord: Long, currentGeneration: List<Long>): Boolean {
        val pattern = getPatternOfCoord(coord, currentGeneration)
        for (rule in rules) {
            if (rule.matches(pattern)) {
                return true
            }
        }
        return false
    }

}

fun getPatternOfCoord(coord: Long, currentGeneration: List<Long>): String {
    var pattern = ""

    pattern += if (currentGeneration.contains(coord - 2)) '#' else '.'
    pattern += if (currentGeneration.contains(coord - 1)) '#' else '.'
    pattern += if (currentGeneration.contains(coord)) '#' else '.'
    pattern += if (currentGeneration.contains(coord + 1)) '#' else '.'
    pattern += if (currentGeneration.contains(coord + 2)) '#' else '.'

    return pattern
}

data class ReproductionRule(val pattern: String) {

    fun matches(pattern: String): Boolean {
        return pattern == this.pattern
    }

}

fun evolve(currentGeneration: List<Long>, ruleSet: RuleSet): List<Long> {
    val minCoord = currentGeneration.min()!!
    val maxCoord = currentGeneration.max()!!

    val nextGeneration = mutableListOf<Long>()
    for (i in minCoord - 2..maxCoord + 2) {
        if (ruleSet.isAliveInNextGeneration(i, currentGeneration)) {
            nextGeneration.add(i)
        }
    }

    return nextGeneration
}

//Look at test file for solution...