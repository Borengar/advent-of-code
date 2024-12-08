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

                val antinode1 = Pair(antenna.X - diffX, antenna.Y - diffY)
                if (antinode1.first >= 0 && antinode1.first < map.count() &&
                    antinode1.second >= 0 && antinode1.second < map[0].count() &&
                    !antinodes.any { it.first == antinode1.first && it.second == antinode1.second }) {
                    antinodes.add(antinode1)
                }

                val antinode2 = Pair(x + diffX, y + diffY)
                if (antinode2.first >= 0 && antinode2.first < map.count() &&
                    antinode2.second >= 0 && antinode2.second < map[0].count() &&
                    !antinodes.any { it.first == antinode2.first && it.second == antinode2.second }) {
                    antinodes.add(antinode2)
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