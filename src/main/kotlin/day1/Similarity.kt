package org.example.day1

class Similarity {

    private data class Lists(val first: MutableList<Int>, val second: MutableList<Int>)

    suspend fun compute(filename: String) {
        val lists = readFile(filename)
        val res = lists.first.sumOf { number ->
            number.times(lists.second.count { it == number })
        }

        println("Result: $res")


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