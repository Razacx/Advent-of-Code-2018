import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.IntStream

fun main(args: Array<String>) {
    println(loadFrequencyChanges().sum())
}

fun loadFrequencyChanges(): IntStream {
    return BufferedReader(FileReader("frequencyChanges.txt"))
            .lines()
            .mapToInt(Integer::parseInt)
}