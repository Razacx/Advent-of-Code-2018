package day16

import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

fun main(vararg args: String) {

    // Part 1 ----------------------------------------------------------------------------------------
    val samples = parseSamples(
            BufferedReader(FileReader("input/day16/samples.txt"))
                    .lines()
                    .collect(Collectors.toList())
    )

    val samplesThatBehaveLike3OrMoreOperations = samples.count { getPossibleOperationCount(it) >= 3 }
    println("Samples that behave like 3 or more opcodes: $samplesThatBehaveLike3OrMoreOperations")
    println()

    // Part 2 ----------------------------------------------------------------------------------------
    val opcodeOperations = resolveOpcodeOperations(samples)
    println("Resolved ${opcodeOperations.size} opcode operations successfully\n")

    println("Running program...")
    val instructions = BufferedReader(FileReader("input/day16/program.txt"))
            .lines()
            .map { parseInstruction(it) }
            .collect(Collectors.toList())
    val register = Register()
    for (instruction in instructions) {
        opcodeOperations[instruction.opcode]!!.execute(
                register,
                instruction.a,
                instruction.b,
                instruction.c
        )
    }
    println("Done!\nFinal register contains $register")

}

private fun resolveOpcodeOperations(samples: List<Sample>): Map<Int, Operation> {
    val possibleOperationsPerOpcode = samples
            .map { it.instruction.opcode to getPossibleOperations(it) }
            .toMap()

    val foundOpCodes = mutableMapOf<Int, Operation>()

    for (i in 0 until possibleOperationsPerOpcode.size) {
        val newOpCodes = partialResolveOpcodeOperations(
                possibleOperationsPerOpcode
                        .filter { !foundOpCodes.containsKey(it.key) }
        )
        for (opcode in newOpCodes.entries) {
            foundOpCodes[opcode.key] = opcode.value
        }
    }

    if (foundOpCodes.size != possibleOperationsPerOpcode.size) {
        throw RuntimeException("Unable to resolve all opcode operations (only ${foundOpCodes.size} out of ${possibleOperationsPerOpcode.size} were resolved)")
    }

    return foundOpCodes
}

fun partialResolveOpcodeOperations(possibleOperationsPerOpcode: Map<Int, List<Operation>>): Map<Int, Operation> {
    val opcodeOperations = mutableMapOf<Int, Operation>()

    for (entry in possibleOperationsPerOpcode) {
        val possibleOperationsOfOtherOpcodes = possibleOperationsPerOpcode.filter { it.key != entry.key }.flatMap { it.value }
        val uniqueOperation = entry.value.find { !possibleOperationsOfOtherOpcodes.contains(it) }
        if (uniqueOperation != null) {
            opcodeOperations[entry.key] = uniqueOperation
        }
    }

    return opcodeOperations
}

fun getPossibleOperations(sample: Sample): List<Operation> {
    return Operation.values()
            .filter {
                execute(
                        it,
                        sample.before,
                        sample.instruction.a,
                        sample.instruction.b,
                        sample.instruction.c
                ) == sample.after
            }
}

fun getPossibleOperationCount(sample: Sample): Int {
    return getPossibleOperations(sample).count()
}