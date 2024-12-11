package org.example.day11

class Blinking {
    suspend fun compute(filename: String, iterations: Int) {
        val line = readFile(filename)
        var previous = line
        println(previous)
        repeat(iterations) {
            val next = previous.flatMap { stone ->
                if (stone == 0L) {
                    listOf(1L)
                } else if (stone.toString().length.mod(2) == 0) {
                    val stoneString = stone.toString()
                    listOf(
                        stoneString.substring(0, stoneString.length.div(2)).toLong(),
                        stoneString.substring(stoneString.length.div(2), stoneString.length).toLong()
                    )
                } else {
                    listOf(stone * 2024L)
                }
            }
            previous = next
        }
        //previous.forEach { print("$it ") }
        println()
        println("res: ${previous.size}")

    }

    private fun readFile(filename: String): List<Long> {
        val lines = this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()

        return lines.first().split(" ").map { it.toLong() }

    }

}