package org.example.day8

class Harmonic {

    private data class Point(val x: Int, val y: Int)

    fun compute(filename: String) {
        val map = readFile(filename)

        val lettersMap = mutableMapOf<Char, MutableList<Point>>()
        val width = map[0].size
        val height = map.size

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
                    val deltaX = first.x - second.x
                    val deltaY = first.y - second.y
                    var startX = first.x
                    var startY = first.y
                    while (true) {
                        val candidateX = startX
                        val candidateY = startY
                        if (candidateX in 0..<width && candidateY in 0..<height) {
                            antinodes.add(Point(candidateX, candidateY))
                            startX+=deltaX
                            startY+=deltaY
                        } else {
                            break
                        }
                    }
                    val secondDeltaX = second.x - first.x
                    val secondDeltaY = second.y - first.y
                    startX = second.x
                    startY = second.y
                    while (true) {
                        val secondCandidateX = startX
                        val secondCandidateY = startY
                        if (secondCandidateX in 0..<width && secondCandidateY in 0..<height) {
                            antinodes.add(Point(secondCandidateX, secondCandidateY))
                            startX+=secondDeltaX
                            startY+=secondDeltaY
                        } else {
                            break
                        }
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