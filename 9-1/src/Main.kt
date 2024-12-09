import java.io.File

fun main() {
    val inputData = File("src/input.txt").readText()
    val fileBlocks: MutableList<Int?> = mutableListOf()

    for (i in 0..<inputData.count()) {
        val numberToInsert = when (i % 2) {
            0 -> i / 2
            else -> null
        }

        for (j in 0..<inputData[i].toString().toInt()) {
            fileBlocks.add(numberToInsert)
        }
    }

    var i = 0
    var sum: Long = 0
    outerLoop@ while (true) {
        if (i >= fileBlocks.count()) {
            break
        }

        if (fileBlocks[i] != null) {
            sum += i * fileBlocks[i]!!
            i++
            continue
        }

        while (true) {
            if (fileBlocks.last() != null) {
                break
            }
            fileBlocks.removeLast()
        }

        if (i >= fileBlocks.count()) {
            break
        }

        fileBlocks[i] = fileBlocks.last()
        fileBlocks.removeLast()

        sum += i * fileBlocks[i]!!
        i++
    }

    println(sum)
}