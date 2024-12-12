import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val inputData = File("src/input.txt").readText()
    val numbers: MutableList<Long> = inputData.split(" ").map { it.toLong() }.toMutableList()
    val time = measureTimeMillis {
        for (i in 1..25) {
            blink(numbers)
        }
    }
    println("${time}ms")
    println(numbers.count())
}

fun blink(numbers: MutableList<Long>) {
    var i = 0
    while (true) {
        if (i >= numbers.count()) {
            break
        }

        if (numbers[i] == 0L) {
            numbers[i] = 1
            i++
            continue
        }

        val numberAsString = numbers[i].toString()
        if (numberAsString.count() % 2 == 0) {
            val number1 = numberAsString.substring(0, numberAsString.length / 2).toLong()
            val number2 = numberAsString.substring(numberAsString.length / 2, numberAsString.length).toLong()
            numbers[i] = number1
            numbers.add(i + 1, number2)
            i += 2
            continue
        }

        numbers[i] = 2024 * numbers[i]
        i++
    }
}