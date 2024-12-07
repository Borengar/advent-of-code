import java.io.File

fun main() {
    val inputData = File("src/input.txt").readLines()
    val lines: MutableList<MutableList<Char>> = mutableListOf()

    for (line in inputData) {
        val chars: MutableList<Char> = line.toCharArray().toMutableList()
        lines.add(chars)
    }

    println(searchWord(lines, "MAS"))
}

fun searchWord(matrix: MutableList<MutableList<Char>>, word: String): Int {
    var sum = 0
    val wordLength = word.count()

    for (iLine in 0..<matrix.count()) {
        val line = matrix[iLine]
        for (iColumn in 0..<line.count()) {
            if ((parseMatrix(matrix, iLine, iColumn, wordLength, Direction.DOWNRIGHT) == word || parseMatrix(matrix, iLine, iColumn, wordLength, Direction.DOWNRIGHT) == word.reversed()) &&
                (parseMatrix(matrix, iLine, iColumn + wordLength - 1, wordLength, Direction.DOWNLEFT) == word || parseMatrix(matrix, iLine, iColumn + wordLength - 1, wordLength, Direction.DOWNLEFT) == word.reversed())) {
                sum++
            }
        }
    }

    return sum
}

fun parseMatrix(matrix: MutableList<MutableList<Char>>, startLine: Int, startColumn: Int, length: Int, direction: Direction): String {
    var moveLines = 0
    var moveColumns = 0

    when (direction) {
        Direction.RIGHT -> moveColumns = 1
        Direction.LEFT -> moveColumns = -1
        Direction.UP -> moveLines = -1
        Direction.DOWN -> moveLines = 1
        Direction.UPRIGHT -> { moveLines = -1; moveColumns = 1 }
        Direction.UPLEFT -> { moveLines = -1; moveColumns = -1 }
        Direction.DOWNRIGHT -> { moveLines = 1; moveColumns = 1 }
        Direction.DOWNLEFT -> { moveLines = 1; moveColumns = -1 }
    }

    var resultString = ""
    var iLine = startLine
    var iColumn = startColumn

    for (i in 1..length) {
        if (iLine < 0 || iLine >= matrix.count()) {
            break
        }
        val line = matrix[iLine]
        if (iColumn < 0 || iColumn >= line.count()) {
            break
        }

        resultString += line[iColumn]

        iLine += moveLines
        iColumn += moveColumns
    }

    return resultString
}

enum class Direction {
    RIGHT, LEFT, UP, DOWN, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT
}