package day07

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test

class Day07KtTest {

    @Test
    fun test_parseRequirement() {
        assertEquals(Requirement("C", "A"), parseRequirement("Step C must be finished before step A can begin."))
        assertEquals(Requirement("C", "F"), parseRequirement("Step C must be finished before step F can begin."))
        assertEquals(Requirement("A", "B"), parseRequirement("Step A must be finished before step B can begin."))
        assertEquals(Requirement("A", "D"), parseRequirement("Step A must be finished before step D can begin."))
        assertEquals(Requirement("B", "E"), parseRequirement("Step B must be finished before step E can begin."))
        assertEquals(Requirement("D", "E"), parseRequirement("Step D must be finished before step E can begin."))
        assertEquals(Requirement("F", "E"), parseRequirement("Step F must be finished before step E can begin."))
    }

    @Test
    fun test_buildDependencyGraph_Linear() {
        val requirements = listOf(
                Requirement("A", "B"),
                Requirement("B", "C")
        )

        val nodeA = Node("A", mutableListOf(), false)
        val nodeB = Node("B", mutableListOf(nodeA), false)
        val nodeC = Node("C", mutableListOf(nodeB), false)

        assertEquals(nodeC, buildDependencyGraph(requirements))
    }

    @Test
    fun test_buildDependencyGraph_Diamond() {
        val requirements = listOf(
                Requirement("A", "B"),
                Requirement("A", "C"),
                Requirement("B", "D"),
                Requirement("C", "D")
        )

        val nodeA = Node("A", mutableListOf(), false)
        val nodeB = Node("B", mutableListOf(nodeA), false)
        val nodeC = Node("C", mutableListOf(nodeA), false)
        val nodeD = Node("D", mutableListOf(nodeB, nodeC), false)

        assertEquals(nodeD, buildDependencyGraph(requirements))
    }

    @Test
    fun test_buildBaseNodes() {
        val requirements = listOf(
                Requirement("A", "B"),
                Requirement("B", "C")
        )
        assertEquals(
                listOf(
                        Node("A", mutableListOf(), false),
                        Node("B", mutableListOf(), false),
                        Node("C", mutableListOf(), false)
                ),
                buildBaseNodes(requirements)
        )
    }

    @Test
    fun test_node_executeNextRequirement() {
        val nodeA = Node("A", mutableListOf(), false)
        val nodeB = Node("B", mutableListOf(nodeA), false)
        val nodeC = Node("C", mutableListOf(nodeA), false)
        val nodeD = Node("D", mutableListOf(nodeC, nodeB), false)

        assertEquals(nodeD.executeNextRequirement(), "A")
        assertEquals(nodeD.executeNextRequirement(), "B")
        assertEquals(nodeD.executeNextRequirement(), "C")
        assertEquals(nodeD.executeNextRequirement(), "D")
    }

    @Test
    fun test_resolveGraphExecutionOrder() {
        val requirements = listOf(
                Requirement("C", "A"),
                Requirement("C", "F"),
                Requirement("A", "B"),
                Requirement("A", "D"),
                Requirement("B", "E"),
                Requirement("D", "E"),
                Requirement("F", "E")
        )
        assertEquals("CABDFE", resolveGraphExecutionOrder(requirements))
    }

    @Test
    fun test_resolveGraphExecutionTime() {
        val requirements = listOf(
                Requirement("C", "A"),
                Requirement("C", "F"),
                Requirement("A", "B"),
                Requirement("A", "D"),
                Requirement("B", "E"),
                Requirement("D", "E"),
                Requirement("F", "E")
        )
        assertEquals(15, resolveGraphExecutionTime(requirements, 2, getNodeTimeRequirementFunction(0)))
    }

}