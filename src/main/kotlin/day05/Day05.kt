package day05

import java.io.BufferedReader
import java.io.FileReader
import java.lang.Character.isUpperCase
import kotlin.system.measureTimeMillis

typealias Polymer = String
typealias PolymerPair = String

val alphabet = "abcdefghijklmnopqrstuvwxyz".split("").filter { it != "" }

fun Polymer.react(): Polymer {
    val newPolymer = reactionCycle()
    return if(newPolymer != this) {
        newPolymer.react()
    } else {
        this
    }
}

//Test with regex --------------------------------------------------------
fun Polymer.regexReactionCycle(): Polymer {
    return generatePairsRegex().replace(this, "")
}

fun generatePairsRegex(): Regex {
    val regex = StringBuilder()

    alphabet.forEach {
        val lowercase = it.toLowerCase()
        val uppercase = it.toUpperCase()
        var groups = "$lowercase$uppercase|$uppercase$lowercase|"
        if(lowercase == "z") groups = groups.substring(0, groups.length - 1)
        regex.append(groups)
    }

    return regex.toString().toRegex()
}
// -----------------------------------------------------------------------

fun Polymer.reactionCycle(): Polymer {
    var result = StringBuilder()

    var i = 0
    while (i < length) {
        if (i != length - 1) {
            val pair = this[i].toString() + this[i + 1].toString()
            if (!pair.isPolarPair()) {
                result.append(this[i])
                i++
            } else {
                i += 2
            }
        } else {
            result.append(this[i])
            i++
        }
    }

    return result.toString()
}

fun PolymerPair.isPolarPair(): Boolean {
    if (this.length != 2) {
        throw IllegalArgumentException("Invalid PolymerPair: $this")
    }

    val sameCharacter = this[0].toLowerCase() == this[1].toLowerCase()
    val polarOpposites = isUpperCase(this[0]) != isUpperCase(this[1])

    return sameCharacter && polarOpposites
}

fun Polymer.getWithoutUnit(unit: String): String {
    return this.replace("""[${unit.toLowerCase()}${unit.toUpperCase()}]""".toRegex(), "")
}

fun main(vararg args: String) {
    val polymer = BufferedReader(FileReader("input/day05/polymer.txt"))
            .readLine()

    val runtimeInMs = measureTimeMillis {

        val finalPolymer = polymer.react()
        println("Final polymer has ${finalPolymer.length} units")

        val minPolymerLength = alphabet
                .parallelStream()
                .mapToInt { finalPolymer.getWithoutUnit(it).react().length }
                .min().asInt
        println("Shortest possible polymer when removing 1 unit type: $minPolymerLength")

    }
    println("Program ran in ${runtimeInMs}ms")
}
