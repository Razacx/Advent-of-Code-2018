package day07

import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

data class Requirement(val id: String, val requirementFor: String)

data class Node(val id: String, val requirements: MutableList<Node>, var executed: Boolean) {

    fun findAvailableNodes(): List<Node> {
        return if (!executed && requirements.all { it.executed }) {
            listOf(this)
        } else {
            requirements
                    .filter { !it.executed }
                    .flatMap { it.findAvailableNodes() }
                    .distinct()
                    .sortedBy { it.id }
        }
    }

    // This solving strategy is wrong :(
    // Seems I didn't read the problem description well enough
    fun executeNextRequirement(): String {
        return if (requirements.none { !it.executed }) {
            executed = true
            id
        } else {
            requirements
                    .filter { !it.executed }
                    .sortedBy { it.id }
                    .first()
                    .executeNextRequirement()
        }
    }

}

fun parseRequirement(line: String): Requirement {
    val result = """Step ([A-Z]) must be finished before step ([A-Z]) can begin\.""".toRegex()
            .matchEntire(line)!!
    return Requirement(
            result.groups[1]!!.value,
            result.groups[2]!!.value
    )
}

fun buildDependencyGraph(requirements: List<Requirement>): Node {
    val nodes = buildBaseNodes(requirements).map { it.id to it }.toMap()

    for (requirement in requirements) {
        val requiredNode = nodes[requirement.id]!!
        val nodeThatRequirementIsFor = nodes[requirement.requirementFor]!!
        nodeThatRequirementIsFor.requirements.add(requiredNode)
    }

    val requiredNodes = requirements.map { it.id }
    return nodes.values.first { !requiredNodes.contains(it.id) }
}

fun buildBaseNodes(requirements: List<Requirement>): List<Node> {
    return requirements
            .flatMap { listOf(it.id, it.requirementFor) }
            .distinct()
            .map { Node(it, mutableListOf(), false) }
}

fun resolveGraphExecutionOrder(requirements: List<Requirement>): String {
    val finalNode = buildDependencyGraph(requirements)
    var executionOrder = ""

    while (!finalNode.executed) {
        val nextNode = finalNode.findAvailableNodes()
                .sortedBy { it.id }
                .first()
        nextNode.executed = true
        executionOrder += nextNode.id
    }

    return executionOrder
}

data class WorkOrder(var timeLeft: Int, val node: Node)

//Brute forcing it...
fun resolveGraphExecutionTime(requirements: List<Requirement>, workers: Int): Int {
    val finalNode = buildDependencyGraph(requirements)
    var timePassed = 0

    val workerTasks: MutableMap<Int, WorkOrder?> = (0 until workers).map { it to null as WorkOrder? }.toMap().toMutableMap()

    fun doWork() {
        workerTasks.keys.forEach {
            val workOrder = workerTasks[it]
            if(workOrder != null) {
                workOrder.timeLeft--
                if(workOrder.timeLeft == 0) {
                    workOrder.node.executed = true
                    workerTasks[it] = null
                }
            }
        }
    }

    fun assignAvailableWorkers(availableNodes: MutableList<Node>) {
        val availableWorkers = workerTasks.keys.filter { workerTasks[it] == null }
        for (worker in availableWorkers) {
            if(availableNodes.isEmpty()) {
                return
            } else {
                val node = availableNodes.removeAt(0)
                workerTasks[worker] = WorkOrder(getNodeTimeRequirement(node), node)
            }
        }
    }

    while (!finalNode.executed) {
        val availableNodes = finalNode.findAvailableNodes()
        val notOccupiedNodes = availableNodes.filter { !workerTasks.values.map { it?.node }.contains(it) }
        assignAvailableWorkers(notOccupiedNodes.toMutableList())

        doWork()
        timePassed++
    }

    return timePassed
}

fun getNodeTimeRequirement(node: Node): Int {
    return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(node.id) + 1 + 60
}

// This solving strategy is wrong :(
// Seems I didn't read the problem description well enough
//
//fun resolveGraphExecutionOrder(requirements: List<Requirement>): String {
//    val finalNode = buildDependencyGraph(requirements)
//    var executionOrder = ""
//
//    while (!finalNode.executed) {
//        executionOrder += finalNode.executeNextRequirement()
//    }
//
//    return executionOrder
//}

fun main(vararg args: String) {

    val requirements = BufferedReader(FileReader("input/day07/nodeRequirements.txt"))
            .lines()
            .map { parseRequirement(it) }
            .collect(Collectors.toList())
    val executionOrder = resolveGraphExecutionOrder(requirements)

    println("Execution order: \n\t$executionOrder")
    println()

    val executionTime = resolveGraphExecutionTime(requirements, 5)

    println("Execution time with 5 workers: \n\t${executionTime}s")

}