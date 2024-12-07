import java.io.File
import java.math.BigInteger

fun main() {
    val inputData = File("src/input.txt").readLines()
    var sum: Long = 0

    for (line in inputData) {
        val resultValue = line.split(':')[0].toLong()
        val numbers = line.split(':')[1].trim().split(' ').map { it.toLong() }.toMutableList()

        if (operate(numbers, resultValue)) {
            sum += resultValue
        }
    }

    println(sum)
}

fun operate(numbers: MutableList<Long>, result: Long): Boolean {
    if (numbers.count() == 2) {
        if (numbers[0] + numbers[1] == result)
            return true
        if (numbers[0] * numbers[1] == result)
            return true
        if ((numbers[0].toString() + numbers[1].toString()).toLong() == result)
            return true
        return false
    }

    val numbers1 = numbers.toMutableList()
    numbers1[1] = numbers1[0] + numbers1[1]
    numbers1.removeAt(0)

    val numbers2 = numbers.toMutableList()
    numbers2[1] = numbers2[0] * numbers2[1]
    numbers2.removeAt(0)

    val numbers3 = numbers.toMutableList()
    numbers3[1] = (numbers3[0].toString() + numbers3[1].toString()).toLong()
    numbers3.removeAt(0)

    return operate(numbers1, result) || operate(numbers2, result) || operate(numbers3, result)
}