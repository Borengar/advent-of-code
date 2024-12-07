import java.io.File

fun main() {
    val numbers1: MutableList<Int> = mutableListOf()
    val numbers2: MutableList<Int> = mutableListOf()

    val numbersInput = File("src/input.txt").readLines()

    for (line in numbersInput) {
        val numberSplit = line.split("   ")
        numbers1.add(numberSplit[0].toInt())
        numbers2.add(numberSplit[1].toInt())
    }

    var sum: Int = 0

    for (i in 0..<numbers1.count()) {
        val count = numbers2.count { it == numbers1[i] }
        sum += numbers1[i] * count
    }

    println(sum)
}