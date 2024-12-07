import java.io.File
import kotlin.math.abs

fun main() {
    val numbers1: MutableList<Int> = mutableListOf()
    val numbers2: MutableList<Int> = mutableListOf()

    val numbersInput = File("src/input.txt").readLines()

    for (line in numbersInput) {
        val numberSplit = line.split("   ")
        numbers1.add(numberSplit[0].toInt())
        numbers2.add(numberSplit[1].toInt())
    }

    numbers1.sort()
    numbers2.sort()

    var sum: Int = 0

    for (i in 0..<numbers1.count()) {
        sum += abs(numbers2[i] - numbers1[i])
    }

    println(sum)
}