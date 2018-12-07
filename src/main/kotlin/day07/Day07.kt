package day07

data class Requirement(val id: String, val requirementFor: String)

data class Node(val id: String, val requirements: List<Node>)

fun parseRequirement(line: String): Requirement {
    val result = """Step ([A-Z]) must be finished before step ([A-Z]) can begin\.""".toRegex()
            .matchEntire(line)!!
    return Requirement(
            result.groups[1]!!.value,
            result.groups[2]!!.value
    )
}

fun buildDependencyGraph(requirements: List<Requirement>) {

}

fun buildBaseNodes(requirements: List<Requirement>): List<Node> {
    requirements.flatMap { listOf() }
}