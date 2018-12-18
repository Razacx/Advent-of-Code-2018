package day16

fun parseInstruction(data: String): Instruction {
    val tokens = data.trim().split(" ")
    return Instruction(tokens[0].toInt(), tokens[1].toInt(), tokens[2].toInt(), tokens[3].toInt())
}

fun parseRegister(data: String): Register {
    val tokens = data.trim().split(", ")
    return Register(tokens.map { it.toInt() }.toIntArray())
}

fun parseSample(lines: List<String>): Sample {
    val beforeData = lines[0].removePrefix("Before: [").removeSuffix("]")
    val before = parseRegister(beforeData)

    val instruction = parseInstruction(lines[1])

    val afterData = lines[2].removePrefix("After:  [").removeSuffix("]")
    val after = parseRegister(afterData)

    return Sample(before, instruction, after)
}

fun parseSamples(lines: List<String>): List<Sample> {
    return lines
            .partition { it.isEmpty() }
            .map { parseSample(it) }
}

fun <K> List<K>.partition(predicate : (K) -> Boolean): List<List<K>> {
    val partitions = mutableListOf<List<K>>()
    var currentPartition = mutableListOf<K>()
    for (element in this) {
        if(predicate(element)) {
            partitions.add(currentPartition)
            currentPartition = mutableListOf()
        } else {
            currentPartition.add(element)
        }
    }
    partitions.add(currentPartition)
    return partitions
}