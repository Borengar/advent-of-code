import java.io.File

fun main() {
    val map = TopoMap()
    val score = map.getTotalScore()
    println(score)
}

class TopoMap() {
    private var map: Array<Array<Int>>;

    init {
        val inputData = File("src/input.txt").readLines()
        map = Array(inputData.count()) { arrayOf() }
        for (iLine in 0..<inputData.count()) {
            val line = inputData[iLine]
            map[iLine] = line.map { it.toString().toInt() }.toTypedArray()
        }
    }

    fun getTotalScore(): Int {
        var score = 0
        for (x in 0..<map.count()) {
            for (y in 0..<map[x].count()) {
                if (map[x][y] == 0) {
                    score += getTrailheadScore(x, y)
                }
            }
        }

        return score
    }

    fun getTrailheadScore(x: Int, y: Int): Int {
        val currentForks: MutableList<Fork> = mutableListOf()
        var score = 0

        var posX = x
        var posY = y
        while (true) {
            val currentHeight = map[posX][posY]

            // Summit reached
            if (currentHeight == 9) {
                // Increment score
                score++

                // No forks to go back to
                // -> This is the last reachable summit
                if (currentForks.count() == 0) {
                    break
                }

                // Go back to most recent fork of the trail
                val lastFork = currentForks.last()
                posX = lastFork.x
                posY = lastFork.y

                continue
            }

            // Check in which directions you can go from this position
            var fork: Fork
            if (currentForks.any { it.x == posX && it.y == posY }) {
                // Revisit earlier fork
                fork = currentForks.first { it.x == posX && it.y == posY }
            } else {
                // Haven't been here before
                fork = Fork(posX, posY, false, false, false, false)
                if (posX > 0 && map[posX - 1][posY] == currentHeight + 1) {
                    fork.north = true
                }
                if (posY < map[posX].count() - 1 && map[posX][posY + 1] == currentHeight + 1) {
                    fork.east = true
                }
                if (posX < map.count() - 1 && map[posX + 1][posY] == currentHeight + 1) {
                    fork.south = true
                }
                if (posY > 0 && map[posX][posY - 1] == currentHeight + 1) {
                    fork.west = true
                }
            }

            // This is a dead end
            if (fork.getAmountOfRoads() == 0) {
                // Remove this fork from list
                currentForks.remove(fork)

                // No forks to go back to
                if (currentForks.count() == 0) {
                    break
                }

                // Go back to most recent fork of the trail
                val lastFork = currentForks.last()
                posX = lastFork.x
                posY = lastFork.y

                continue
            }

            // Multiple ways to take exist
            if (fork.getAmountOfRoads() > 1 && !currentForks.contains(fork)) {
                currentForks.add(fork)
            }

            // Select a way to go
            if (fork.north) {
                posX--
                fork.north = false
            } else if (fork.east) {
                posY++
                fork.east = false
            } else if (fork.south) {
                posX++
                fork.south = false
            } else if (fork.west) {
                posY--
                fork.west = false
            }
        }

        return score
    }
}

class Fork(val x: Int, val y: Int, var north: Boolean, var east: Boolean, var south: Boolean, var west: Boolean) {
    fun getAmountOfRoads(): Int {
        var amount = 0
        if (north)
            amount++
        if (east)
            amount++
        if (south)
            amount++
        if (west)
            amount++
        return amount
    }
}