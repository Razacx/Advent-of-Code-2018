package day09

import org.junit.Test
import kotlin.test.assertEquals

class Day09KtTest {

    @Test
    fun test_node_insertAfter_singleElement() {
        val rootNode = Node(5)

        assertEquals(rootNode, rootNode.rightNeighbour)
        assertEquals(rootNode, rootNode.leftNeighbour)
    }

    @Test
    fun test_node_insertAfter_twoElements() {
        val rootNode = Node(5)
        val secondNode = Node(6)
        rootNode.insertAfter(secondNode)

        assertEquals(secondNode, rootNode.rightNeighbour)
        assertEquals(secondNode, rootNode.leftNeighbour)
        assertEquals(rootNode, secondNode.leftNeighbour)
        assertEquals(rootNode, secondNode.rightNeighbour)
    }

    @Test
    fun test_node_insertAfter_multipleNodes() {
        val rootNode = Node(5)
        val secondNode = Node(6)
        val thirdNode = Node(7)
        rootNode.insertAfter(secondNode)
        secondNode.insertAfter(thirdNode)

        assertEquals(secondNode, rootNode.rightNeighbour)
        assertEquals(thirdNode, rootNode.leftNeighbour)
        assertEquals(rootNode, secondNode.leftNeighbour)
        assertEquals(thirdNode, secondNode.rightNeighbour)
        assertEquals(secondNode, thirdNode.leftNeighbour)
        assertEquals(rootNode, thirdNode.rightNeighbour)
    }

    @Test
    fun test_node_remove() {
        val rootNode = Node(5)
        val secondNode = Node(6)
        val thirdNode = Node(7)
        rootNode.insertAfter(secondNode)
        secondNode.insertAfter(thirdNode)

        val removedNode = secondNode.remove()

        assertEquals(thirdNode, rootNode.leftNeighbour)
        assertEquals(thirdNode, rootNode.rightNeighbour)

        assertEquals(rootNode, thirdNode.leftNeighbour)
        assertEquals(rootNode, thirdNode.rightNeighbour)

        assertEquals(secondNode, removedNode)
    }

    @Test
    fun test_game() {
        assertEquals(32, Game(9, 25).play().values.max())
        assertEquals(8317, Game(10, 1618).play().values.max())
        assertEquals(146373, Game(13, 7999).play().values.max())
        assertEquals(2764, Game(17, 1104).play().values.max())
        assertEquals(54718, Game(21, 6111).play().values.max())
        assertEquals(37305, Game(30, 5807).play().values.max())
    }
}