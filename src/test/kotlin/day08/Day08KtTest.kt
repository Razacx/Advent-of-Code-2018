package day08

import org.junit.Test
import kotlin.test.assertEquals

class Day08KtTest {

    @Test
    fun test_parseNode() {
        val expectedNode = Node(
                listOf(
                        Node(listOf(), listOf(10, 11, 12)),
                        Node(listOf(
                                Node(listOf(), listOf(99))
                        ), listOf(2))
                ),
                listOf(1, 1, 2)
        )
        val actualNode = parseNode("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2  ")

        assertEquals(expectedNode, actualNode)
    }

    @Test
    fun test_getAllMetadata() {
        assertEquals(138, parseNode("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2").getAllMetadata().sum())
    }

    @Test
    fun test_getValue() {
        assertEquals(66, parseNode("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2").getValue())
    }
}