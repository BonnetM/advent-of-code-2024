package org.example.day10

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class Rating {

    suspend fun compute(filename: String) {
        val topological = readFile(filename)

        coroutineScope {
            val res = topological.flatMapIndexed { i, row ->
                row.mapIndexed { j, value ->
                    if (value == 0) {
                        async {
                            trailHeadRating(topological, i, j, expected = 0)
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

    private fun trailHeadRating(
        topological: List<List<Int>>,
        i: Int,
        j: Int,
        expected: Int,
    ): Int {
        return try {
            if (topological[i][j] == expected) {
                if (expected == 9) {
                    1
                } else {
                    trailHeadRating(topological, i + 1, j, expected + 1) +
                            trailHeadRating(topological, i - 1, j, expected + 1) +
                            trailHeadRating(topological, i, j + 1, expected + 1) +
                            trailHeadRating(topological, i, j - 1, expected + 1)
                }
            } else {
                0
            }
        } catch (e: Exception) {
            0
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