import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors
import kotlin.streams.toList

fun main(vararg args: String) {
    println("Id checksum: ${getBoxIdsChecksum(loadBoxIds())}")
}

fun loadBoxIds(): List<String> {
    return BufferedReader(FileReader("boxIds.txt"))
            .lines()
            .toList()
}

fun getBoxIdsChecksum(boxIds: List<String>): Long {
    var twos: Long = 0
    var threes: Long = 0

    boxIds.stream()
            .forEach { id ->
                if(containsLetterThatIsPresentNTimes(id, 2)) twos++
                if(containsLetterThatIsPresentNTimes(id, 3)) threes++
            }

    return twos * threes;
}

fun containsLetterThatIsPresentNTimes(string: String, times: Long): Boolean {
    return getLetterCount(string).values
            .stream()
            .filter { count -> count == times }
            .findAny().isPresent
}

fun getLetterCount(string: String): Map<String, Long> {
    return string.split("").stream()
            .filter { char -> !char.isEmpty() }
            .collect(Collectors.groupingBy(
                    { char -> char },
                    Collectors.counting<String>()
            ))
}