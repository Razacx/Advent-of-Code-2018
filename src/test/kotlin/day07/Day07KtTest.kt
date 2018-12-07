package day07

import org.junit.Assert.assertEquals
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
        assertEquals(Node("C", mutableListOf(Node("B", mutableListOf(Node("A", mutableListOf()))))), buildDependencyGraph(requirements))
    }

    @Test
    fun test_buildBaseNodes() {
        val requirements = listOf(
                Requirement("A", "B"),
                Requirement("B", "C")
        )
        assertEquals(
                listOf(
                        Node("A", mutableListOf()),
                        Node("B", mutableListOf()),
                        Node("C", mutableListOf())
                ),
                buildBaseNodes(requirements)
        )
    }

}