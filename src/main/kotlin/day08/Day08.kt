package day08

import java.io.BufferedReader
import java.io.FileReader

data class Node(val children: List<Node>, val metadata: List<Int>) {

    fun getAllMetadata(): List<Int> {
        val childMetadata = children.flatMap { it.getAllMetadata() }
        return childMetadata + metadata
    }

    fun getValue(): Int {
        return if (children.isEmpty()) {
            metadata.sum()
        } else {
            metadata
                    .map { it - 1 }
                    .filter { it >= 0 && it < children.size }
                    .map { children[it].getValue() }
                    .sum()
        }
    }

}

fun parseNode(data: String): Node {
    return parseNode(
            data.split(" ")
                    .filter { it.trim() != "" }
                    .map { it.toInt() }
                    .toMutableList()
    )
}

// Having a mutable list here makes it so we can treat the data as a queue
// Using sub-arrays would make it quite a bit harder to select the correct ranges for child nodes
fun parseNode(data: MutableList<Int>): Node {

    // Read & remove header
    val childCount = data.removeAt(0)
    val metadataCount = data.removeAt(0)

    // Parse children
    val children = mutableListOf<Node>()
    for (i in 0 until childCount) {
        children.add(parseNode(data))
    }

    // Read & remove metadata
    val metadata = mutableListOf<Int>()
    for (i in 0 until metadataCount) {
        metadata.add(data.removeAt(0))
    }

    return Node(children, metadata)

}

fun main(vararg args: String) {
    val rootNode = parseNode(BufferedReader(FileReader("input/day08/nodes.txt")).readLine())

    println("Sum of all metadata: " + rootNode.getAllMetadata().sum())
    println("Value of root node: " + rootNode.getValue())
}