package day02

import java.lang.IllegalArgumentException

fun main(vararg args: String) {
    val (id1, id2) = findCorrectBoxesPair(loadBoxIds())
    println("Found correct pair: $id1 <-> $id2")
    println("Common letters: ${getCommonLetters(id1, id2)}")
}

fun findCorrectBoxesPair(boxIds: List<String>): Pair<String, String> {
    for (i in 0 until boxIds.size) {
        val id1 = boxIds[i]
        for (j in 0 until boxIds.size) {
            if(i == j) continue
            val id2 = boxIds[j]
            val commonLetters = getCommonLetters(id1, id2)
            if(id1.length - commonLetters.length == 1) {
                return Pair(id1, id2)
            }
        }
    }
    throw IllegalArgumentException("Could not find a correct pair of boxes")
}

fun getCommonLetters(s1: String, s2: String): String {
    var common = ""
    for (i in 0 until s1.length) {
        if(s1[i] == s2[i]) {
            common += s1[i]
        }
    }
    return common
}