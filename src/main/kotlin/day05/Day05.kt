package day05

import java.io.BufferedReader
import java.io.FileReader
import java.lang.Character.isUpperCase

typealias Polymer = String
typealias PolymerPair = String

fun Polymer.react(): Polymer {



}

fun PolymerPair.isPolarPair(): Boolean {
    if (this.length != 2) {
        throw IllegalArgumentException("Invalid PolymerPair: $this")
    }

    val sameCharacter = this[0].toLowerCase() == this[1].toLowerCase()
    val polarOpposites = isUpperCase(this[0]) != isUpperCase(this[1])

    return sameCharacter && polarOpposites
}

fun main(vararg args: String) {

    println(BufferedReader(FileReader("input/day05/polymer.txt")).readLine().length)

    val finalPolymer = BufferedReader(FileReader("input/day05/polymer.txt"))
            .readLine().react()

    println("Final polymer has ${finalPolymer.length} units\n$finalPolymer")
}