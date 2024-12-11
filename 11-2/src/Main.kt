import java.io.File
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun main() {
    val inputData = File("src/input.txt").readText()
    val numbers: Array<Long> = inputData.split(" ").map { it.toLong() }.toTypedArray()
    var stones: MutableMap<Long, Long> = mutableMapOf()
    for (number in numbers) {
        stones[number] = 1
    }

    for (i in 1..75) {
        stones = blink(stones)
    }

    var sum = 0L
    for (stone in stones) {
        sum += stone.value
    }

    println(sum)
}

fun blink(stones: MutableMap<Long, Long>): MutableMap<Long, Long> {
    val newStones: MutableMap<Long, Long> = mutableMapOf()
    for (stone in stones) {
        if (stone.key == 0L) {
            newStones[1] = stone.value + newStones.getOrDefault(1, 0)
            continue
        }

        val numberOfDigits = numberOfDigits(stone.key)
        if (numberOfDigits % 2 == 0) {
            val number1 = floor(stone.key / 10.toDouble().pow(numberOfDigits / 2)).toLong()
            val number2 = stone.key - number1 * 10.toDouble().pow(numberOfDigits / 2).toLong()
            newStones[number1] = stone.value + newStones.getOrDefault(number1, 0)
            newStones[number2] = stone.value + newStones.getOrDefault(number2, 0)
            continue
        }

        newStones[stone.key * 2024] = stone.value + newStones.getOrDefault(stone.key * 2024, 0)
    }
    return newStones
}

fun numberOfDigits(number: Long): Int {
    if (number == 0L)
        return 1
    return log10(number.toDouble()).toInt() + 1
}