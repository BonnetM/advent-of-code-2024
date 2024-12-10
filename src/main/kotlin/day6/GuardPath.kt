package org.example.day6

class GuardPath {

    fun compute(filename: String) {
        val map = readFile(filename)

        var currentX = -1
        var currentY = -1
        map.forEachIndexed { x, row ->
            row.forEachIndexed { y, char ->
                if (char == '^') {
                    currentX = x
                    currentY = y
                }
            }
        }
        map[currentX][currentY] = 'X'
        var currentOrientation = 0 // 0 (-x), 1 (+y), 2(+x), 3(-y)
        while (true) {
            try {
                val nextX = nextX(currentX, currentOrientation)
                val nextY = nextY(currentY, currentOrientation)
                if (map[nextX][nextY] != '#') {
                    currentX = nextX
                    currentY = nextY
                    map[currentX][currentY] = 'X'
                } else if (map[nextX][nextY] == '#') {
                    currentOrientation = nextOrientation(currentOrientation)
                }
            } catch (e: Exception) {
                println(map.sumOf { it.sumOf { if (it == 'X') 1L else 0 } })
                break
            }
        }
    }

    fun nextX(currentX: Int, orientation: Int): Int {
        return when (orientation) {
            0 -> currentX - 1
            2 -> currentX + 1
            else -> currentX
        }
    }

    fun nextY(currentY: Int, orientation: Int): Int {
        return when (orientation) {
            1 -> currentY + 1
            3 -> currentY - 1
            else -> currentY
        }
    }

    fun nextOrientation(currentOrientation: Int): Int {
        return (currentOrientation + 1).mod(4)
    }

    fun printMap(map: List<MutableList<Char>>) {
        map.forEach {
            println(it.joinToString(""))
        }
    }


    private fun readFile(filename: String): List<MutableList<Char>> {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines().map { line ->
                line.toCharArray().toMutableList()
            }
    }

}