package day07

data class Requirement(val id: String, val requirementFor: String)

data class Node(val id: String, val requirements: MutableList<Node>)

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
            .map { Node(it, mutableListOf()) }
}