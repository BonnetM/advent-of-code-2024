package org.example.day8

class Antinodes {

    private data class Point(val x: Int, val y: Int)

    fun compute(filename: String) {
        val map = readFile(filename)

        val lettersMap = mutableMapOf<Char, MutableList<Point>>()
        val width = map[0].size
        val height = map.size
        println("dimensions (${width}x${height})")

        for (i in map.indices) {
            for (j in map[i].indices) {
                val char = map[i][j]
                if (char != '.') {
                    if (lettersMap[char] == null) {
                        lettersMap[char] = mutableListOf(Point(i, j))
                    } else {
                        lettersMap[char]?.add(Point(i, j))
                    }
                }
            }
        }

        val antinodes = mutableSetOf<Point>()
        lettersMap.forEach { (char, list) ->
            for (i in list.indices) {
                val first = list[i]
                for (j in i + 1 until list.size) {
                    val second = list[j]
                    val candidateX = 2 * first.x - second.x
                    val candidateY = 2 * first.y - second.y
                    val secondCandidateX = 2 * second.x - first.x
                    val secondCandidateY = 2 * second.y - first.y
                    if (candidateX in 0..<width && candidateY in 0..<height) {
                        antinodes.add(Point(candidateX, candidateY))
                    }
                    if (secondCandidateX in 0..<width && secondCandidateY in 0..<height) {
                        antinodes.add(Point(secondCandidateX, secondCandidateY))
                    }
                }
            }
        }

        println(antinodes.size)

    }

    /**
     * j milieu de ia ssi
     * xj = xi/2 + xa/2, xa/2 = xj - xi/2, xa = 2xj - xi
     * yj = yi/2 + ya/2, ya/2 = yj - yi/2, ya = 2yj - yi
     *
     * i milieu de ja ssi
     *
     *
     */

    private fun readFile(filename: String): List<List<Char>> {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines().map { line ->
                line.toCharArray().toList()
            }
    }
}