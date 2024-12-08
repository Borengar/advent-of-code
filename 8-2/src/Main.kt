import java.io.File

fun main() {
    val inputData = File("src/input.txt").readLines()
    val map: MutableList<MutableList<Char>> = mutableListOf()

    for (line in inputData) {
        map.add(line.toCharArray().toMutableList())
    }

    val antennas: MutableList<Antenna> = mutableListOf()
    val antinodes: MutableList<Pair<Int, Int>> = mutableListOf()

    for (x in 0..<map.count()) {
        for (y in 0..<map[x].count()) {
            if (map[x][y] == '.') {
                continue
            }

            val sameAntennas = antennas.filter { it.Type == map[x][y] }
            for (antenna in sameAntennas) {
                val diffX = x - antenna.X
                val diffY = y - antenna.Y

                var antinodeX = antenna.X
                var antinodeY = antenna.Y

                while (antinodeX >= 0 && antinodeX < map.count() &&
                    antinodeY >= 0 && antinodeY < map[0].count()) {
                    val antinode = Pair(antinodeX, antinodeY)
                    if (!antinodes.any { it.first == antinode.first && it.second == antinode.second }) {
                        antinodes.add(antinode)
                    }
                    antinodeX -= diffX
                    antinodeY -= diffY
                }

                antinodeX = x
                antinodeY = y
                while (antinodeX >= 0 && antinodeX < map.count() &&
                    antinodeY >= 0 && antinodeY < map[0].count()) {
                    val antinode = Pair(antinodeX, antinodeY)
                    if (!antinodes.any { it.first == antinode.first && it.second == antinode.second }) {
                        antinodes.add(antinode)
                    }
                    antinodeX += diffX
                    antinodeY += diffY
                }
            }

            antennas.add(Antenna(map[x][y], x, y))
        }
    }

    println(antinodes.count())
}

class Antenna(type: Char, x: Int, y: Int) {
    var Type: Char = type;
    var X: Int = x;
    var Y: Int = y;
}