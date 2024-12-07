import java.io.File

fun main() {
    val inputData = File("src/input.txt").readLines()
    val map: MutableList<MutableList<Char>> = mutableListOf()
    var startRow = 0
    var startColumn = 0

    for (iLine in 0..<inputData.count()) {
        val line = inputData[iLine]
        val columns = line.toCharArray().toMutableList()
        map.add(columns)

        if (line.contains('^')) {
            startRow = iLine
            startColumn = columns.indexOf('^')
        }
    }

    map[startRow][startColumn] = 'X'
    var foundLoops = 0
    for (obstacleRow in 0..<map.count()) {
        obstacleLoop@ for (obstacleColumn in 0..<map[0].count()) {
            if (map[obstacleRow][obstacleColumn] != '.') {
                continue@obstacleLoop
            }

            val mapCopy: MutableList<MutableList<Char>> = mutableListOf()
            for (row in map) {
                mapCopy.add(row.toMutableList())
            }
            mapCopy[obstacleRow][obstacleColumn] = 'O'
            var iRow = startRow
            var iColumn = startColumn
            var direction = Direction.UP
            val touchies: MutableList<Pair<Pair<Int, Int>, Direction>> = mutableListOf()

            while (true) {
                var newRow = iRow
                var newColumn = iColumn

                when (direction) {
                    Direction.UP -> newRow--
                    Direction.RIGHT -> newColumn++
                    Direction.DOWN -> newRow++
                    Direction.LEFT -> newColumn--
                }

                if (newRow < 0 || newRow >= map.count() || newColumn < 0 || newColumn >= map[0].count()) {
                    continue@obstacleLoop
                }

                when (mapCopy[newRow][newColumn]) {
                    '.', 'X' -> {
                        iRow = newRow; iColumn = newColumn; mapCopy[newRow][newColumn] = 'X'
                    }
                    '#', 'O' -> {
                        if (touchies.any { it.first.first == newRow && it.first.second == newColumn && it.second == direction }) {
                            foundLoops++
                            continue@obstacleLoop
                        }
                        touchies.add(Pair(Pair(newRow, newColumn), direction))
                        direction = when (direction) {
                            Direction.UP -> Direction.RIGHT
                            Direction.RIGHT -> Direction.DOWN
                            Direction.DOWN -> Direction.LEFT
                            Direction.LEFT -> Direction.UP
                        }
                    }
                }
            }
        }
    }

    println("Found loops: ${foundLoops}")
}

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}