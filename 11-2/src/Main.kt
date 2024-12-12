import java.io.File
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.system.measureTimeMillis

val blinkCache: MutableMap<Long, Pair<Long, Long?>> = mutableMapOf()

fun main() {
    val inputData = File("src/input.txt").readText()
    val numbers: Array<Long> = inputData.split(" ").map { it.toLong() }.toTypedArray()
    var stones: MutableMap<Long, Long> = mutableMapOf()
    for (number in numbers) {
        stones[number] = 1
    }
    blinkCache[0] = Pair(1, null)

    val time = measureTimeMillis {
        for (i in 1..75) {
            stones = blink(stones)
        }
    }
    println("${time}ms")

    var sum = 0L
    for (stone in stones) {
        sum += stone.value
    }

    println(sum)
}

fun blink(stones: MutableMap<Long, Long>): MutableMap<Long, Long> {
    val newStones: MutableMap<Long, Long> = mutableMapOf()
    for (stone in stones) {
        if (blinkCache.containsKey(stone.key)) {
            val value = blinkCache[stone.key]!!
            newStones[value.first] = stone.value + newStones.getOrDefault(value.first, 0)
            if (value.second != null) {
                newStones[value.second!!] = stone.value + newStones.getOrDefault(value.second, 0)
            }
            continue
        }

        val numberOfDigits = numberOfDigits(stone.key)
        if (numberOfDigits % 2 == 0) {
            val number1 = floor(stone.key / 10.toDouble().pow(numberOfDigits / 2)).toLong()
            val number2 = stone.key - number1 * 10.toDouble().pow(numberOfDigits / 2).toLong()
            newStones[number1] = stone.value + newStones.getOrDefault(number1, 0)
            newStones[number2] = stone.value + newStones.getOrDefault(number2, 0)
            blinkCache[stone.key] = Pair(number1, number2)
            continue
        }

        newStones[stone.key * 2024] = stone.value + newStones.getOrDefault(stone.key * 2024, 0)
        blinkCache[stone.key] = Pair(stone.key * 2024, null)
    }
    return newStones
}

fun numberOfDigits(number: Long): Int {
    if (number == 0L)
        return 1
    return log10(number.toDouble()).toInt() + 1
}