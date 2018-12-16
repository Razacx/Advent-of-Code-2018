package day14

import java.lang.StringBuilder

data class Elf(var currentRecipeIndex: Int)

fun main(vararg args: String) {

    // Part 1 -------------------------------
    val iterations = 765071
    val scoreLength = 10
    val scoreAfterIterations = RecipeTester().getScoreAfter(iterations, scoreLength, false)
    println("Scores of $scoreLength recipes after $iterations iterations: $scoreAfterIterations")

    // Part 2 -------------------------------
    val wantedScore = 765071.toString()
    val iterationsForScore = RecipeTester().getIterationsForScore(wantedScore, false)
    println("It took $iterationsForScore iterations to reach $wantedScore")

}

class RecipeTester {

    val recipes = mutableListOf(3, 7)
    val elves = listOf(
            Elf(0),
            Elf(1)
    )

    fun createNewRecipes() {
        val recipeSum = elves.map { recipes[it.currentRecipeIndex] }.sum()
        val newRecipes = recipeSum.toString().toCharArray().map { it.toString().toInt() }

        recipes.addAll(newRecipes)
    }

    fun pickNewRecipes() {
        for (elf in elves) {
            elf.currentRecipeIndex += 1 + recipes[elf.currentRecipeIndex]
            elf.currentRecipeIndex %= recipes.size // Wrap around
        }
    }

    fun printState() {
        var line = ""
        recipes.forEachIndexed { index, i ->
            val executingElf = elves.find { it.currentRecipeIndex == index }
            val recipe = if (executingElf != null) {
                val elfIndex = elves.indexOf(executingElf)
                if (elfIndex == 0) "($i)" else "[$i]"
            } else {
                " $i "
            }
            line += "$recipe "
        }
        println(line)
    }

    fun getScoreAfter(iterations: Int, nextIterations: Int, print: Boolean): String {
        for (i in 0 until iterations + nextIterations) {
            if (print) printState()
            createNewRecipes()
            pickNewRecipes()
        }
        return recipes.subList(iterations, iterations + nextIterations).joinTo(StringBuilder(), "").toString()
    }

    fun getIterationsForScore(score: String, print: Boolean): Int {
        while (tailingScoresMatch(score) == -1) {
            if(print) printState()
            createNewRecipes()
            pickNewRecipes()
            if((recipes.size - 2) % 10e6.toInt() == 0) {
                println("${recipes.size - 2} iterations")
            }
        }
        return recipes.size - score.length - tailingScoresMatch(score)
    }

    // Workaround. I hate this catch
    private fun tailingScoresMatch(score: String): Int {
        if(recipes.size - 1 - score.length < 0) return -1
        val tailingScore = recipes.subList(recipes.size - score.length, recipes.size).joinTo(StringBuilder(), "").toString()
        if( score == tailingScore) {
            return 0
        }

        if(recipes.size - 2 - score.length < 0) return -1
        val tailingScore2 = recipes.subList(recipes.size - 1 - score.length, recipes.size - 1).joinTo(StringBuilder(), "").toString()
        if( score == tailingScore2) {
            return 1
        }

        return -1
    }

}
