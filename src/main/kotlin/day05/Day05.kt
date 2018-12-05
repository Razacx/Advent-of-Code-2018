package day05

import java.lang.Character.isUpperCase

typealias Polymer = String
typealias PolymerPair = String

fun react(polymer: Polymer): Polymer {
    var result = ""

    for (i in 0 until polymer.length) {
        val previousPair: PolymerPair? =
                if (i > 0) {
                    polymer[i - 1].toString() + polymer[i].toString()
                } else {
                    null
                }
        val nextPair: PolymerPair? =
                if (i < polymer.length - 1) {
                    polymer[i].toString() + polymer[i + 1].toString()
                } else {
                    null
                }

        if(!(previousPair?.isPolarPair() == true || nextPair?.isPolarPair() == true)) {
            result += polymer[i]
        }
    }

    return result
}

fun PolymerPair.isPolarPair(): Boolean {
    if (this.length != 2) {
        throw IllegalArgumentException("Invalid PolymerPair: $this")
    }

    val sameCharacter = this[0].toLowerCase() == this[1].toLowerCase()
    val polarOpposites = isUpperCase(this[0]) != isUpperCase(this[1])

    return sameCharacter && polarOpposites
}



