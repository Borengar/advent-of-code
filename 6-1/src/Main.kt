import java.io.File

fun main() {
    val inputData = File("src/input.txt").readLines()
    val map: MutableList<MutableList<Char>> = mutableListOf()
    var iRow = 0
    var iColumn = 0

    for (iLine in 0..<inputData.count()) {
        val line = inputData[iLine]
        val columns = line.toCharArray().toMutableList()
        map.add(columns)

        if (line.contains('^')) {
            iRow = iLine
            iColumn = columns.indexOf('^')
        }
    }

    map[iRow][iColumn] = 'X'
    var uniqueSteps = 1
    var direction = Direction.UP
    pathingLoop@ while (true) {
        var newRow = iRow
        var newColumn = iColumn

        when (direction) {
            Direction.UP -> newRow--
            Direction.RIGHT -> newColumn++
            Direction.DOWN -> newRow++
            Direction.LEFT -> newColumn--
        }

        if (newRow < 0 || newRow >= map.count() || newColumn < 0 || newColumn >= map[0].count()) {
            break@pathingLoop
        }

        when (map[newRow][newColumn]) {
            '.' -> { iRow = newRow; iColumn = newColumn; uniqueSteps++; map[newRow][newColumn] = 'X' }
            'X' -> { iRow = newRow; iColumn = newColumn }
            '#' -> {
                direction = when (direction) {
                    Direction.UP -> Direction.RIGHT
                    Direction.RIGHT -> Direction.DOWN
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                }
            }
        }
    }

    println(uniqueSteps)
}

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}