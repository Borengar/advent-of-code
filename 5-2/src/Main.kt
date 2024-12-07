import java.io.File

fun main() {
    val inputData = File("src/input.txt").readLines()
    val rules: MutableList<Pair<String, String>> = mutableListOf()

    var sum = 0

    for (line in inputData) {
        if (line.contains('|')) {
            val split = line.split('|')
            rules.add(Pair(split[0], split[1]))
            continue
        }

        if (line.contains(',')) {
            val split = line.split(',').toMutableList()
            var ruleBroken = false

            outer@ for (iNumber1 in 0..<split.count() - 1) {
                for (iNumber2 in iNumber1+1..<split.count()) {
                    if (rules.any { it.first == split[iNumber2] && it.second == split[iNumber1] }) {
                        ruleBroken = true
                        break@outer
                    }
                }
            }

            if (!ruleBroken) {
                continue
            }

            for (iNumber1 in 0..<split.count() - 1) {
                var swapped = true
                while (swapped) {
                    swapped = false
                    for (iNumber2 in iNumber1 + 1..<split.count()) {
                        if (rules.any { it.first == split[iNumber2] && it.second == split[iNumber1] }) {
                            val number1 = split[iNumber1]
                            val number2 = split[iNumber2]
                            split[iNumber1] = number2
                            split[iNumber2] = number1
                            swapped = true
                        }
                    }
                }
            }

            sum += split[(split.count() - 1) / 2].toInt()
        }
    }

    println(sum)
}