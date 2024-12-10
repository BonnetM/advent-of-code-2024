package org.example.day10

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class Trailhead {

    private data class Point(val x: Int, val y: Int)

    suspend fun compute(filename: String) {
        val topological = readFile(filename)

        coroutineScope {
            val res = topological.flatMapIndexed { i, row ->
                row.mapIndexed { j, value ->
                    if (value == 0) {
                        async {
                            val summitsSet = mutableSetOf<Point>()
                            trailHeadScore(topological, i, j, expected = 0, summitsSet)
                            summitsSet.size
                        }
                    } else {
                        null
                    }
                }
            }
                .filterNotNull()
                .awaitAll()
                .sum()

            println("res = $res")
        }
    }

    private suspend fun trailHeadScore(
        topological: List<List<Int>>,
        i: Int,
        j: Int,
        expected: Int,
        summitsSet: MutableSet<Point>,
    ) {
        try {
            if (topological[i][j] == expected) {
                if (expected == 9) {
                    summitsSet.add(Point(i, j))
                } else {
                    trailHeadScore(topological, i + 1, j, expected + 1, summitsSet)
                    trailHeadScore(topological, i - 1, j, expected + 1, summitsSet)
                    trailHeadScore(topological, i, j + 1, expected + 1, summitsSet)
                    trailHeadScore(topological, i, j - 1, expected + 1, summitsSet)
                }
            }
        } catch (e: Exception) {
            // Nothing
        }
    }

    private fun readFile(filename: String): List<List<Int>> {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines().map { line ->
                line.map {
                    try {
                        it.digitToInt()
                    } catch (e: Exception) {
                        -1
                    }
                }
            }
    }
}