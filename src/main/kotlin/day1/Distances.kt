package org.example.day1

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.math.absoluteValue

class Distances {

    private data class Lists(val first: MutableList<Int>, val second: MutableList<Int>)

    suspend fun compute(filename: String) {
        val lists = readFile(filename)
        coroutineScope {
            val firstSorted = async { lists.first.sorted() }
            val secondSorted = async { lists.second.sorted() }

            val result = firstSorted.await().mapIndexed { index, it ->
                val other = secondSorted.await()[index]
                it.minus(other).absoluteValue
            }
                .sum()

            println(result)

        }


    }

    private fun readFile(filename: String): Lists {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()
            .map {
                val splitted = it.split("\\s+".toRegex())
                val pair = Pair(splitted[0].toInt(), splitted[1].toInt())
                pair
            }.fold(Lists(mutableListOf(), mutableListOf())) { acc, pair ->
                acc.first.add(pair.first)
                acc.second.add(pair.second)
                acc
            }
    }

}